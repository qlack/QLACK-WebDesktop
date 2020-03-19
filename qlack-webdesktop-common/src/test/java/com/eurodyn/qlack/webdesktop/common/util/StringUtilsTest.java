package com.eurodyn.qlack.webdesktop.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StringUtilsTest {

  @InjectMocks
  private StringUtils stringUtils;

  @Test
  public void isNotNullOrEmptySuccessTest() {
    boolean result = stringUtils.isNotNullOrEmpty("a string");
    assertTrue(result);
  }

  @Test
  public void isNotNullOrEmptyWithNullValueTest() {
    boolean result = stringUtils.isNotNullOrEmpty(null);
    assertFalse(result);
  }

  @Test
  public void isNotNullOrEmptyWithEmptyValueTest() {
    boolean result = stringUtils.isNotNullOrEmpty("");
    assertFalse(result);
  }

}
