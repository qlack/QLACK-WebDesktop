package com.eurodyn.qlack.webdesktop.app.filter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.service.LdapUserUtil;
import com.eurodyn.qlack.webdesktop.app.InitTestValues;
import com.eurodyn.qlack.webdesktop.app.filter.PostAuthFilter;
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

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class PostAuthFilterTest {

  @InjectMocks
  private PostAuthFilter postAuthFilter;

  @Mock
  private LdapUserUtil ldapUserUtil;
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
    when(ldapUserUtil.syncUserWithAAA(anyString())).thenReturn(user);

    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(ldapUserUtil, times(1)).syncUserWithAAA(anyString());
  }

  @Test
  public void doFilterWithoutDefaultOath2UserTest() throws IOException, ServletException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(ldapUserUtil, times(0)).syncUserWithAAA(anyString());
  }


  @Test
  public void doFilterCouldNotSyncAdminWithAAATest() throws IOException, ServletException {
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(ldapUserUtil.syncUserWithAAA(anyString())).thenReturn(null);

    postAuthFilter.doFilter(servletRequest, servletResponse, filterChain);
    verify(ldapUserUtil, times(1)).syncUserWithAAA(anyString());
  }

}
