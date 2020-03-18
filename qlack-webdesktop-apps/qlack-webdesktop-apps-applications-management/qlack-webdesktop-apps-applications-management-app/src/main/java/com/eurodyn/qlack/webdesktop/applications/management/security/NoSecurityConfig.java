package com.eurodyn.qlack.webdesktop.applications.management.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security Configuration that permits access to all paths
 *
 * @author European Dynamics SA.
 */
@Configuration
@ConditionalOnMissingBean(SsoSecurityConfig.class)
public class NoSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Permits access to all paths
   *
   * @param http the {@link HttpSecurity} object
   * @throws Exception if the security configuration cannot be applied
   */
  @Override
  @SuppressWarnings({"java:S4834"})
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().antMatchers("/").permitAll().and().headers().frameOptions().disable();
  }
}
