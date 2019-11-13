package com.eurodyn.qlack.webdesktop.configuration;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.eurodyn.qlack.common.exception.QAlreadyExistsException;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.model.Lexicon;
import com.eurodyn.qlack.webdesktop.util.LanguagesEnum;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.webdesktop.model.WdApplication;
import com.eurodyn.qlack.webdesktop.repository.WdApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.java.Log;

/**
 * Loads and creates Web Desktop application from .yaml configuration files provided as url endpoints and also registers
 * the routes for Zuul reverse proxy.
 *
 * @author European Dynamics SA.
 */
@Log
@Component
@Configuration
public class WdApplicationConfig implements ApplicationRunner {


    private static final String COMMA_REGEX = ",";
    private static final String APPS_URL = "apps.url";
    private static final String APPS_LANGUAGES = "apps.languages";
    private static final String VALID_URL_MSG = "The following valid urls have been provided:";
    private static final String APP_CREATION_MSG = "Web Desktop Application url found. "
            + "The application will be created / updated.";
    private static final String NO_URL_MSG = "No urls have been provided. No application will be integrated with Qlack WebDesktop.";
    private static final String USAGE_MSG = "Usage: --apps.url=http://www.myurl.com,https://www.myurl2.com, etc.";
    private static final String APP_UPDATE_MSG = "The Web Desktop Application configuration file has been "
            + "changed. The application will be updated.";
    private static final String INPUT_ERROR_MSG = "One of the provided urls returns a malformed YAML Web Desktop "
            + "Application file or no yaml file at all. Please check again your command line arguments. : %s";
    private static final String ERROR_MSG = "An error has occurred while initializing Web Desktop applications "
            + "configuration: %s";
    private static final String NO_LANGUAGES_MSG = "No languages have been provided. Default language: english";
    private static final String LANGUAGE_USAGE_MSG = "Languages_Usage: --apps.languages=en,el,de,fr, etc.";

    private WdApplicationRepository wdApplicationRepository;
    private CryptoDigestService cryptoDigestService;
    private DiscoveryClientRouteLocator discoveryClientRouteLocator;
    private LanguageService languageService;
    private GroupService groupService;
    private KeyService keyService;

    @Autowired
    public WdApplicationConfig(
            WdApplicationRepository wdApplicationRepository, CryptoDigestService cryptoDigestService,
            DiscoveryClientRouteLocator discoveryClientRouteLocator, LanguageService languageService,
            GroupService groupService, KeyService keyService) {

        this.wdApplicationRepository = wdApplicationRepository;
        this.cryptoDigestService = cryptoDigestService;
        this.discoveryClientRouteLocator = discoveryClientRouteLocator;
        this.languageService = languageService;
        this.groupService = groupService;
        this.keyService = keyService;
    }

    /**
     * Searches command line arguments for WebDesktop application configuration url endpoints. If found, the urls are
     * validated and the configuration files are loaded as Web Desktop applications in the database. This methods
     * overrides the {@link ApplicationRunner#run(org.springframework.boot.ApplicationArguments)} method to allow it to
     * run just before the application starts.
     *
     * @param args The command-line application arguments as loaded by Spring Boot
     */
    @Override
    public void run(ApplicationArguments args) {


        if (args.containsOption(APPS_LANGUAGES)) {
            List<String> languagesLocales = Arrays.asList(args.getOptionValues(APPS_LANGUAGES).get(0).trim().split("\\s*,\\s*"));

            for (String languageLocale : languagesLocales) {
                if (isValidLocale(languageLocale)) {
                    LanguageDTO languageDTO = new LanguageDTO();
                    languageDTO.setLocale(languageLocale);
                    languageDTO.setName(LanguagesEnum.valueOf(languageLocale.toUpperCase()).getLanguageName());
                    languageDTO.setActive(true);
                    try {
                        languageService.createLanguageIfNotExists(languageDTO);
                    } catch (QAlreadyExistsException e) {
                        log.info(" Language: " + languageLocale + " already exists and will not be created.");
                    }
                }
            }
        }else
        {
            log.warning(NO_LANGUAGES_MSG);
            log.info(LANGUAGE_USAGE_MSG);
        }

        if (args.containsOption(APPS_URL)) {
            String[] urls = args.getOptionValues(APPS_URL).get(0).split(COMMA_REGEX);

            List<String> validUrls = Arrays.stream(urls).filter(url -> UrlValidator.getInstance().isValid(url)).collect(
                    Collectors.toList());


            loadWdApplicationConfig(validUrls);

        } else {
            log.warning(NO_URL_MSG);
            log.info(USAGE_MSG);
        }



    }

