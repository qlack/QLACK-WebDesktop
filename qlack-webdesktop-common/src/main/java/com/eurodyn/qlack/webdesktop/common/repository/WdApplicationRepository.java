package com.eurodyn.qlack.webdesktop.common.repository;

import com.eurodyn.qlack.common.repository.QlackBaseRepository;
import com.eurodyn.qlack.webdesktop.common.model.WdApplication;

import java.util.List;

/**
 * Repository interface for <tt>WdApplication</tt> entities
 *
 * @author European Dynamics SA.
 */
public interface WdApplicationRepository extends QlackBaseRepository<WdApplication, String> {

  /**
   * Finds all active Web Desktop applications.
   *
   * @return a list of {@link WdApplication}
   */
  List<WdApplication> findByActiveIsTrue();

  /**
   * Finds a Web Desktop application by its translation group key attribute.
   *
   * @return a {@link WdApplication}
   */
  WdApplication findByApplicationName(String applicationName);

  /**
   * Finds all system Web Desktop applications.
   *
   * @param system a boolean value that corresponds to the question if the application is system or not.
   * @return a list of system applications.
   */
  List<WdApplication> findBySystemAndActiveIsTrue(Boolean system);
}
