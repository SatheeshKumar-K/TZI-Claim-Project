package com.claim.claim.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.claim.claim.dto.*;
import com.claim.claim.entity.Claim;
import com.claim.claim.repository.ClaimRepository;
import com.claim.claim.utils.ClaimStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.domain.*;

public class ClaimServiceTest {

  @InjectMocks private ClaimService claimService;
  @Mock private ClaimRepository claimRepository;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    objectMapper = new ObjectMapper();
  }

  @Test
  void createClaim_success() {
    CreateClaimReqDto request = new CreateClaimReqDto();
    request.setClaimantName("John Doe");
    request.setClaimAmount(1000.0);
    request.setNotes("Test claim");

    Claim savedClaim = new Claim();
    savedClaim.setId(1L);
    savedClaim.setClaimantName("John Doe");

    when(claimRepository.save(any(Claim.class))).thenReturn(savedClaim);
    ResponseEntity<CommonResponse> response = claimService.createClaim(request);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(response.getBody().isSuccess());
    assertEquals("Claim created successfully", response.getBody().getMessage());
    assertNotNull(response.getBody().getData());
  }

  @Test
  void createClaim_exception() {
    CreateClaimReqDto request = new CreateClaimReqDto();
    request.setClaimantName("John Doe");
    request.setClaimAmount(1000.0);

    when(claimRepository.save(any(Claim.class))).thenThrow(new RuntimeException("DB error"));
    ResponseEntity<CommonResponse> response = claimService.createClaim(request);
    assertFalse(response.getBody().isSuccess());
    assertTrue(response.getBody().getMessage().contains("Failed to create claim"));
  }

  @Test
  void updateClaim_success() {
    UpdateClaimReqDto request = new UpdateClaimReqDto();
    request.setClaimantName("Jane Doe");
    request.setClaimAmount(2000.0);

    Claim claim = new Claim();
    claim.setId(1L);
    when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
    when(claimRepository.save(any(Claim.class))).thenReturn(claim);
    ResponseEntity<CommonResponse> response = claimService.updateClaim(1L, request);
    assertTrue(response.getBody().isSuccess());
    assertEquals("Claim updated successfully", response.getBody().getMessage());
  }

  @Test
  void updateClaim_notFound() {
    UpdateClaimReqDto request = new UpdateClaimReqDto();
    when(claimRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> claimService.updateClaim(1L, request));
  }

  @Test
  void getClaim_success() {
    Claim claim = new Claim();
    claim.setId(1L);
    when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
    ResponseEntity<CommonResponse> response = claimService.getClaim(1L);
    assertTrue(response.getBody().isSuccess());
    assertEquals(claim, response.getBody().getData());
  }

  @Test
  void getClaim_notFound() {
    when(claimRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> claimService.getClaim(1L));
  }

  @Test
  void updateStatus_success() {
    Claim claim = new Claim();
    claim.setId(1L);

    UpdateStatusReqDto request = new UpdateStatusReqDto();
    request.setStatus("APPROVED");
    request.setNotes("All good");
    when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
    when(claimRepository.save(any(Claim.class))).thenReturn(claim);
    ResponseEntity<CommonResponse> response = claimService.updateStatus(1L, request);
    assertTrue(response.getBody().isSuccess());
    assertEquals(ClaimStatus.APPROVED, ((Claim) response.getBody().getData()).getStatus());
  }

  @Test
  void updateStatus_invalidStatus() {
    Claim claim = new Claim();
    claim.setId(1L);
    UpdateStatusReqDto request = new UpdateStatusReqDto();
    request.setStatus("INVALID_STATUS");
    when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));

    ResponseEntity<CommonResponse> response = claimService.updateStatus(1L, request);
    assertFalse(response.getBody().isSuccess());
    assertTrue(response.getBody().getMessage().contains("Invalid status value"));
  }

  @Test
  void listClaims_success() throws Exception {
    Claim claim1 = new Claim();
    claim1.setId(1L);
    claim1.setClaimAmount(100.0);
    claim1.setClaimDate(LocalDate.now());
    claim1.setStatus(ClaimStatus.PENDING);

    List<Claim> claims = List.of(claim1);
    Page<Claim> page = new PageImpl<>(claims, PageRequest.of(0, 10), claims.size());
    when(claimRepository.findClaims(
            null, null, null, null, null, PageRequest.of(0, 10, Sort.by("claim_date"))))
        .thenReturn(page);

    ResponseEntity<Map<String, Object>> response =
        claimService.listClaims("ASC", "claim_date", 0, 10, null);
    assertEquals(1L, response.getBody().get("total"));
    assertEquals(0, response.getBody().get("page"));
    assertEquals(1, response.getBody().get("totalPages"));
  }

  @Test
  void createClaim_runtimeException() {
    CreateClaimReqDto request = new CreateClaimReqDto();
    request.setClaimantName("John");
    request.setClaimAmount(100.0);
    when(claimRepository.save(any(Claim.class)))
        .thenThrow(new RuntimeException("Unexpected error"));

    ResponseEntity<CommonResponse> response = claimService.createClaim(request);
    assertFalse(response.getBody().isSuccess());
    assertTrue(response.getBody().getMessage().contains("Failed to create claim"));
  }

  @Test
  void listClaims_invalidFilter() {
    String invalidFilter = "{ invalid json }"; // malformed JSON

    ResponseStatusException ex =
        assertThrows(
            ResponseStatusException.class,
            () -> claimService.listClaims("ASC", "claim_date", 0, 10, invalidFilter));
    assertTrue(ex.getReason().contains("Invalid filter format"));
  }

  @Test
  void updateStatus_noNotes() {
    Claim claim = new Claim();
    claim.setId(1L);

    UpdateStatusReqDto request = new UpdateStatusReqDto();
    request.setStatus("APPROVED");
    request.setNotes(null);
    when(claimRepository.findById(1L)).thenReturn(Optional.of(claim));
    when(claimRepository.save(any(Claim.class))).thenReturn(claim);

    ResponseEntity<CommonResponse> response = claimService.updateStatus(1L, request);
    assertTrue(response.getBody().isSuccess());
    assertEquals(ClaimStatus.APPROVED, ((Claim) response.getBody().getData()).getStatus());
  }
}
