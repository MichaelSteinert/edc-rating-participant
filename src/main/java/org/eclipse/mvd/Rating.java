package org.eclipse.mvd;

import java.time.Instant;
import java.util.UUID;

public record Rating(
    UUID ratingId,
    String userId,
    String serviceProviderId,
    double ratingValue,
    String review,
    Instant createdAt) {
  // Default constructor that generates a new UUID and sets the current timestamp
  public Rating(String userId, String serviceProviderId, double ratingValue, String review) {
    this(UUID.randomUUID(), userId, serviceProviderId, ratingValue, review, Instant.now());
  }
}