package com.eurodyn.qlack.webdesktop.filter;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;


/**
 * SSO / LDAP / AAA Integration filter After SSO user authentication the username is queried against
 * a user provided LDAP service to ensure the user exists. If this is the case the users is created
 * if not already in the local AAA users database. The filter should be registered at the Spring
 * Security Filter chain.
 *
 * @author European Dynamics SA.
 */
@Slf4j
@Component
@Profile("sso")
public class PostAuthFilter implements Filter {

  private final LdapUserUtil ldapUserUtil;

  @Autowired
  public PostAuthFilter(LdapUserUtil ldapUserUtil) {
    this.ldapUserUtil = ldapUserUtil;
  }

  /**
   * Extracts the username from Principal and syncs the user with AAA
   *
   * @param servletRequest the request
   * @param servletResponse the response
   * @param filterChain the filterchain
   * @throws IOException if an error occurs during filtering
   * @throws ServletException if an error occurs during filtering
   */
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain)
      throws IOException, ServletException {

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof DefaultOAuth2User) {
      User user = ldapUserUtil.syncUserWithAAA(((DefaultOAuth2User) principal).getName());

      if (user != null) {
        log.info(String.format("User %s logged in at %s", user.getUsername(), new Date()));
      } else {
        log.warn("Could not sync user with AAA.");
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

}
