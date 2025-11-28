package com.claim.claim.utils;

import java.util.HashMap;
import java.util.Map;

public interface AppConstants {
  Map<String, String> CLAIM_FILTER_FIELD_MAP =
      new HashMap<>() {
        {
          put("status", "status");
          put("fromDate", "claim_date");
          put("toDate", "claim_date");
          put("maxAmount", "claim_amount");
          put("minAmount", "claim_amount");
        }
      };

  Integer PAGE = 0;
  Integer SIZE = 10;
}
