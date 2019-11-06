package com.eurodyn.qlack.webdesktop.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for WdApplication.
 *
 * @author European Dynamics SA.
 */
@Getter
@Setter
public class WdApplicationDTO {

  /**
   * Id
   */
  private String id;

  /**
   * Title
   */
  private String titleKey;

  /**
   * Description
   */
  private String descriptionKey;

  /**
   * Version
   */
  private String version;

  /**
   * Path
   */
  private String path;

  /**
   * Presentation order index No.
   */
  private String appIndex;

  /**
   * Background color
   */
  private String bgColor;

  /**
   * Whether multiple instance of this application are allowed to run
   */
  private boolean multipleInstances;

  /**
   * Whether the access is restricted for this application
   */
  private boolean restrictAccess;

  /**
   * Icon
   */
  private String icon;

  /**
   * Thumb icon
   */
  private String iconSmall;

  /**
   * Width of the application window
   */
  private int width;

  /**
   * Minimum width of the application window
   */
  private int minWidth;

  /**
   * Height of the application window
   */
  private int height;

  /**
   * Minimum height of the application window
   */
  private int minHeight;

  /**
   * Whether the application window is resizable
   */
  private boolean resizable;

  /**
   * Whether the application window is minimizable
   */
  private boolean minimizable;

  /**
   * Whether the application window is maximizable
   */
  private boolean maximizable;

  /**
   * Whether the application window is closable
   */
  private boolean closable;

  /**
   * Whether the application window is draggable
   */
  private boolean draggable;

  /**
   * The translations group
   */
  private String translationsGroup;

  /**
   * Symbolic name for the application bundle
   */
  private String bundleSymbolicName;

  /**
   * Whether the application is active
   */
  private boolean active;

  /**
   * The date the application was added on
   */
  private Date addedOn;

  /**
   * The date the application was last deployed on
   */
  private Date lastDeployedOn;

  /**
   * System
   */
  private boolean system;

  /**
   * Whether the title is shown on the application window
   */
  private boolean showTitle;

  /**
   * File sha256 checksum
   */
  private String checksum;

  /***
   * The name of the group that might the application belongs to.
   */
  private String groupName;

  /**
   * Set the {@link java.util.Date} based on a long value
   * @param addedOn the date as a {@link Long} value
   */
  public void setAddedOn(Long addedOn) {
    if (addedOn != null) {
      this.addedOn = new Date(addedOn);
    }
  }

  /**
   * Set the {@link java.util.Date} based on a long value
   * @param lastDeployedOn the date as a {@link Long} value
   */
  public void setLastDeployedOn(Long lastDeployedOn) {
    if (lastDeployedOn != null) {
      this.lastDeployedOn = new Date(lastDeployedOn);
    }
  }
}
