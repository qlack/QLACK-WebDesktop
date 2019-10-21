package com.eurodyn.qlack.webdesktop.exception;

import com.eurodyn.qlack.common.exception.QException;

/**
 * Exceptions wrapper for Qlack Web Desktop module.
 *
 * @author European Dynamics SA.
 */
public class WdException extends QException {

  private static final long serialVersionUID = 1L;

  public WdException() {
  }

  public WdException(String message) {
    super(message);
  }

  public WdException(String message, Throwable cause) {
    super(message, cause);
  }

  public WdException(Throwable cause) {
    super(cause);
  }


}
