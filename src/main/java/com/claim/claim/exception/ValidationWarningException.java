package com.claim.claim.exception;

/** The app warning exception. */
public class ValidationWarningException extends RuntimeException {

  /**
   * Instantiates a new Validation exception.
   *
   * @param message the message
   */
  public ValidationWarningException(final String message) {
    super(message);
  }
}
