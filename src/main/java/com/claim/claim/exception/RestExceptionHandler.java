package com.claim.claim.exception;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/** The type Rest exception handler. */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Bad request exception handler api error response.
   *
   * @param ex the ex
   * @return the api error response
   */
  @ExceptionHandler(value = {ValidationException.class, IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse badRequestExceptionHandler(final RuntimeException ex) {
    return ApiErrorResponse.builder()
        .error(HttpStatus.BAD_REQUEST.name())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(ex.getMessage())
        .timestamp(new Date())
        .build();
  }

  /**
   * Internal server exception handler api error response.
   *
   * @param ex the ex
   * @return the api error response
   */
  @ExceptionHandler(ServiceException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse internalServerExceptionHandler(final ServiceException ex) {
    return ApiErrorResponse.builder()
        .error(HttpStatus.INTERNAL_SERVER_ERROR.toString())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(ex.getMessage())
        .timestamp(new Date())
        .build();
  }

  /**
   * Bad request exception handler api error response.
   *
   * @param ex the ex
   * @return the api error response
   */
  @ExceptionHandler(ValidationWarningException.class)
  @ResponseStatus(HttpStatus.OK)
  public ApiErrorResponse validationWarningExceptionHandler(final ValidationWarningException ex) {
    return ApiErrorResponse.builder()
        .error(HttpStatus.BAD_REQUEST.name())
        .status(HttpStatus.BAD_REQUEST.value())
        .message(ex.getMessage())
        .timestamp(new Date())
        .build();
  }
}
