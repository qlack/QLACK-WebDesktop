package com.eurodyn.qlack.webdesktop.app.configuration;

import com.eurodyn.qlack.webdesktop.app.service.ZuulRouteService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Reverse proxy configuration for OAuth2 provider.
 *
 * @author European Dynamics SA.
 */
@Profile("sso")
@Configuration
@Log
public class KeycloakZuulConfig implements ApplicationRunner {

  @Value("${oauth2.provider.url}")
  private String oauth2ProviderUrl;

  @Value("${oauth2.reversed.path:/}")
  private String oauth2ReservedPath;

  private ZuulRouteService zuulRouteService;

  @Autowired
  public KeycloakZuulConfig(ZuulRouteService zuulRouteService) {
    this.zuulRouteService = zuulRouteService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    zuulRouteService.addRoute("/" + oauth2ReservedPath + "/**", "" + oauth2ProviderUrl + "/", "oauth2-provider");
    zuulRouteService.refresh();
    log.info("Added SSO Provider url into Zuul routes.");
  }
}