    /**
     * Reads the .yaml files from the provided urls. If their content has already been persisted in the Web Desktop
     * application table and no changes are found, nothing happens. If their content has changed the application is
     * updated in the database. In case the current application does not exist it will be created.
     *
     * @param urls The urls that return a yaml configuration file
     */
    public void loadWdApplicationConfig(List<String> urls) {
        log.info(VALID_URL_MSG);
        for (String url : urls) {
            log.info(url);
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                WdApplication wdApplication = mapper.readValue(new URL(url), WdApplication.class);
                WdApplication existingWdApp = wdApplicationRepository.findByApplicationName(wdApplication.getApplicationName());
                String sha256 = cryptoDigestService.sha256(new URL(url).openStream());

                if (existingWdApp == null) {
                    log.info(APP_CREATION_MSG);
                    processWdApplication(wdApplication, sha256);
                    processLexiconValues(wdApplication.getLexicon(), wdApplication);

                } else if (!existingWdApp.getChecksum().equals(sha256)) {
                    log.info(APP_UPDATE_MSG);
                    wdApplication.setId(existingWdApp.getId());
                    processWdApplication(wdApplication, sha256);
                    processLexiconValues(wdApplication.getLexicon(), wdApplication);

                } else if (existingWdApp.getChecksum().equals(sha256)) {
                    processLexiconValues(wdApplication.getLexicon(), wdApplication);
                    registerReverseProxyRouteFromWdApp(existingWdApp);

                }
            } catch (MismatchedInputException mie) {
                log.warning(String.format(INPUT_ERROR_MSG, mie.toString()));
            } catch (Exception e) {
                log.warning(String.format(ERROR_MSG, e.toString()));
            }
        }
    }

    /**
     * Updates the file's SHA-256 checksum, saves the Web Desktop application and registers
     *
     * @param wdApplication The Web Desktop application
     * @param checksum      The file's SHA-256 checksum
     */
    private void processWdApplication(WdApplication wdApplication, String checksum) {
        if (wdApplication != null) {
            wdApplication.setChecksum(checksum);
            wdApplicationRepository.save(wdApplication);
            registerReverseProxyRouteFromWdApp(wdApplication);
        }
    }

    /**
     * Registers a route for Zuul reverse proxy by extracting all the required values from a {@link
     * com.eurodyn.qlack.webdesktop.model.WdApplication} object
     *
     * @param wdApplication the {@link com.eurodyn.qlack.webdesktop.model.WdApplication} object
     */
    private void registerReverseProxyRouteFromWdApp(WdApplication wdApplication) {
        registerReverseProxyRoute(wdApplication.getProxyPath(), wdApplication.getAppIndex(),
                wdApplication.isStripPrefix(),
                wdApplication.getSensitiveHeaders().split(COMMA_REGEX)
        );
    }

    /**
     * Registers a route for Zuul reverse proxy
     *
     * @param path             the reverse proxy path
     * @param url              the matching url
     * @param stripPrefix      whether the path should be stripped off of the forwarding url
     * @param sensitiveHeaders headers allowed to pass through Zuul reverse proxy
     */
    private void registerReverseProxyRoute(String path, String url, boolean stripPrefix, String[] sensitiveHeaders) {
        ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute(path, url);
        zuulRoute.setStripPrefix(stripPrefix);
        zuulRoute.setSensitiveHeaders(new HashSet<>(Arrays.asList(sensitiveHeaders)));
        discoveryClientRouteLocator.addRoute(zuulRoute);
    }

    /**
     * Saves/Updates lexicon values from .yaml configuration files
     *
     * @param translations The lexicon from .yaml configuration files
     * @param wdApplication  The webDesktop application
     */
    private void processLexiconValues(List<Lexicon> translations,  WdApplication wdApplication) {


        GroupDTO groupDTO = groupService.getGroupByTitle(wdApplication.getApplicationName());
        // we need groupId variable to get the generated Id of new group  from database
        String groupId;
        if (groupDTO == null) {

            groupDTO = new GroupDTO();
            groupDTO.setTitle(wdApplication.getApplicationName());
            groupDTO.setDescription("groupDescription");
            groupId = groupService.createGroup(groupDTO);
        } else {
            groupId = groupDTO.getId();
        }


        for (Lexicon translation : translations) {
            if (languageService.getLanguageByLocale(translation.getLanguageLocale()) != null) {
                KeyDTO keyDTO = keyService.getKeyByName(translation.getKey(), groupId, false);
                if (keyDTO == null) {
                    keyDTO = new KeyDTO();
                    keyDTO.setGroupId(groupId);
                    keyDTO.setName(translation.getKey());
                    String keyId = keyService.createKey(keyDTO, false);
                    keyService.updateTranslationByLocale(keyId, translation.getLanguageLocale(), translation.getValue());
                } else {

                    keyService.updateTranslationByLocale(keyDTO.getId(), translation.getLanguageLocale(), translation.getValue());
                }
            }
        }
    }

    /**
     * checks if the input locale from command line is supported from system
     *
     * @param locale the locale from command line
     * @return true if locale is valid else false
     */
    private Boolean isValidLocale(String locale) {
        for (LanguagesEnum value : LanguagesEnum.values()) {
            if (value.name().equalsIgnoreCase(locale))
                return true;
        }
        return false;
    }

}
