package com.eurodyn.qlack.webdesktop.app.configuration;

import com.eurodyn.qlack.webdesktop.app.service.ZuulRouteService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("sso-dev")
@Configuration
@Log
public class SSOZuulConfig implements ApplicationRunner {

  @Value("${oauth2.provider.url:http://localhost:8180/auth/}")
  private String oauth2ProviderUrl;

  private final ZuulRouteService zuulRouteService;

  @Autowired
  public SSOZuulConfig(ZuulRouteService zuulRouteService) {
    this.zuulRouteService = zuulRouteService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    zuulRouteService.addRoute("/auth/**", oauth2ProviderUrl, "oauth2-provider");
    zuulRouteService.refresh();
    log.info("Added SSO Provider url into Zuul routes.");
  }
}
