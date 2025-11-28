package com.claim.claim.dto;

import org.springframework.http.HttpStatus;

public class CommonResponse {
  private boolean success;
  private String message;
  private HttpStatus code;
  private Object data;

  // Getters and Setters
  public boolean isSuccess() {return success;}
  public void setSuccess(boolean success) {this.success = success;}

  public Object getData() {return data;}
  public void setData(Object data) {this.data = data;}

  public HttpStatus getCode() {return code;}
  public void setCode(HttpStatus code) {this.code = code;}

  public String getMessage() {return message;}
  public void setMessage(String message) {this.message = message;}
}
