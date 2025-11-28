package com.claim.claim.dto;

public class FilterReqDto {
  private String status;
  private String dateFrom;
  private String dateTo;
  private Double  minAmount;
  private Double  maxAmount;

  // Getters and Setters
  public String getStatus() {return status;}
  public void setStatus(String status) {this.status = status;}

  public String getDateFrom() {return dateFrom;}
  public void setDateFrom(String dateFrom) {this.dateFrom = dateFrom;}

  public String getDateTo() {return dateTo;}
  public void setDateTo(String dateTo) {this.dateTo = dateTo;}

  public Double  getMinAmount() {return minAmount;}
  public void setMinAmount(Double  minAmount) {this.minAmount = minAmount;}

  public Double  getMaxAmount() {return maxAmount;}
  public void setMaxAmount(Double  maxAmount) {this.maxAmount = maxAmount;}
}

