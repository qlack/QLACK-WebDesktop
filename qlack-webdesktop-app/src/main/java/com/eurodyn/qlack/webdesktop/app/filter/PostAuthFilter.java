package com.eurodyn.qlack.webdesktop.app.filter;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.model.UserAttribute;
import com.eurodyn.qlack.fuse.aaa.repository.UserAttributeRepository;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


/**
 * SSO / AAA Integration filter After SSO user authentication the username is queried against a user provided service to
 * ensure the user exists. If this is the case the users is created if not already in the local AAA users database. The
 * filter should be registered at the Spring Security Filter chain.
 *
 * @author European Dynamics SA.
 */
@Slf4j
@Profile("sso")
public class PostAuthFilter implements Filter {

  private final UserRepository userRepository;
  private final UserAttributeRepository userAttributeRepository;

  public PostAuthFilter(UserRepository userRepository, UserAttributeRepository userAttributeRepository) {
    this.userRepository = userRepository;
    this.userAttributeRepository = userAttributeRepository;
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
      User user = userRepository.findByUsername(((DefaultOAuth2User) principal).getName());
      if (user != null) {
        log.info(String.format("User %s logged in at %s", user.getUsername(), new Date()));
      } else {
        user = new User();
        user.setUsername(((DefaultOAuth2User) principal).getName());
        userRepository.save(user);
        createUserAttributes((DefaultOAuth2User) principal);
        log.info(String.format("User %s successfully created", user.getUsername()));
      }
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  private void createUserAttributes(DefaultOAuth2User principal) {
    User user = userRepository.findByUsername(principal.getName());
    for (Map.Entry<String, Object> entry : principal.getAttributes().entrySet()) {
      if ("given_name".equals(entry.getKey())) {
        setAttributes(entry, user, "firstName");
      }
      if ("family_name".equals(entry.getKey())) {
        setAttributes(entry, user, "lastName");
      }
    }
  }

  private void setAttributes(Map.Entry<String, Object> entry, User user, String attribute) {
    UserAttribute userAttribute = new UserAttribute();
    userAttribute.setUser(user);
    userAttribute.setName(attribute);
    userAttribute.setData(entry.getValue().toString());
    userAttributeRepository.save(userAttribute);
  }
}
