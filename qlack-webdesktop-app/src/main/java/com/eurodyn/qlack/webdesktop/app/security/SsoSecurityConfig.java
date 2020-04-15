package com.eurodyn.qlack.webdesktop.app.security;

import com.eurodyn.qlack.fuse.aaa.repository.UserAttributeRepository;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.webdesktop.app.filter.PostAuthFilter;
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

  @Value("${oauth2.logout.page.url:/}")
  private String logoutUrl;
  private UserRepository userRepository;
  private UserAttributeRepository userAttributeRepository;

  @Autowired
  public SsoSecurityConfig(UserRepository userRepository, UserAttributeRepository userAttributeRepository) {
    this.userRepository = userRepository;
    this.userAttributeRepository = userAttributeRepository;
  }

  /**
   * Enables OAuth2 SSO authentication
   *
   * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} object
   * @throws Exception if the security configuration cannot be applied
   */
  @Override
  @SuppressWarnings({"java:S4502", "java:S4834"})
  public void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable().antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/login/**", "/logout.html", "/css/style.css", "/assets/img/**", "/auth/**").permitAll()
        .anyRequest().authenticated()
        .and().logout().invalidateHttpSession(true).clearAuthentication(true).logoutUrl("/logout")
        .logoutSuccessUrl(
            logoutUrl).deleteCookies("JSESSIONID").and().headers().frameOptions().disable().and()
        .oauth2Login();
  }

  /**
   * ensures that PostAuthFilter will be invoke only one time
   *
   * @return the  registrationBean
   */
  @Bean
  public FilterRegistrationBean<PostAuthFilter> loggingFilter() {
    FilterRegistrationBean<PostAuthFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new PostAuthFilter(this.userRepository, this.userAttributeRepository));
    registrationBean.addUrlPatterns("/");

    return registrationBean;
  }

}

