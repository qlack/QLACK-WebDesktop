package com.eurodyn.qlack.webdesktop.applications.management.security;

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
   * @param http the {@link HttpSecurity} object
   * @throws Exception if the security configuration cannot be applied
   */
  @Override
  @SuppressWarnings({"java:S4502", "java:S4834"})
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/configuration","/logo/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .oauth2Login();
  }
}
