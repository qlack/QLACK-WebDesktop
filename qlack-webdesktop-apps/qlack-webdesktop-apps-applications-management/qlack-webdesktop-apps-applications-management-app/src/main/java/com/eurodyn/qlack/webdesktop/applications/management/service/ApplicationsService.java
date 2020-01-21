package com.eurodyn.qlack.webdesktop.applications.management.service;

import com.eurodyn.qlack.fuse.crypto.service.CryptoDigestService;
import com.eurodyn.qlack.webdesktop.applications.management.util.ProcessLexiconUtil;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import com.eurodyn.qlack.webdesktop.common.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.common.mapper.WdApplicationMapper;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;
import com.eurodyn.qlack.webdesktop.common.repository.WdApplicationRepository;
import com.eurodyn.qlack.webdesktop.common.service.WdApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * This Service method contains all the actions related to the applications. It uses the related
 * Service of the QLACK WebDesktop Service.
 *
 * @author EUROPEAN DYNAMICS SA
 */
@Service
@RequiredArgsConstructor
public class ApplicationsService {

  private WdApplicationService wdApplicationService;
  private WdApplicationRepository wdApplicationRepository;
  private WdApplicationMapper mapper;
  private CryptoDigestService cryptoDigestService;
  private ProcessLexiconUtil processLexiconUtil;

  @Autowired
  public ApplicationsService(WdApplicationService wdApplicationService,
      WdApplicationRepository wdApplicationRepository, ProcessLexiconUtil processLexiconUtil,
      CryptoDigestService cryptoDigestService, WdApplicationMapper mapper) {
    this.wdApplicationService = wdApplicationService;
    this.wdApplicationRepository = wdApplicationRepository;
    this.cryptoDigestService = cryptoDigestService;
    this.processLexiconUtil = processLexiconUtil;
    this.mapper = mapper;
  }

  /**
   * This method returns all the QLACK Web Desktop applications.
   *
   * @return a list containing all the applications
   */
  public Page<WdApplicationDTO> getApplications() {
    return new PageImpl<>(wdApplicationService.findAllApplications());
  }

  /**
   * This method returns a QLACK Web Desktop application by id.
   *
   * @return a single application
   */
  public WdApplicationDTO getApplicationById(String id) {
    return wdApplicationService.findApplicationById(id);
  }

  /**
   * Finds all translations from all groups for a specific locale
   *
   * @param locale the language locale
   * @return a list of translations for a specific locale
   */
  public Map<String, Map<String, String>> getTranslations(String locale) {
    return wdApplicationService.findTranslationsForLocale(locale);
  }

  /**
   * Finds an application by giving the application Name.
   *
   * @param applicationName the application Name field.
   * @return the webdesktop application that has been retrieved.
   */
  public WdApplication findApplicationByName(String applicationName) {
    return wdApplicationService.findApplicationByName(applicationName);
  }

  /**
   * Saves a new wd application or updates an existing one. Checks by id and by name if the
   * application exists, if so only two fields are going to be updated. If id and name are not
   * existed, a new application is going to be saved. An extra check must be done, in order to make
   * sure that application Name is unique.
   *
   * @param wdApplicationDTO the application to be saved/updated
   * @return
   */
  public ResponseEntity updateApplication(WdApplicationDTO wdApplicationDTO) {
    WdApplication wdApplicationByName = findApplicationByName(
        wdApplicationDTO.getApplicationName());

    //if the application is new but the application name already exists.
    if (wdApplicationDTO.getId() == null && wdApplicationByName != null && wdApplicationDTO
        .getApplicationName()
        .equals(wdApplicationByName.getApplicationName())) {
      return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("alreadyExistsCode");
    }
    //application exists but only 2 fields will be updated
    if (wdApplicationDTO.getId() != null) {
      wdApplicationByName.setActive(wdApplicationDTO.isActive());
      wdApplicationByName.setRestrictAccess(wdApplicationDTO.isRestrictAccess());
    } else {
      wdApplicationByName = mapper.mapToEntity(wdApplicationDTO);
    }
    List<LexiconDTO> lexiconValues = processLexiconUtil.createLexiconList(wdApplicationDTO);
    processLexiconUtil.createLexiconValues(lexiconValues, wdApplicationDTO);
    wdApplicationService.saveApplication(wdApplicationByName);
    return ResponseEntity.status(HttpStatus.CREATED).body(wdApplicationByName);
  }

  /**
   * Saves a new application in database from yaml file.
   *
   * @param file the yaml file that has been uploaded.
   */
  public void saveApplicationFromYaml(MultipartFile file) {
    try {
      byte[] bytes = file.getBytes();
      Path path = Paths.get(file.getOriginalFilename());
      path.toString();
      Files.write(path, bytes);

      //Map to wdapplication
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
      WdApplication wdApplication = mapper.readValue(file.getInputStream(), WdApplication.class);
      handleWdApplication(file.getInputStream(), wdApplication);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleWdApplication(InputStream is, WdApplication wdApplication) throws IOException {
    WdApplication existingWdApp = wdApplicationRepository
        .findByApplicationName(wdApplication.getApplicationName());
    String sha256 = cryptoDigestService.sha256(is);

    if (existingWdApp == null) {
      processWdApplication(wdApplication, sha256);
      processLexiconUtil
          .createLexiconValues(wdApplication.getLexicon(), this.mapper.mapToDTO(wdApplication));

    } else if (!existingWdApp.getChecksum().equals(sha256)) {

      if (existingWdApp.isEditedByUI()) {
        wdApplication.setActive(existingWdApp.isActive());
        wdApplication.setRestrictAccess(existingWdApp.isRestrictAccess());
      }
      wdApplication.setId(existingWdApp.getId());
      processWdApplication(wdApplication, sha256);
      processLexiconUtil
          .createLexiconValues(wdApplication.getLexicon(), this.mapper.mapToDTO(wdApplication));
    }
  }

  /**
   * Updates the file's SHA-256 checksum, saves the Web Desktop application and registers
   *
   * @param wdApplication The Web Desktop application
   * @param checksum      The file's SHA-256 checksum
   */
  private void processWdApplication(WdApplication wdApplication, String checksum) {
    if (wdApplication != null) {
      wdApplication.setChecksum(checksum);
      wdApplicationRepository.save(wdApplication);
    }
  }
}
