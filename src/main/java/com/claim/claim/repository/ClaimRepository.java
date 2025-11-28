package com.claim.claim.repository;

import com.claim.claim.entity.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
  @Query(
      value =
          """
        SELECT * FROM claim_db.claim c
        WHERE (:status IS NULL OR c.status = :status)
          AND (:minAmount IS NULL OR c.claim_amount >= :minAmount)
          AND (:maxAmount IS NULL OR c.claim_amount <= :maxAmount)
          AND (:dateFrom IS NULL OR c.claim_date >= :dateFrom)
          AND (:dateTo IS NULL OR c.claim_date <= :dateTo)
        """,
      countQuery =
          """
        SELECT count(*) FROM claim_db.claim c
        WHERE (:status IS NULL OR c.status = :status)
          AND (:minAmount IS NULL OR c.claim_amount >= :minAmount)
          AND (:maxAmount IS NULL OR c.claim_amount <= :maxAmount)
          AND (:dateFrom IS NULL OR c.claim_date >= :dateFrom)
          AND (:dateTo IS NULL OR c.claim_date <= :dateTo)
        """,
      nativeQuery = true)
  Page<Claim> findClaims(
      @Param("status") String status,
      @Param("minAmount") Double minAmount,
      @Param("maxAmount") Double maxAmount,
      @Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo,
      Pageable pageable);
}
