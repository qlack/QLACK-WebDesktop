package com.eurodyn.qlack.webdesktop.common.service;

import com.eurodyn.qlack.fuse.aaa.dto.ResourceDTO;
import com.eurodyn.qlack.fuse.aaa.service.ResourceService;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for create a resourceDTO object based on new application's id.
 */
@Service
public class ResourceWdApplicationService {

  @Autowired
  private ResourceService resourceService;

  public String createApplicationResource(WdApplication wdApplication) {
    return resourceService.createResource(createResourceDTO(wdApplication));
  }

  /**
   * Creates a new resourceDTO based on a new application. Every new application must be connected to a resource id so
   * as to apply permissions.
   *
   * @param wdApplication the new application.
   * @return the new resourceDTO.
   */
  protected ResourceDTO createResourceDTO(WdApplication wdApplication) {
    ResourceDTO resourceDTO = new ResourceDTO();
    resourceDTO.setObjectId(wdApplication.getId());
    resourceDTO.setName(wdApplication.getApplicationName());
    return resourceDTO;
  }
}
