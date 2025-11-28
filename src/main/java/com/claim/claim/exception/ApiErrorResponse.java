package com.claim.claim.exception;

import java.util.Date;

public class ApiErrorResponse {

  private Date timestamp;
  private int status;
  private String error;
  private String message;
  private String path;

  // --- Constructors ---
  public ApiErrorResponse() {}

  public ApiErrorResponse(Date timestamp, int status, String error, String message, String path) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
  }

  // --- Getters ---
  public Date getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  // --- Setters ---
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setPath(String path) {
    this.path = path;
  }

  // --- toString() ---
  @Override
  public String toString() {
    return "ApiErrorResponse{"
        + "timestamp="
        + timestamp
        + ", status="
        + status
        + ", error='"
        + error
        + '\''
        + ", message='"
        + message
        + '\''
        + ", path='"
        + path
        + '\''
        + '}';
  }

  // --- Manual Builder ---
  public static class ApiErrorResponseBuilder {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ApiErrorResponseBuilder timestamp(Date timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public ApiErrorResponseBuilder status(int status) {
      this.status = status;
      return this;
    }

    public ApiErrorResponseBuilder error(String error) {
      this.error = error;
      return this;
    }

    public ApiErrorResponseBuilder message(String message) {
      this.message = message;
      return this;
    }

    public ApiErrorResponseBuilder path(String path) {
      this.path = path;
      return this;
    }

    public ApiErrorResponse build() {
      return new ApiErrorResponse(timestamp, status, error, message, path);
    }
  }

  // --- Static builder method ---
  public static ApiErrorResponseBuilder builder() {
    return new ApiErrorResponseBuilder();
  }
}
