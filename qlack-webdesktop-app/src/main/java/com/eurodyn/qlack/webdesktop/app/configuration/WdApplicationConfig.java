package com.eurodyn.qlack.webdesktop.app.configuration;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.service.GroupService;
import com.eurodyn.qlack.fuse.lexicon.service.KeyService;
import com.eurodyn.qlack.webdesktop.app.service.ZuulRouteService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.ResourceWdApplicationService;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.eurodyn.qlack.webdesktop.common.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.java.Log;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

  private static final String WEBDESKTOP_UI_LEXICON_GROUP = "webdesktop-ui";
  private static final String DEFAULT_LANGUAGE_LOCALE = "en";
  private static final String COMMA_REGEX = ",";
  private static final String APPS_URL = "apps.url";
  private static final String VALID_URL_MSG = "The following valid urls have been provided:";
  private static final String APP_CREATION_MSG = "Web Desktop Application url found. "
      + "The application will be created / updated.";
  private static final String NO_URL_MSG = "No urls have been provided. No new application will be integrated with Qlack WebDesktop.";
  private static final String USAGE_MSG = "Usage: --apps.url=http://www.myurl.com,https://www.myurl2.com, etc.";
  private static final String APP_UPDATE_MSG =
      "The Web Desktop Application configuration file has been "
          + "changed. The application will be updated.";
  private static final String INPUT_ERROR_MSG =
      "One of the provided urls returns a malformed YAML Web Desktop "
          + "Application file or no yaml file at all. Please check again your command line arguments. : %s";
  private static final String ERROR_MSG =
      "An error has occurred while initializing Web Desktop applications "
          + "configuration: %s";
  private static final String LOAD_ROUTES_FROM_DB_MSG = "Loading routes from database..";

  private WdApplicationRepository wdApplicationRepository;
  private CryptoDigestService cryptoDigestService;
  private GroupService groupService;
  private KeyService keyService;
  private UserRepository userRepository;
  private LdapUserUtil ldapUserUtil;
  private WdApplicationService wdApplicationService;
  private ResourceWdApplicationService resourceWdApplicationService;
  private ZuulRouteService zuulRouteService;
  private StringUtils stringUtils;
  @Value("${webdesktop.ldap.administrator.username:null}")
  private String wdAdmin;

  @Autowired
  @SuppressWarnings("squid:S00107")
  public WdApplicationConfig(
      WdApplicationRepository wdApplicationRepository, CryptoDigestService cryptoDigestService,
      GroupService groupService, KeyService keyService, UserRepository userRepository,
      LdapUserUtil ldapUserUtil,
      ResourceWdApplicationService resourceWdApplicationService,
      WdApplicationService wdApplicationService, ZuulRouteService zuulRouteService, StringUtils stringUtils) {

    this.wdApplicationRepository = wdApplicationRepository;
    this.cryptoDigestService = cryptoDigestService;
    this.groupService = groupService;
    this.keyService = keyService;
    this.userRepository = userRepository;
    this.ldapUserUtil = ldapUserUtil;
    this.wdApplicationService = wdApplicationService;
    this.resourceWdApplicationService = resourceWdApplicationService;
    this.zuulRouteService = zuulRouteService;
    this.stringUtils = stringUtils;
  }

  /**
   * Searches command line arguments for WebDesktop application configuration url endpoints. If found, the urls are
   * validated and the configuration files are loaded as Web Desktop applications in the database. This methods
   * overrides the {@link ApplicationRunner#run(org.springframework.boot.ApplicationArguments)} method to allow it to
   * run just before the application starts. Also creates superAdmin user if not exists.
   *
   * @param args The command-line application arguments as loaded by Spring Boot
   */
  @Override
  public void run(ApplicationArguments args) {
    if (userRepository.findByUsername(wdAdmin) == null && wdAdmin != null) {
      ldapUserUtil.setLdapMappingAttrs("firstName-givenName,lastName-sn");
      User user = ldapUserUtil.syncUserWithAAA(wdAdmin);
      if (user != null) {
        user.setSuperadmin(true);
        userRepository.save(user);
        log.info("Admin  successfully created");
      } else {
        log.warning("Could not sync Admin  with AAA.");
      }
    }

    if (args.containsOption(APPS_URL)) {
      String[] urls = args.getOptionValues(APPS_URL).get(0).split(COMMA_REGEX);

      List<String> validUrls = Arrays.stream(urls).collect(
              Collectors.toList());
      loadWdApplicationConfig(validUrls);
    } else {
      log.warning(NO_URL_MSG);
      log.info(USAGE_MSG);
    }

    // Register Reverse Proxy Routes from database
    for (WdApplication app : wdApplicationRepository.findByActiveIsTrue()) {
      if (stringUtils.isNotNullOrEmpty(app.getProxyAppPath()) && stringUtils.isNotNullOrEmpty(app.getAppUrl())) {
        log.info(LOAD_ROUTES_FROM_DB_MSG);
        zuulRouteService.addRoute("" + app.getProxyAppPath() + "**",
            app.getAppUrl() + app.getAppPath(), app.getId());
      }
    }

    zuulRouteService.refresh();
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
        WdApplication existingWdApp = wdApplicationRepository
            .findByApplicationName(wdApplication.getApplicationName());
        String sha256 = cryptoDigestService.sha256(new URL(url).openStream());

        if (existingWdApp == null) {
          log.info(APP_CREATION_MSG);
          wdApplication.setAddedOn(Instant.now().toEpochMilli());
          processWdApplication(wdApplication, sha256);
          processLexiconValues(wdApplication.getLexicon(), wdApplication);

        } else if (!existingWdApp.getChecksum().equals(sha256)) {
          log.info(APP_UPDATE_MSG);

          if (existingWdApp.isEditedByUI()) {
            wdApplication.setActive(existingWdApp.isActive());
            wdApplication.setRestrictAccess(existingWdApp.isRestrictAccess());
          }
          wdApplication.setId(existingWdApp.getId());
          processWdApplication(wdApplication, sha256);
          processLexiconValues(wdApplication.getLexicon(), wdApplication);

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
   * @param checksum The file's SHA-256 checksum
   */
  public void processWdApplication(WdApplication wdApplication, String checksum) {
    if (wdApplication != null) {
      wdApplication.setChecksum(checksum);
      boolean isNew = wdApplication.getId() == null;
      wdApplication.setLastDeployedOn(Instant.now().toEpochMilli());
      wdApplication = wdApplicationRepository.save(wdApplication);
      if (isNew){
        resourceWdApplicationService.createApplicationResource(wdApplication);
      }
      zuulRouteService.addRoute("" + wdApplication.getProxyAppPath() + "**",
          wdApplication.getAppUrl() + wdApplication.getAppPath(), wdApplication.getId());
      zuulRouteService.refresh();
    }
  }

  /**
   * Saves/Updates lexicon values from .yaml configuration files
   *
   * @param translations The lexicon from .yaml configuration files
   * @param wdApplication The webDesktop application
   */
  public void processLexiconValues(List<LexiconDTO> translations, WdApplication wdApplication) {
    wdApplicationService.processLexiconValues(translations, wdApplication);
    createKeyForAppGroupName(wdApplication.getGroupName());
  }

  public void createKeyForAppGroupName(String appGroupName) {
    if (appGroupName != null) {
      String webDesktopUiGroupId = groupService.getGroupByTitle(WEBDESKTOP_UI_LEXICON_GROUP)
          .getId();
      if (keyService.getKeyByName(appGroupName, webDesktopUiGroupId, false) == null) {
        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setGroupId(webDesktopUiGroupId);
        keyDTO.setName(appGroupName);
        String keyId = keyService.createKey(keyDTO, false);
        String modifiedGroupName =
            Character.toUpperCase(appGroupName.charAt(0)) + appGroupName.substring(1);
        keyService
            .updateTranslationByLocale(keyId, DEFAULT_LANGUAGE_LOCALE, modifiedGroupName);
      }
    }
  }

}
