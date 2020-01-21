package com.eurodyn.qlack.webdesktop.translations.management.controller;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This Controller class contains all the endpoints related to application files
 * (configuration and icons).
 *
 * @author EUROPEAN DYNAMICS SA
 */
@RestController
@Slf4j
@RequestMapping("/webdesktop/translations/management")
public class IndexController {

  @Autowired
  private ResourceLoader resourceLoader;

  /**
   * This method returns the application configuration.
   *
   * @return the application configuration
   */
  @GetMapping(value = "/configuration", produces = "application/yaml")
  public @ResponseBody
  byte[] getConfiguration() {
    try {
      return FileCopyUtils
        .copyToByteArray(
          resourceLoader.getResource("classpath:configuration.yaml")
            .getInputStream());
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      return new byte[0];
    }
  }

  /**
   * This method returns the application logo.
   *
   * @return the application logo
   */
  @GetMapping(value = "/icon", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody
  byte[] getLogo() {
    try {
      return FileCopyUtils
        .copyToByteArray(
          resourceLoader.getResource("classpath:icon.png").getInputStream());
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      return new byte[0];
    }
  }

  /**
   * This method returns the application small logo.
   *
   * @return the application small logo
   */
  @GetMapping(value = "/icon/small", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody
  byte[] getSmallLogo() {
    try {
      return FileCopyUtils
        .copyToByteArray(resourceLoader.getResource("classpath:icon_small.png")
          .getInputStream());
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      return new byte[0];
    }
  }

}
