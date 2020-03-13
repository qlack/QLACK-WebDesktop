package com.eurodyn.qlack.webdesktop.applications.management.util;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.service.LanguageService;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessLexiconUtilTest {

  @InjectMocks
  private ProcessLexiconUtil processLexiconUtil;

  @Mock
  private LanguageService languageService;
  @Mock
  private WdApplicationService wdApplicationService;
  @Mock
  private WdApplicationMapper wdApplicationMapper;
  private WdApplicationDTO wdApplicationDTO;
  private List<LanguageDTO> translationKeys = new ArrayList<>();
  private List<LexiconDTO> lexiconDTOS = new ArrayList<>();
  private LanguageDTO languageDTO;

  @Before
  public void init() {
    wdApplicationDTO = new WdApplicationDTO();
    wdApplicationDTO.setId("wdApplicationId");

    languageDTO = new LanguageDTO();
    languageDTO.setLocale("en");

    translationKeys.add(languageDTO);
  }

  @Test
  public void createLexiconListTest() {
    when(languageService.getLanguages(anyBoolean())).thenReturn(translationKeys);

    assertNotNull(processLexiconUtil.createLexiconList(wdApplicationDTO));
    verify(languageService, times(1)).getLanguages(anyBoolean());
  }

  @Test
  public void createLexiconValuesTest() {
    processLexiconUtil.createLexiconValues(lexiconDTOS, wdApplicationDTO);
    verify(wdApplicationService, times(1)).processLexiconValues(any(), any());
  }
}
