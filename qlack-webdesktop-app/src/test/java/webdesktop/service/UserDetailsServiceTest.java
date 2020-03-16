package webdesktop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.dto.UserDTO;
import com.eurodyn.qlack.fuse.aaa.service.UserService;
import com.eurodyn.qlack.webdesktop.service.UserDetailsService;
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
import webdesktop.InitTestValues;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

  @InjectMocks
  private UserDetailsService userDetailsService;

  @Mock
  private UserService userService;

  @Mock
  private DefaultOAuth2User defaultOAuth2User;
  @Mock
  private Authentication authentication;
  @Mock
  private SecurityContext securityContext;
  @Mock
  private UserDTO userDTO;
  @Mock
  private Object principal;

  private Set<UserAttributeDTO> userAttributeDTOS;
  private InitTestValues initTestValues;

  @Before
  public void setup() {
    initTestValues = new InitTestValues();
    userAttributeDTOS = new HashSet<> (initTestValues.createUserAttributesDTO());
  }

  @Test
  public void findUserAttributesSuccessTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    Map<String, UserAttributeDTO> result = userDetailsService.findUserAttributes();
    assertNotNull(result);
    assertEquals(2,result.size());
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserAttributesFailTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);

    Map<String, UserAttributeDTO> result = userDetailsService.findUserAttributes();
    assertNull(result);

  }

  @Test
  public void findUserAttributeByNameSuccessTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    UserAttributeDTO result = userDetailsService.findUserAttributeByName("company");
    assertNotNull(result);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserAttributeByNameWithWrongNameTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(defaultOAuth2User);
    when(defaultOAuth2User.getName()).thenReturn("username");
    when(userService.getUserByName("username")).thenReturn(userDTO);
    when(userDTO.getUserAttributes()).thenReturn(userAttributeDTOS);
    UserAttributeDTO result = userDetailsService.findUserAttributeByName("wrong name");
    assertNull(result);
    verify(userService, times(1)).getUserByName(anyString());
    verify(userDTO, times(1)).getUserAttributes();

  }

  @Test
  public void findUserAttributeByNameFailTest(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);

    UserAttributeDTO result = userDetailsService.findUserAttributeByName("attributeName");
    assertNull(result);

  }

}
