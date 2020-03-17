package com.eurodyn.qlack.webdesktop.user.profile.management;

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
    "com.eurodyn.qlack.fuse.aaa.repository",
    "com.eurodyn.qlack.webdesktop.common.repository"
})
@EntityScan({
    "com.eurodyn.qlack.fuse.lexicon.model",
    "com.eurodyn.qlack.fuse.aaa.model",
    "com.eurodyn.qlack.webdesktop.common.model"
})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.fuse.lexicon",
    "com.eurodyn.qlack.webdesktop.user.profile.management.controller",
    "com.eurodyn.qlack.webdesktop.user.profile.management.service",
    "com.eurodyn.qlack.webdesktop.user.profile.management.security",
    "com.eurodyn.qlack.webdesktop.common.service",
    "com.eurodyn.qlack.webdesktop.common.mapper",
    "com.eurodyn.qlack.fuse.aaa",
})
public class QlackWebdesktopUserProfileManagement {

  @SuppressWarnings({"java:S4823"})
  public static void main(String[] args) {
    SpringApplication.run(QlackWebdesktopUserProfileManagement.class, args);
  }
}
