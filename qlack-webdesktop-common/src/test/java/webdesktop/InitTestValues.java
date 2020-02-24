package webdesktop;

import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.model.Group;
import com.eurodyn.qlack.webdesktop.common.dto.LanguageDataDTO;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test values initialization class
 *
 * @author European Dynamics
 */
public class InitTestValues {

  public WdApplication createWdApplication() {
    WdApplication wdApplication = new WdApplication();

    wdApplication.setId("0f9a2472-cde0-44a6-ba3d-8e60992904fb");
    wdApplication.setProxyAppUrl("apps/usermanagement/");
    wdApplication.setProxyPath("a_path");

    wdApplication.setAddedOn(new Long("2121545432165"));
    wdApplication.setLastDeployedOn(new Long("2121545432165"));
    wdApplication.setVersion("2.0.0");
    wdApplication.setApplicationName("usermanagement");

    wdApplication.setIcon("{icon-square}mif-user");
    wdApplication.setIconSmall("{icon}mif-user");
    wdApplication.setWidth(800);
    wdApplication.setMinWidth(470);
    wdApplication.setHeight(500);
    wdApplication.setMinHeight(450);

    wdApplication.setActive(true);
    wdApplication.setMultipleInstances(false);
    wdApplication.setRestrictAccess(true);
    wdApplication.setResizable(true);
    wdApplication.setDraggable(true);
    wdApplication.setClosable(true);
    wdApplication.setMinimizable(true);
    wdApplication.setMaximizable(true);
    wdApplication.setSystem(false);
    wdApplication.setShowTitle(false);

    return wdApplication;
  }

  public WdApplicationDTO createWdApplicationDTO() {
    WdApplicationDTO wdApplicationDTO = new WdApplicationDTO();

    wdApplicationDTO.setId("0f9a2472-cde0-44a6-ba3d-8e60992904fb");
    wdApplicationDTO.setProxyAppUrl("apps/usermanagement/");
    wdApplicationDTO.setProxyPath("a_path");

    wdApplicationDTO.setAddedOn(new Long("2121545432165"));
    wdApplicationDTO.setLastDeployedOn(new Long("2121545432165"));
    wdApplicationDTO.setVersion("2.0.0");
    wdApplicationDTO.setApplicationName("usermanagement");

    wdApplicationDTO.setIcon("{icon-square}mif-user");
    wdApplicationDTO.setIconSmall("{icon}mif-user");
    wdApplicationDTO.setWidth(800);
    wdApplicationDTO.setMinWidth(470);
    wdApplicationDTO.setHeight(500);
    wdApplicationDTO.setMinHeight(450);

    wdApplicationDTO.setActive(true);
    wdApplicationDTO.setMultipleInstances(false);
    wdApplicationDTO.setRestrictAccess(true);
    wdApplicationDTO.setResizable(true);
    wdApplicationDTO.setDraggable(true);
    wdApplicationDTO.setClosable(true);
    wdApplicationDTO.setMinimizable(true);
    wdApplicationDTO.setMaximizable(true);
    wdApplicationDTO.setSystem(false);
    wdApplicationDTO.setShowTitle(false);

    return wdApplicationDTO;
  }

  public List<WdApplicationDTO> createWdApplicationsDTO() {
    List<WdApplicationDTO> wdApplicationDTOs = new ArrayList<>();
    wdApplicationDTOs.add(createWdApplicationDTO());
    return wdApplicationDTOs;
  }

  public List<WdApplication> createWdApplications() {
    List<WdApplication> wdApplications = new ArrayList<>();
    wdApplications.add(createWdApplication());
    return wdApplications;
  }

  public List<LexiconDTO> createLexicon() {
    List<LexiconDTO> lexiconDTOList = new ArrayList<>();
    LexiconDTO lexiconDTO = new LexiconDTO();
    lexiconDTO.setValues(createListOfLanguageData());
    lexiconDTO.setLanguageLocale("en");
    lexiconDTOList.add(lexiconDTO);
    return lexiconDTOList;
  }

  public List<LanguageDataDTO> createListOfLanguageData() {
    List<LanguageDataDTO> languageDataDTOList = new ArrayList<>();
    LanguageDataDTO languageDataDTO = new LanguageDataDTO();
    languageDataDTO.setKey("title");
    languageDataDTO.setValue(" english title");
    LanguageDataDTO languageDataDTO1 = new LanguageDataDTO();
    languageDataDTO.setKey("description");
    languageDataDTO.setValue(" english description");
    languageDataDTOList.add(languageDataDTO);
    languageDataDTOList.add(languageDataDTO1);
    return languageDataDTOList;
  }

  public GroupDTO createGroupDTO() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    groupDTO.setTitle("Application UI");
    groupDTO.setDescription("description");
    return groupDTO;
  }

  public KeyDTO createKeyDTO() {
    KeyDTO keyDTO = new KeyDTO();
    keyDTO.setId("0f2f12f8-4902-4355-ae52-20ccf92db2f3");
    keyDTO.setGroupId(createGroup().getId());
    keyDTO.setName("attachment_desc");

    Map<String, String> translations = new HashMap<>();
    translations.put("777119b0-bda0-4e87-9d3b-08d80e9bb9e8",
        "Add attachment description");
    translations.put("71df58f1-be26-410a-94ca-cfc90ac955a4",
        "Adicionar descrição do anexo");
    keyDTO.setTranslations(translations);

    return keyDTO;
  }

  public Group createGroup() {
    Group group = new Group();
    group.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    group.setTitle("Application UI");
    group.setDescription("description");
    return group;
  }

}
