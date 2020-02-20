package com.eurodyn.qlack.webdesktop.security;

import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import com.eurodyn.qlack.webdesktop.filter.PostAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
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

  @Value("${logout.url}")
  private String logoutUrl;
  private LdapUserUtil ldapUserUtil;

  @Autowired
  public SsoSecurityConfig(LdapUserUtil ldapUserUtil) {
    this.ldapUserUtil = ldapUserUtil;
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
        .antMatchers("/login/**","/logout.html","/css/style.css").permitAll()
        .anyRequest().authenticated()
        .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout").logoutSuccessUrl(
        logoutUrl).deleteCookies("JSESSIONID").and()
        .oauth2Login();
  }

  /**
   * ensures that PostAuthFilter will be invoke only one time
   *
   * @return the  registrationBean
   */
  @Bean
  public FilterRegistrationBean<PostAuthFilter> loggingFilter(){
    FilterRegistrationBean<PostAuthFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new PostAuthFilter(this.ldapUserUtil));
    registrationBean.addUrlPatterns("/");

    return registrationBean;
  }

}

