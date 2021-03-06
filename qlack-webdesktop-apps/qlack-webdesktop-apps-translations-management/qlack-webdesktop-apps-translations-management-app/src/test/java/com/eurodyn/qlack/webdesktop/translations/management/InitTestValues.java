package com.eurodyn.qlack.webdesktop.translations.management;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.GroupDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.KeyDTO;
import com.eurodyn.qlack.fuse.lexicon.dto.LanguageDTO;
import com.eurodyn.qlack.fuse.lexicon.model.Data;
import com.eurodyn.qlack.fuse.lexicon.model.Group;
import com.eurodyn.qlack.fuse.lexicon.model.Key;
import com.eurodyn.qlack.fuse.lexicon.model.Language;
import com.eurodyn.qlack.webdesktop.translations.management.dto.TmKeyDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author European Dynamics
 */
public class InitTestValues {

  public Language createEnglishLanguage() {
    Language englishLang = new Language();
    englishLang.setId("71df58f1-be26-410a-94ca-cfc90ac955a4");
    englishLang.setLocale("en");
    englishLang.setName("English");
    return englishLang;
  }

  public LanguageDTO createEnglishLanguageDTO() {
    LanguageDTO englishLangDTO = new LanguageDTO();
    englishLangDTO.setId("71df58f1-be26-410a-94ca-cfc90ac955a4");
    englishLangDTO.setLocale("en");
    englishLangDTO.setName("English");
    return englishLangDTO;
  }

  public Language createPortugueseLanguage() {
    Language portugueseLang = new Language();
    portugueseLang.setId("12df58f1-be26-410a-94ca-cfc90ac955a4");
    portugueseLang.setName("Portuguese");
    portugueseLang.setLocale("pt");
    portugueseLang.setActive(true);
    return portugueseLang;
  }

  public LanguageDTO createPortugueseLanguageDTO() {
    LanguageDTO portugueseLangDTO = new LanguageDTO();
    portugueseLangDTO.setId("12df58f1-be26-410a-94ca-cfc90ac955a4");
    portugueseLangDTO.setName("Portuguese");
    portugueseLangDTO.setLocale("pt");
    portugueseLangDTO.setActive(true);
    return portugueseLangDTO;
  }

  public List<LanguageDTO> createLanguagesDTO() {
    List<LanguageDTO> languagesDTO = new ArrayList<>();
    languagesDTO.add(createEnglishLanguageDTO());
    languagesDTO.add(createPortugueseLanguageDTO());
    return languagesDTO;
  }

  public Group createGroup() {
    Group group = new Group();
    group.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    group.setTitle("Application UI");
    group.setDescription("description");
    return group;
  }

  public Key createKey() {
    Key key = new Key();
    key.setId("0f2f12f8-4902-4355-ae52-20ccf92db2f3");
    key.setGroup(createGroup());
    key.setName("attachment_desc");
    key.setData(createDataList());
    return key;
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

  public TmKeyDTO createTmKeyDTO() {
    TmKeyDTO tmKeyDTO = new TmKeyDTO();
    tmKeyDTO.setId("0f2f12f8-4902-4355-ae52-20ccf92db2f3");
    tmKeyDTO.setGroupId(createGroup().getId());
    tmKeyDTO.setName("attachment_desc");
    tmKeyDTO.setGroupName("groupName");
    Map<String, String> translations = new HashMap<>();
    translations.put("777119b0-bda0-4e87-9d3b-08d80e9bb9e8",
        "Add attachment description");
    translations.put("71df58f1-be26-410a-94ca-cfc90ac955a4",
        "Adicionar descrição do anexo");
    tmKeyDTO.setTranslations(translations);

    return tmKeyDTO;
  }


  public List<KeyDTO> createKeysDTO() {
    List<KeyDTO> keysDTO = new ArrayList<>();
    keysDTO.add(createKeyDTO());

    KeyDTO key2 = createKeyDTO();
    key2.setId("ec5efa0b-5f6e-4059-9ae4-d262180a61d5");
    key2.setName("change_password");
    Map<String, String> translations = new HashMap<>();
    translations.put("777119b0-bda0-4e87-9d3b-08d80e9bb9e8", "Change password");
    translations.put("71df58f1-be26-410a-94ca-cfc90ac955a4", "Mudar senha");

    keysDTO.add(key2);
    return keysDTO;
  }

  public List<Key> createKeys() {
    List<Key> keys = new ArrayList<>();
    keys.add(createKey());

    Key key2 = createKey();
    key2.setId("ec5efa0b-5f6e-4059-9ae4-d262180a61d5");
    key2.setName("change_password");

    keys.add(key2);
    return keys;
  }

  public Data createData() {
    Data data = new Data();
    data.setId("93efd573-e3e8-49e7-9740-8a341d0e5b84");
    data.setLanguage(createEnglishLanguage());
    data.setValue("Add attachment description");
    data.setLastUpdatedOn(1550527200000L);
    return data;
  }

  public List<Data> createDataList() {
    List<Data> dataList = new ArrayList<>();
    dataList.add(createData());

    Data data2 = createData();
    data2.setLanguage(createPortugueseLanguage());
    data2.setId("2d4a5be3-de1e-40d1-b5b8-eb386bd5d8c8");
    data2.setLastUpdatedOn(1550649743302L);
    data2.setValue("Adicionar descrição do anexo");
    dataList.add(data2);

    return dataList;
  }

  public List<GroupDTO> createGroupsDTO() {
    List<GroupDTO> groupsDTO = new ArrayList<>();
    groupsDTO.add(createGroupDTO());

    GroupDTO groupDTO2 = createGroupDTO();
    groupDTO2.setId("07a68e66-d4fa-4070-ae49-826be4111eb52");
    groupDTO2.setTitle("Application Reports");
    groupsDTO.add(groupDTO2);

    GroupDTO groupDTO3 = createGroupDTO();
    groupDTO3.setId("12a68e66-d4fa-4070-ae49-826be4111eb10");
    groupDTO3.setTitle("Application Appendix");
    groupsDTO.add(groupDTO3);

    return groupsDTO;
  }

  public GroupDTO createGroupDTO() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId("06b68e66-d4fa-4070-ae49-826be499eb41");
    groupDTO.setTitle("Application UI");
    groupDTO.setDescription("description");
    return groupDTO;
  }

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

}
