package org.eclipse.mvd;

public record RatingDto(
    String userId,
    String serviceProviderId,
    double ratingValue,
    String review) {
  public RatingDto {
    if (ratingValue < 0 || ratingValue > 5) {
      throw new IllegalArgumentException("Rating value must be between 0 and 5");
    }
  }
}