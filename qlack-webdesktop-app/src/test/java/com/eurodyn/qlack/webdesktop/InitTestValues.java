package com.eurodyn.qlack.webdesktop;

import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.model.WdApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test values initialization class
 *
 * @author European Dynamics
 */
public class InitTestValues {

  public WdApplication createWdApplication() {
    WdApplication wdApplication = new WdApplication();

    wdApplication.setId("0f9a2472-cde0-44a6-ba3d-8e60992904fb");
    wdApplication.setTitleKey("application_title");
    wdApplication.setDescriptionKey("application_description");
    wdApplication.setAppIndex("apps/usermanagement/");
    wdApplication.setPath("a_path");

    wdApplication.setAddedOn(new Long("2121545432165"));
    wdApplication.setLastDeployedOn(new Long("2121545432165"));
    wdApplication.setVersion("2.0.0");
    wdApplication.setBgColor("bg-indigo");
    wdApplication.setBundleSymbolicName("com.eurodyn.qlack2.webdesktop.apps.qlack2-wdapps-user-management-imp");
    wdApplication.setTranslationsGroup("usermanagement");

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
    wdApplicationDTO.setTitleKey("application_title");
    wdApplicationDTO.setDescriptionKey("application_description");
    wdApplicationDTO.setAppIndex("apps/usermanagement/");
    wdApplicationDTO.setPath("a_path");

    wdApplicationDTO.setAddedOn(new Long("2121545432165"));
    wdApplicationDTO.setLastDeployedOn(new Long("2121545432165"));
    wdApplicationDTO.setVersion("2.0.0");
    wdApplicationDTO.setBgColor("bg-indigo");
    wdApplicationDTO.setBundleSymbolicName("com.eurodyn.qlack2.webdesktop.apps.qlack2-wdapps-user-management-imp");
    wdApplicationDTO.setTranslationsGroup("usermanagement");

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
}
