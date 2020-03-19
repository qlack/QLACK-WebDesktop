package com.eurodyn.qlack.webdesktop.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer class
 *
 * @author European Dynamics SA.
 */
public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WebDesktopApplication.class);
  }

}
