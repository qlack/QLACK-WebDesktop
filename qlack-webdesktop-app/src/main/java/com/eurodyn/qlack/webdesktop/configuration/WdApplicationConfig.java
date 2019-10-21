package com.eurodyn.qlack.webdesktop.configuration;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
 * Loads and creates Web Desktop application from .yaml configuration files provided as url endpoints.
 *
 * @author European Dynamics SA.
 */
@Log
@Component
@Configuration
public class WdApplicationConfig implements ApplicationRunner {

    private static final String APPS_URL = "apps.url";
    private static final String VALID_URL_MSG = "The following valid urls have been provided:";
    private static final String APP_CREATION_MSG = "Web Desktop Application url found. "
            + "The application will be created / updated.";
    private static final String NO_URL_MSG = "No urls have been provided. No application will be integrated with Qlack WebDesktop.";
    private static final String USAGE_MSG = "Usage: --apps.url=http://www.myurl.com,https://www.myurl2.com, etc.";
    private static final String APP_UPDATE_MSG = "The Web Desktop Application configuration file url has been "
            + "changed. The application will be updated.";
    private static final String INPUT_ERROR_MSG = "One of the provided urls returns a malformed YAML Web Desktop "
            + "Application file or no yaml file at all. Please check again your command line arguments. : %s";
    private static final String ERROR_MSG = "An error has occurred while initializing Web Desktop applications "
            + "configuration: %s";

    private WdApplicationRepository wdApplicationRepository;
    private CryptoDigestService cryptoDigestService;

    @Autowired
    public WdApplicationConfig(
            WdApplicationRepository wdApplicationRepository, CryptoDigestService cryptoDigestService) {
        this.wdApplicationRepository = wdApplicationRepository;
        this.cryptoDigestService = cryptoDigestService;
    }

    /**
     * Searhes command line arguments for WebDesktop application configuration url endpoints. If found, the urls are
     * validated and the configuration files are loaded as Web Desktop applications in the database. This methods
     * overrides the {@link ApplicationRunner#run(org.springframework.boot.ApplicationArguments)} method to allow it to
     * run just before the application starts.
     *
     * @param args The command-line application arguments as loaded by Spring Boot
     */
    @Override
    public void run(ApplicationArguments args) {

        if (args.containsOption(APPS_URL)) {
            String[] urls = args.getOptionValues(APPS_URL).get(0).split(",");

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
                WdApplication existingWdApp = wdApplicationRepository.findByTitleKey(wdApplication.getTitleKey());
                String sha256 = cryptoDigestService.sha256(new URL(url).openStream());

                if (existingWdApp == null) {
                    log.info(APP_CREATION_MSG);
                    processWdApplication(wdApplication, sha256);
                } else if (!existingWdApp.getChecksum().equals(sha256)) {
                    log.info(APP_UPDATE_MSG);
                    wdApplication.setId(existingWdApp.getId());
                    processWdApplication(wdApplication, sha256);
                }
            } catch (MismatchedInputException mie) {
                log.warning(String.format(INPUT_ERROR_MSG, mie.toString()));
            } catch (Exception e) {
                log.warning(String.format(ERROR_MSG, e.toString()));
            }
        }
    }

    /**
     * Updates the file's SHA-256 checksum and saves the Web Desktop application
     *
     * @param wdApplication The Web Desktop application
     * @param checksum      The file's SHA-256 checksum
     */
    private void processWdApplication(WdApplication wdApplication, String checksum) {
        if (wdApplication != null) {
            wdApplication.setChecksum(checksum);
            wdApplicationRepository.save(wdApplication);
        }
    }
}
