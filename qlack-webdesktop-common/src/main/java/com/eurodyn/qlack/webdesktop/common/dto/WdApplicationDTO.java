package com.eurodyn.qlack.webdesktop.common.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
   * Version
   */
  @NotNull
  private String version;

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
   * The application name
   */
  @NotNull
  private String applicationName;


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

  /***
   * If the application is created or edited by UI.
   */
  private boolean editedByUI;

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
   * Reverse proxy path
   */
  @NotNull
  private String proxyPath;

  /**
   * Reverse proxy application url
   */
  @NotNull
  private String proxyAppUrl;

  /**
   * Presentation order index No.
   */
  @NotNull
  private String appUrl;

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
