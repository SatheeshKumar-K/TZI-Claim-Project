package com.claim.claim.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class FilterResDto {
  private Long id;
  private String claimNumber;
  private String claimantName;
  private Double claimAmount;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate claimDate;
  private String status;
  private String notes;

  // Getters and Setters
  public Long getId() {return id;}
  public void setId(Long id) {this.id = id;}

  public String getClaimNumber() {return claimNumber;}
  public void setClaimNumber(String claimNumber) {this.claimNumber = claimNumber;}

  public String getClaimantName() {return claimantName;}
  public void setClaimantName(String claimantName) {this.claimantName = claimantName;}

  public Double getClaimAmount() {return claimAmount;}
  public void setClaimAmount(Double claimAmount) {this.claimAmount = claimAmount;}

  public LocalDate getClaimDate() {return claimDate;}
  public void setClaimDate(LocalDate claimDate) {this.claimDate = claimDate;}

  public String getStatus() {return status;}
  public void setStatus(String status) {this.status = status;}

  public String getNotes() {return notes;}
  public void setNotes(String notes) {this.notes = notes;}
}
