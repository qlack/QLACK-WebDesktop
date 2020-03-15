package com.eurodyn.qlack.webdesktop.translations.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories({
  "com.eurodyn.qlack.fuse.lexicon.repository",
    "com.eurodyn.qlack.fuse.aaa.repository"
})
@EntityScan({
  "com.eurodyn.qlack.fuse.lexicon.model",
  "com.eurodyn.qlack.webdesktop.common.model",
    "com.eurodyn.qlack.fuse.aaa.model"
})
@ComponentScan(basePackages = {
  "com.eurodyn.qlack.fuse.lexicon",
  "com.eurodyn.qlack.webdesktop.translations.management.controller",
    "com.eurodyn.qlack.webdesktop.translations.management.security",
  "com.eurodyn.qlack.webdesktop.translations.management.service",
    "com.eurodyn.qlack.fuse.aaa"
})
public class QlackWebDesktopTranslationsManagement {

  @SuppressWarnings({"java:S4823"})
  public static void main(String[] args) {
    SpringApplication.run(QlackWebDesktopTranslationsManagement.class, args);
  }

}
