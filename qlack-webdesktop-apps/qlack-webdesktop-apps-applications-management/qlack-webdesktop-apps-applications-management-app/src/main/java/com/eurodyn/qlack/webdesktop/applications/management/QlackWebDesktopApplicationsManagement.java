package com.eurodyn.qlack.webdesktop.applications.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The main class of the Spring Boot application.
 *
 * @author EUROPEAN DYNAMICS SA
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
@EnableJpaRepositories({
    "com.eurodyn.qlack.webdesktop.repository",
    "com.eurodyn.qlack.fuse.lexicon.repository",
    "com.eurodyn.qlack.fuse.aaa.repository"
})
@EntityScan({
    "com.eurodyn.qlack.webdesktop.model",
    "com.eurodyn.qlack.fuse.lexicon.model",
    "com.eurodyn.qlack.fuse.aaa.model"
})
@ComponentScan(basePackages = {
    "com.eurodyn.qlack.webdesktop.applications.management",
    "com.eurodyn.qlack.webdesktop.service",
    "com.eurodyn.qlack.webdesktop.mapper",
    "com.eurodyn.qlack.fuse.lexicon",
    "com.eurodyn.qlack.fuse.crypto.service",
    "com.eurodyn.qlack.fuse.aaa"
})
public class QlackWebDesktopApplicationsManagement {

  /**
   * This method runs the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(QlackWebDesktopApplicationsManagement.class, args);
  }
}
