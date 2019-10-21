package com.eurodyn.qlack.webdesktop;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"com.eurodyn.qlack.webdesktop.repository"})
@EntityScan({"com.eurodyn.qlack.webdesktop.model"})
@ComponentScan(basePackages = {
        "com.eurodyn.qlack.webdesktop.configuration",
        "com.eurodyn.qlack.webdesktop.mapper",
        "com.eurodyn.qlack.webdesktop.service",
        "com.eurodyn.qlack.fuse.crypto.service"
})
public class WebDesktopApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WebDesktopApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
