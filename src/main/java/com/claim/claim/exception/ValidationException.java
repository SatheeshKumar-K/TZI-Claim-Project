package com.claim.claim.exception;

/** The type Validation exception. */
public class ValidationException extends RuntimeException {

  /**
   * Instantiates a new Validation exception.
   *
   * @param message the message
   */
  public ValidationException(final String message) {
    super((message));
  }
}
