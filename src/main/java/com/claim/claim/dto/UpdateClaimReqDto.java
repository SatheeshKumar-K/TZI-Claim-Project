package com.claim.claim.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class UpdateClaimReqDto {
  @NotNull(message = "ClaimantName must not be Null | Empty | Blank")
  private String claimantName;

  @NotNull(message = "claimAmount must not be Null | Empty | Blank")
  private Double claimAmount;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate claimDate;
  private String notes;

  //Getter Setter
  public String getClaimantName() {return claimantName;}
  public void setClaimantName(String claimantName) {this.claimantName = claimantName;}

  public Double getClaimAmount() {return claimAmount;}
  public void setClaimAmount(Double claimAmount) {this.claimAmount = claimAmount;}

  public LocalDate getClaimDate() {return claimDate;}
  public void setClaimDate(LocalDate claimDate) {this.claimDate = claimDate;}

  public String getNotes() {return notes;}
  public void setNotes(String notes) {this.notes = notes;}
}
