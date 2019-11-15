package com.eurodyn.qlack.webdesktop;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableZuulProxy
@EnableJpaRepositories({
    "com.eurodyn.qlack.webdesktop.repository",
    "com.eurodyn.qlack.fuse.aaa.repository",
    "com.eurodyn.qlack.fuse.lexicon.repository"
})
@EntityScan({
    "com.eurodyn.qlack.webdesktop.model",
    "com.eurodyn.qlack.fuse.aaa.model",
    "com.eurodyn.qlack.fuse.lexicon.model"
})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.webdesktop.configuration",
    "com.eurodyn.qlack.webdesktop.security",
    "com.eurodyn.qlack.webdesktop.controller",
    "com.eurodyn.qlack.webdesktop.mapper",
    "com.eurodyn.qlack.webdesktop.service",
    "com.eurodyn.qlack.webdesktop.filter",
    "com.eurodyn.qlack.fuse.crypto.service",
    "com.eurodyn.qlack.fuse.lexicon",
    "com.eurodyn.qlack.fuse.aaa"
})
public class WebDesktopApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(WebDesktopApplication.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }
}

