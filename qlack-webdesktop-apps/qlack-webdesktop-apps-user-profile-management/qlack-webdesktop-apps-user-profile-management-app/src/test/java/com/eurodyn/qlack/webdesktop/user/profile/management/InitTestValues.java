package com.eurodyn.qlack.webdesktop.user.profile.management;

import com.eurodyn.qlack.fuse.aaa.dto.UserAttributeDTO;

import java.util.ArrayList;
import java.util.List;

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

}
