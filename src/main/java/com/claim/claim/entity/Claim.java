package com.claim.claim.entity;

import com.claim.claim.utils.ClaimStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "claim")
public class Claim {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "claim_number", unique = true, nullable = false)
  private String claimNumber;

  @Column(name = "claimant_name", nullable = false)
  private String claimantName;

  @Column(name = "claim_amount", nullable = false)
  private Double claimAmount = 0.0;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Column(name = "claim_date", nullable = false)
  private LocalDate claimDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ClaimStatus status;

  @Column(name = "notes")
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

  public ClaimStatus getStatus() {return status;}
  public void setStatus(ClaimStatus status) {this.status = status;}

  public String getNotes() {return notes;}
  public void setNotes(String notes) {this.notes = notes;}
  }