package com.eurodyn.qlack.webdesktop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration class for OAuth2 login
 *
 * @author European Dynamics SA.
 */
@Configuration
@Profile("sso")
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Enables OAuth2 SSO authentication
   *
   * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity}
   * object
   * @throws Exception if the security configuration cannot be applied
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login();
  }
}

