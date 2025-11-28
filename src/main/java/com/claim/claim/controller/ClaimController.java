package com.claim.claim.controller;

import com.claim.claim.dto.CreateClaimReqDto;
import com.claim.claim.dto.CommonResponse;
import com.claim.claim.dto.UpdateClaimReqDto;
import com.claim.claim.dto.UpdateStatusReqDto;
import com.claim.claim.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

  @Autowired private ClaimService claimService;

  /** create the status Claim. */
  @PostMapping
  public ResponseEntity<CommonResponse> createClaim(
      @Valid @RequestBody CreateClaimReqDto createClaimRequest, final BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      for (Object error : bindingResult.getAllErrors()) {
        if (error instanceof FieldError fieldError) {
          CommonResponse response = new CommonResponse();
          response.setData(null);
          response.setMessage(fieldError.getDefaultMessage());
          response.setSuccess(false);
          response.setCode(HttpStatus.BAD_REQUEST);
          return ResponseEntity.badRequest().body(response);
        }
      }
    }
    return claimService.createClaim(createClaimRequest);
  }

  /** update the Claim. */
  @PutMapping("/{id}")
  public ResponseEntity<CommonResponse> updateClaim(
      @PathVariable("id") Long id,
      @Valid @RequestBody UpdateClaimReqDto updateClaimRequest,
      final BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      for (Object error : bindingResult.getAllErrors()) {
        if (error instanceof FieldError fieldError) {
          CommonResponse response = new CommonResponse();
          response.setData(null);
          response.setMessage(fieldError.getDefaultMessage());
          response.setSuccess(false);
          response.setCode(HttpStatus.BAD_REQUEST);
          return ResponseEntity.badRequest().body(response);
        }
      }
    }
    return claimService.updateClaim(id, updateClaimRequest);
  }

  /** fetch the status Claim. */
  @GetMapping("/{id}")
  public ResponseEntity<CommonResponse> fetchClaim(@PathVariable("id") Long id) {
    return claimService.getClaim(id);
  }

  /** update the status Claim. */
  @PatchMapping("/{id}/status")
  public ResponseEntity<CommonResponse> updateStatus(
      @PathVariable("id") Long id,
      @Valid @RequestBody UpdateStatusReqDto updateClaimStatusRequest) {
    return claimService.updateStatus(id, updateClaimStatusRequest);
  }

  /** list claim with sorting pagination. */
  @GetMapping("/list")
  public ResponseEntity<Map<String, Object>> listClaims(
      @RequestParam(defaultValue = "ASC") String order,
      @RequestParam(defaultValue = "claim_date") String field,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String filter) {
    return claimService.listClaims(order, field, page, size, filter);
  }
}
