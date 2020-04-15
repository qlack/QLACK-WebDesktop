package com.eurodyn.qlack.webdesktop.app.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.webdesktop.app.InitTestValues;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@RunWith(MockitoJUnitRunner.class)
public class PostAuthFilterTest {

  @InjectMocks
  private PostAuthFilter postAuthFilter;

  @Mock
  private DefaultOAuth2User defaultOAuth2User;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private Object principal;
  @Mock
  private ServletRequest servletRequest;
  @Mock
  private ServletResponse servletResponse;
  @Mock
  private UserRepository userRepository;
  @Mock
  private FilterChain filterChain;
  private InitTestValues initTestValues;
  private User user;

  @Before
  public void setup() {
    initTestValues = new InitTestValues();
    user = initTestValues.createUser();
  }

  @Test
  public void doFilterWithDefaultOath2UserTest() throws IOException, ServletException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userRepository.findByUsername(any())).thenReturn(user);

    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  public void doFilterWithoutDefaultOath2UserTest() throws IOException, ServletException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(userRepository, times(0)).findByUsername(anyString());
    verify(userRepository, times(0)).save(any());
  }

  @Test
  public void doFilterWithDefaultOath2NewUserTest() throws IOException, ServletException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userRepository.findByUsername(any())).thenReturn(null);

    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(userRepository, times(1)).findByUsername(anyString());
    verify(userRepository, times(1)).save(any());
  }

}
