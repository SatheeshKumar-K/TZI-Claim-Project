package com.claim.claim.utils;

import jakarta.annotation.Nullable;

/** Validate utils. */
public final class ValidateUtils {

  /** Restrict object creation. */
  private ValidateUtils() {}

  /**
   * Method to check whether a string is null or empty.
   *
   * @param string is a string value
   * @return {@code boolean}
   */
  public static boolean isNullOrEmpty(final @Nullable String string) {
    return string == null || string.trim().isEmpty();
  }
  }
