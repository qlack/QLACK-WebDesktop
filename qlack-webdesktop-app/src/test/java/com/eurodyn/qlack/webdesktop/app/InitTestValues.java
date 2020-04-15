package com.eurodyn.qlack.webdesktop.app;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.model.Group;
import com.eurodyn.qlack.webdesktop.common.dto.LanguageDataDTO;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitTestValues {

  public UserAttributeDTO createUserAttributeDTO() {
    UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
    userAttributeDTO.setId("dca76ec3-0423-4a17-8287-afd311697dbf");
    userAttributeDTO.setName("fullName");
    userAttributeDTO.setData("FirstName LastName");
    userAttributeDTO.setContentType("text");

    return userAttributeDTO;
  }

  public List<UserAttributeDTO> createUserAttributesDTO() {
    List<UserAttributeDTO> userAttributesDTO = new ArrayList<>();
    userAttributesDTO.add(this.createUserAttributeDTO());

    UserAttributeDTO userAttributeDTO = new UserAttributeDTO();
    userAttributeDTO.setId("ef682d4c-be43-4a33-8262-8af497816277");
    userAttributeDTO.setName("company");
    userAttributeDTO.setData("European Dynamics");
    userAttributeDTO.setContentType("text");

    userAttributesDTO.add(userAttributeDTO);

    return userAttributesDTO;
  }

  public Group createGroup() {
    Group group = new Group();
    group.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    group.setTitle("Application UI");
    group.setDescription("description");
    return group;
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

  public GroupDTO createGroupDTO() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    groupDTO.setTitle("Application UI");
    groupDTO.setDescription("description");
    return groupDTO;
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


  public User createUser() {
    User user = new User();
    user.setId("57d30f8d-cf0c-4742-9893-09e2aa08c255");
    user.setUsername("AAA Default User");
    user.setPassword("thisisaverysecurepassword");
    user.setSalt("thisisaveryrandomsalt");
    user.setStatus((byte) 1);
    user.setSuperadmin(false);
    user.setExternal(false);
    return user;
  }

  public List<String> createUrls() {
    List<String> urls = new ArrayList<>();
    urls.add("url1");
    urls.add("url2");

    return urls;
  }

  public WdApplication createWdApplication(String proxyPath, String appUrl) {
    WdApplication wdApplication = new WdApplication();

    wdApplication.setId("0f9a2472-cde0-44a6-ba3d-8e60992904fb");
    wdApplication.setProxyAppPath(proxyPath);
    wdApplication.setAppUrl(appUrl);

    wdApplication.setAddedOn(new Long("2121545432165"));
    wdApplication.setLastDeployedOn(new Long("2121545432165"));
    wdApplication.setVersion("2.0.0");
    wdApplication.setApplicationName("usermanagement");
    wdApplication.setGroupName("groupName");
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

  public List<WdApplication> createWdApplications() {
    List<WdApplication> wdApplications = new ArrayList<>();
    wdApplications.add(createWdApplication("a_path", "appUrl"));
    wdApplications.add(createWdApplication("", ""));
    wdApplications.add(createWdApplication("a_path", ""));
    return wdApplications;
  }

}
