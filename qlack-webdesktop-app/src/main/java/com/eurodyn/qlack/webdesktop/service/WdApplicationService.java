package com.eurodyn.qlack.webdesktop.service;

        import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
        import com.eurodyn.qlack.webdesktop.mapper.WdApplicationMapper;
        import com.eurodyn.qlack.webdesktop.model.WdApplication;
        import com.eurodyn.qlack.webdesktop.repository.WdApplicationRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.validation.annotation.Validated;

        import java.util.List;

/**
 * Provides Qlack Web Desktop application related functionality
 *
 * @author European Dynamics SA.
 */
@Service
@Validated
public class WdApplicationService {

  private WdApplicationMapper mapper;
  private WdApplicationRepository wdApplicationRepository;

  @Autowired
  public WdApplicationService(WdApplicationMapper mapper,
                              WdApplicationRepository wdApplicationRepository) {
    this.mapper = mapper;
    this.wdApplicationRepository = wdApplicationRepository;
  }

  /**
   * Finds all active Web Desktop applications
   *
   * @return a list of {@link com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplications() {
    return mapper.mapToDTO(wdApplicationRepository.findByActiveIsTrue());
  }

  /**
   * Finds all active Web Desktop applications with trimmed group names.
   *
   * @return a list of {@link com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO}
   */
  public List<WdApplicationDTO> findAllActiveApplicationsFilterGroupName() {
    List<WdApplication> wdApplicationList = wdApplicationRepository.findByActiveIsTrue();
    wdApplicationList.forEach(wdApplication -> {
      if (wdApplication.getGroupName() != null) {
            wdApplication.setGroupName(wdApplication.getGroupName().trim());
        }
        if ("null".equals(wdApplication.getGroupName())) {
            wdApplication.setGroupName("");
        }
    });
    return mapper.mapToDTO(wdApplicationList);
  }

}
