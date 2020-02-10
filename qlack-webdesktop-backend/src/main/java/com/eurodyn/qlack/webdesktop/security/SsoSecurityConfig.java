package com.eurodyn.qlack.webdesktop.security;

import com.eurodyn.qlack.webdesktop.filter.PostAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security configuration class for OAuth2 login
 *
 * @author European Dynamics SA.
 */
@Configuration
@Profile("sso")
public class SsoSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PostAuthFilter postAuthFilter;

  @Value("${logout.url}")
  private String logoutUrl;

  @Autowired
  public SsoSecurityConfig(PostAuthFilter postAuthFilter) {
    this.postAuthFilter = postAuthFilter;
  }


  /**
   * Enables OAuth2 SSO authentication
   *
   * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} object
   * @throws Exception if the security configuration cannot be applied
   */
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable().antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/login/**").permitAll()
        .anyRequest().authenticated()
        .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout").logoutSuccessUrl(
        logoutUrl).deleteCookies("JSESSIONID").permitAll(true)
        .and().addFilterAfter(postAuthFilter, FilterSecurityInterceptor.class)
        .oauth2Login();
  }
}

