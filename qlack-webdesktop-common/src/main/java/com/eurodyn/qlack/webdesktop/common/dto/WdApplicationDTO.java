package com.eurodyn.qlack.webdesktop.common.dto;

import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
   * Placeholder for title when created from UI.
   */
  @NotNull
  private String title;

  /**
   * Placeholder for description when created from UI.
   */
  @NotNull
  private String description;


  /**
   * The application name
   */
  @NotNull
  private String applicationName;

  /***
   * The name of the group that might the application belongs to.
   */
  private String groupName;

  /**
   * The url in which the application is hosted
   */
  @NotNull
  private String appUrl;

  /**
   * The path of the home page of the application
   */
  @NotNull
  private String appPath;

  /**
   * Icon
   */
  @NotNull
  private String icon;

  /**
   * Thumb icon
   */
  @NotNull
  private String iconSmall;

  /**
   * Width of the application window
   */
  @Min(value = 50)
  private int width;

  /**
   * Minimum width of the application window
   */
  @Min(value = 50)
  private int minWidth;

  /**
   * Height of the application window
   */
  @Min(value = 50)
  private int height;

  /**
   * Minimum height of the application window
   */
  @Min(value = 50)
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
   * Whether multiple instance of this application are allowed to run
   */
  private boolean multipleInstances;

  /**
   * Whether the title is shown on the application window
   */
  private boolean showTitle;

  /**
   * Whether the access is restricted for this application
   */
  private boolean restrictAccess;

  /**
   * Whether the application is active
   */
  private boolean active;

  /**
   * System
   */
  private boolean system;

  /**
   * Reverse proxy path
   */
  private String proxyAppPath;

  /**
   * Reverse proxy application url
   */
  private String proxyAppUrl;

  /**
   * File sha256 checksum
   */
  private String checksum;

  /**
   * The date the application was added on
   */
  private Date addedOn;

  /**
   * The date the application was last deployed on
   */
  private Date lastDeployedOn;

  /***
   * If the application is created or edited by UI.
   */
  private boolean editedByUI;

  /**
   * Version
   */
  @NotNull
  private String version;

  /**
   * Set the {@link java.util.Date} based on a long value
   *
   * @param addedOn the date as a {@link Long} value
   */
  public void setAddedOn(Long addedOn) {
    if (addedOn != null) {
      this.addedOn = new Date(addedOn);
    }
  }

  /**
   * Set the {@link java.util.Date} based on a long value
   *
   * @param lastDeployedOn the date as a {@link Long} value
   */
  public void setLastDeployedOn(Long lastDeployedOn) {
    if (lastDeployedOn != null) {
      this.lastDeployedOn = new Date(lastDeployedOn);
    }
  }
}
