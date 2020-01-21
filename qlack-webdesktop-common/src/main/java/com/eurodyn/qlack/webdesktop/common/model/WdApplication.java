package com.eurodyn.qlack.webdesktop.common.model;

import com.eurodyn.qlack.common.model.QlackBaseModel;
import com.eurodyn.qlack.webdesktop.common.dto.LexiconDTO;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class represents a Qlack Web Desktop application
 *
 * @author European Dynamics
 */
@Entity
@Table(name = "wd_application")
@Getter
@Setter
public class WdApplication extends QlackBaseModel {

  /**
   * Version
   */
  @Basic
  @Column(name = "version", nullable = false, length = 32)
  private String version;

  /**
   * Whether multiple instance of this application are allowed to run
   */
  @Basic
  @Column(name = "multiple_instances", nullable = false)
  private boolean multipleInstances;

  /**
   * Whether the access is restricted for this application
   */
  @Basic
  @Column(name = "restrict_access", nullable = false)
  private boolean restrictAccess;

  /**
   * Icon
   */
  @Basic
  @Column(name = "icon", nullable = false, length = 256)
  private String icon;

  /**
   * Thumb icon
   */
  @Basic
  @Column(name = "icon_small", nullable = false, length = 256)
  private String iconSmall;

  /**
   * Width of the application window
   */
  @Basic
  @Column(name = "width", nullable = false)
  private int width;

  /**
   * Minimum width of the application window
   */
  @Basic
  @Column(name = "min_width", nullable = false)
  private int minWidth;

  /**
   * Height of the application window
   */
  @Basic
  @Column(name = "height", nullable = false)
  private int height;

  /**
   * Minimum height of the application window
   */
  @Basic
  @Column(name = "min_height", nullable = false)
  private int minHeight;

  /**
   * Whether the application window is resizable
   */
  @Basic
  @Column(name = "resizable", nullable = false)
  private boolean resizable;

  /**
   * Whether the application window is minimizable
   */
  @Basic
  @Column(name = "minimizable", nullable = false)
  private boolean minimizable;

  /**
   * Whether the application window is maximizable
   */
  @Basic
  @Column(name = "maximizable", nullable = false)
  private boolean maximizable;

  /**
   * Whether the application window is closable
   */
  @Basic
  @Column(name = "closable", nullable = false)
  private boolean closable;

  /**
   * Whether the application window is draggable
   */
  @Basic
  @Column(name = "draggable", nullable = false)
  private boolean draggable;

  /**
   * The translations group
   */
  @Basic
  @Column(name = "application_name", unique = true)
  private String applicationName;

  /**
   * List of translations
   */
  @Transient
  private transient List<LexiconDTO> lexicon;

  /**
   * Whether the application is active
   */
  @Basic
  @Column(name = "is_active", nullable = false)
  private boolean active;

  /**
   * The date the application was added on
   */
  @Basic
  @Column(name = "added_on", nullable = false)
  private long addedOn;

  /**
   * The date the application was last deployed on
   */
  @Basic
  @Column(name = "last_deployed_on", nullable = false)
  private long lastDeployedOn;

  /**
   * System
   */
  @Basic
  @Column(name = "is_system", nullable = false)
  private boolean system;

  /**
   * Whether the title is shown on the application window
   */
  @Basic
  @Column(name = "show_title", nullable = false)
  private boolean showTitle;

  /**
   * File sha256 checksum
   */
  @Basic
  @Column(name = "checksum", nullable = false, length = 256)
  private String checksum;

  /**
   * The name of the group that might the application belongs to.
   */
  @Basic
  @Column(name = "group_name", length = 256)
  private String groupName;

  /**
   * Strip reverse proxy path prefix when forwarding to the matching url (appUrl)
   */
  @Basic
  @Column(name = "strip_prefix", nullable = false)
  private boolean stripPrefix;

  /**
   * Sensitive headers that the reverse proxy should allow for authentication or other reasons
   */
  @Basic
  @Column(name = "sensitive_headers", length = 256)
  private String sensitiveHeaders;

  /**
   * If the app is edited or created by UI.
   */
  @Basic
  @Column(name = "edited_by_ui", nullable = false)
  private boolean editedByUI;

  /**
   * Reverse proxy path rule
   */
  @Basic
  @Column(name = "proxy_path", length = 256)
  private String proxyPath;

  /**
   * Reverse proxy application url
   */
  @Basic
  @Column(name = "proxy_app_url", length = 256)
  private String proxyAppUrl;

  /**
   * Presentation order index No.
   */
  @Basic
  @Column(name = "app_url", length = 256)
  private String appUrl;

}
