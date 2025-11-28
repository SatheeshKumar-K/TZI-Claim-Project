package com.claim.claim.service;

import com.claim.claim.dto.FilterReqDto;
import com.claim.claim.dto.CreateClaimReqDto;
import com.claim.claim.dto.CommonResponse;
import com.claim.claim.dto.UpdateClaimReqDto;
import com.claim.claim.dto.UpdateStatusReqDto;
import com.claim.claim.entity.Claim;
import com.claim.claim.repository.ClaimRepository;
import com.claim.claim.utils.ClaimStatus;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.claim.claim.utils.AppConstants.PAGE;
import static com.claim.claim.utils.AppConstants.SIZE;

@Service
@Transactional
public class ClaimService {

  private final ClaimRepository claimRepository;

  public ClaimService(ClaimRepository claimRepository) {
    this.claimRepository = claimRepository;
  }

  /** create claim. */
  public ResponseEntity<CommonResponse> createClaim(CreateClaimReqDto createClaimRequest) {
    CommonResponse response = new CommonResponse();
    try {
      Claim claim = new Claim();
      claim.setClaimNumber(UUID.randomUUID().toString());
      claim.setClaimantName(createClaimRequest.getClaimantName());
      claim.setClaimAmount(createClaimRequest.getClaimAmount());
      claim.setClaimDate(LocalDate.now());
      claim.setStatus(ClaimStatus.PENDING);
      claim.setNotes(createClaimRequest.getNotes());

      Claim savedClaim = claimRepository.save(claim);
      response.setData(savedClaim);
      response.setMessage("Claim created successfully");
      response.setSuccess(true);
      response.setCode(HttpStatus.CREATED);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMessage("Failed to create claim: " + e.getMessage());
      response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
      response.setData(null);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  /** update claim. */
  public ResponseEntity<CommonResponse> updateClaim(Long id, UpdateClaimReqDto updateClaimReqDto) {
    CommonResponse response = new CommonResponse();
    Claim claim =
        claimRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
    try {
      claim.setId(id);
      claim.setClaimantName(updateClaimReqDto.getClaimantName());
      claim.setClaimAmount(updateClaimReqDto.getClaimAmount());
      claim.setClaimDate(
          updateClaimReqDto.getClaimDate() != null
              ? updateClaimReqDto.getClaimDate()
              : LocalDate.now());
      claim.setNotes(updateClaimReqDto.getNotes());

      Claim updatedClaim = claimRepository.save(claim);
      response.setData(updatedClaim);
      response.setMessage("Claim updated successfully");
      response.setSuccess(true);
      response.setCode(HttpStatus.OK);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMessage("Failed to update claim: " + e.getMessage());
      response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
      response.setData(null);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  /** fetch claim. */
  public ResponseEntity<CommonResponse> getClaim(Long id) {
    CommonResponse response = new CommonResponse();
    Claim claim =
        claimRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
    try {
      response.setData(claim);
      response.setMessage("Claim fetched successfully");
      response.setSuccess(true);
      response.setCode(HttpStatus.OK);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMessage("Failed to get claim: " + e.getMessage());
      response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
      response.setData(null);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  /** update status claim. */
  public ResponseEntity<CommonResponse> updateStatus(
      Long id, UpdateStatusReqDto updateStatusReqDto) {
    CommonResponse response = new CommonResponse();
    Claim claim =
        claimRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Claim not found"));
    try {
      if (updateStatusReqDto.getStatus() != null) {
        boolean isValid =
            Arrays.stream(ClaimStatus.values())
                .anyMatch(e -> e.name().equals(updateStatusReqDto.getStatus().toUpperCase()));
        if (!isValid) {
          response.setData(null);
          response.setMessage(
              "Invalid status value. Allowed values: " + Arrays.toString(ClaimStatus.values()));
          response.setSuccess(false);
          response.setCode(HttpStatus.BAD_REQUEST);
          return ResponseEntity.badRequest().body(response);
        }
        claim.setStatus(ClaimStatus.valueOf(updateStatusReqDto.getStatus().toUpperCase()));
      }
      if (updateStatusReqDto.getNotes() != null) claim.setNotes(updateStatusReqDto.getNotes());

      Claim claimStatusUpdated = claimRepository.save(claim);
      response.setData(claimStatusUpdated);
      response.setMessage("Claim status updated successfully");
      response.setSuccess(true);
      response.setCode(HttpStatus.OK);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      response.setSuccess(false);
      response.setMessage("Failed to update status claim: " + e.getMessage());
      response.setCode(HttpStatus.INTERNAL_SERVER_ERROR);
      response.setData(null);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  /** list claim with sorting pagination. */
  public ResponseEntity<Map<String, Object>> listClaims(
      String order, String field, Integer page, Integer size, String requestFilters) {

    Pageable pageable =
        PageRequest.of(
            page != null ? page : PAGE,
            size != null ? size : SIZE,
            "ASC".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC,
            field != null ? field : "claim_date" // DB column name for native query
            );

    FilterReqDto filters = null;
    if (requestFilters != null) {
      try {
        filters =
            new ObjectMapper()
                .configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true)
                .readValue(requestFilters, FilterReqDto.class);
      } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filter format");
      }
    }

    Page<Claim> result =
        claimRepository.findClaims(
            filters != null ? filters.getStatus() : null,
            filters != null ? filters.getMinAmount() : null,
            filters != null ? filters.getMaxAmount() : null,
            filters != null && filters.getDateFrom() != null
                ? filters.getDateFrom().toString()
                : null,
            filters != null && filters.getDateTo() != null ? filters.getDateTo().toString() : null,
            pageable);

    List<Map<String, Object>> responseList =
        result.getContent().stream()
            .map(
                claim ->
                    Map.<String, Object>of(
                        "id", (Object) claim.getId(),
                        "status", (Object) claim.getStatus(),
                        "claimAmount", (Object) claim.getClaimAmount(),
                        "claimDate", (Object) claim.getClaimDate()))
            .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("total", result.getTotalElements());
    response.put("page", page != null ? page : 0);
    response.put("totalPages", result.getTotalPages());
    response.put("results", responseList);
    return ResponseEntity.ok(response);
  }
}
