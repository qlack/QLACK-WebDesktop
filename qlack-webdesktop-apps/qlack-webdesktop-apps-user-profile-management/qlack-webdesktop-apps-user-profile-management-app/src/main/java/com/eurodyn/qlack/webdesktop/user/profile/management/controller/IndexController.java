package com.eurodyn.qlack.webdesktop.user.profile.management.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * This Controller class contains all the endpoints related to application files (configuration and icons).
 *
 * @author EUROPEAN DYNAMICS SA
 */
@RestController
@Slf4j
@SuppressWarnings({"common-java:DuplicatedBlocks"})
public class IndexController {


  private ResourceLoader resourceLoader;

  @Autowired
  public IndexController(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * This method returns the application configuration.
   *
   * @return the application configuration
   */
  @GetMapping(value = "/configuration", produces = "application/yaml")
  public @ResponseBody
  ResponseEntity<byte[]> getConfiguration() {
    try {
      return new ResponseEntity<>(FileCopyUtils
          .copyToByteArray(
              resourceLoader.getResource("classpath:configuration.yaml").getInputStream()), HttpStatus.OK);
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * This method returns the application logo.
   *
   * @param iconSize the size of icon (regular or small)
   * @return the application logo
   */
  @GetMapping(value = "/logo/{iconSize}", produces = MediaType.IMAGE_PNG_VALUE)
  public @ResponseBody
  ResponseEntity<byte[]> getLogo(@PathVariable String iconSize) {
    try {
      return new ResponseEntity<>(FileCopyUtils
          .copyToByteArray(
              resourceLoader.getResource("classpath:" + iconSize + ".png").getInputStream()), HttpStatus.OK);
    } catch (IOException e) {
      log.error(e.getLocalizedMessage());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
