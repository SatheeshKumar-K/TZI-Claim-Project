package com.claim.claim.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.claim.claim.dto.*;
import com.claim.claim.dto.CommonResponse;
import com.claim.claim.service.ClaimService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;

@WebMvcTest(ClaimController.class)
public class ClaimControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ClaimService claimService;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  /** create claim success. */
  @Test
  void createClaim_success() throws Exception {
    CreateClaimReqDto request = new CreateClaimReqDto();
    request.setClaimantName("sakthi");
    request.setClaimAmount(1000.0);

    CommonResponse response = new CommonResponse();
    response.setSuccess(true);
    response.setMessage("Claim created successfully");

    Mockito.when(claimService.createClaim(any(CreateClaimReqDto.class)))
        .thenReturn(ResponseEntity.ok(response));

    mockMvc
        .perform(
            post("/api/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Claim created successfully"));
  }

  /** create claim validation error. */
  @Test
  void createClaim_validationError() throws Exception {
    CreateClaimReqDto request = new CreateClaimReqDto(); // missing required fields

    mockMvc
        .perform(
            post("/api/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }

  /** update claim success. */
  @Test
  void updateClaim_success() throws Exception {
    UpdateClaimReqDto request = new UpdateClaimReqDto();
    request.setClaimantName("sakthi");
    request.setClaimAmount(2000.0);

    CommonResponse response = new CommonResponse();
    response.setSuccess(true);
    response.setMessage("Claim updated successfully");

    Mockito.when(claimService.updateClaim(eq(1L), any(UpdateClaimReqDto.class)))
        .thenReturn(ResponseEntity.ok(response));

    mockMvc
        .perform(
            put("/api/claims/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Claim updated successfully"));
  }

  /** update claim validation error. */
  @Test
  void updateClaim_validationError() throws Exception {
    UpdateClaimReqDto request = new UpdateClaimReqDto(); // invalid input

    mockMvc
        .perform(
            put("/api/claims/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success").value(false));
  }

  /** fetch claim success. */
  @Test
  void fetchClaim_success() throws Exception {
    CommonResponse response = new CommonResponse();
    response.setSuccess(true);
    response.setMessage("Claim fetched");
    response.setData(null);

    Mockito.when(claimService.getClaim(1L)).thenReturn(ResponseEntity.ok(response));

    mockMvc
        .perform(get("/api/claims/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Claim fetched"));
  }

  /** update status success. */
  @Test
  void updateStatus_success() throws Exception {
    UpdateStatusReqDto request = new UpdateStatusReqDto();
    request.setStatus("APPROVED");

    CommonResponse response = new CommonResponse();
    response.setSuccess(true);
    response.setMessage("Status updated");

    Mockito.when(claimService.updateStatus(eq(1L), any(UpdateStatusReqDto.class)))
        .thenReturn(ResponseEntity.ok(response));

    mockMvc
        .perform(
            patch("/api/claims/1/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.message").value("Status updated"));
  }

  /** list claim success. */
  @Test
  void listClaims_success() throws Exception {
    Mockito.when(claimService.listClaims("ASC", "claim_date", 0, 10, null))
        .thenReturn(ResponseEntity.ok(Map.of("total", 0, "claims", List.of())));

    mockMvc
        .perform(get("/api/claims/list"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.total").value(0))
        .andExpect(jsonPath("$.claims").isArray());
  }
}
