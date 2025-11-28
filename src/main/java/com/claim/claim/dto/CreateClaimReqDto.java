package com.claim.claim.dto;

import jakarta.validation.constraints.NotNull;

public class CreateClaimReqDto {
  @NotNull(message = "ClaimantName must not be Null | Empty | Blank")
  private String claimantName;

  private Double claimAmount;

  private String notes;

  // Getters and Setters
  public String getClaimantName() { return claimantName; }
  public void setClaimantName(String claimantName) { this.claimantName = claimantName; }

  public Double getClaimAmount() { return claimAmount; }
  public void setClaimAmount(Double claimAmount) { this.claimAmount = claimAmount; }

  public String getNotes() { return notes; }
  public void setNotes(String notes) { this.notes = notes; }
}
