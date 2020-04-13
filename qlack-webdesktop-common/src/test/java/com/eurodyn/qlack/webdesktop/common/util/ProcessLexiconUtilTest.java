package com.eurodyn.qlack.webdesktop.common.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

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
  private WdApplicationDTO wdApplicationDTO;;
  @Mock
  private WdApplicationService wdApplicationService;
  @Mock
  private WdApplicationMapper wdApplicationMapper;
  private List<LanguageDTO> languageDTOS = new ArrayList<>();
  private LanguageDTO languageDTO = new LanguageDTO();
  private List<LexiconDTO> translations;

  @Before
  public void init(){
    languageDTO.setId("id");
    languageDTO.setLocale("en");
    languageDTO.setActive(true);

    languageDTOS.add(languageDTO);
  }

  @Test
  public void createLexiconListTest(){
    when(languageService.getLanguages(false)).thenReturn(languageDTOS);
    assertTrue(processLexiconUtil.createLexiconList(wdApplicationDTO).size() > 0);
  }

  @Test
  public void createLexiconValuesTest(){
    processLexiconUtil.createLexiconValues(translations, wdApplicationDTO);
    verify(wdApplicationMapper, times((1))).mapToEntity(wdApplicationDTO);
    verify(wdApplicationService, times((1))).processLexiconValues(any(), any());
  }

}
