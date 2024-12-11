package org.eclipse.mvd;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents an in-memory store of ratings.
 * This class uses the Singleton design pattern to ensure that
 * only one instance of the RatingStore is used throughout the application.
 * It provides methods to add and retrieve ratings.
 */
public class RatingStore {

  private static RatingStore instance;
  private final Map<UUID, Rating> ratings;

  // Private constructor to prevent direct instantiation.
  private RatingStore() {
    this.ratings = new HashMap<>();
  }

  /**
   * Retrieves the singleton instance of RatingStore.
   *
   * @return The singleton instance of RatingStore.
   */
  public static synchronized RatingStore getInstance() {
    if (instance == null) {
      instance = new RatingStore();
    }
    return instance;
  }

  /**
   * Adds a rating to the store.
   *
   * @param rating The Rating object to be added.
   */
  public void addRating(Rating rating) {
    ratings.put(rating.ratingId(), rating);
  }

  /**
   * Retrieves a rating by its ID.
   *
   * @param ratingId The UUID of the rating to retrieve.
   * @return The Rating object, or null if it doesn't exist.
   */
  public Rating getRating(UUID ratingId) {
    return ratings.get(ratingId);
  }

  /**
   * Retrieves all ratings.
   *
   * @return A map of all ratings keyed by their UUID.
   */
  public Map<UUID, Rating> getAllRatings() {
    return new HashMap<>(ratings);
  }

  /**
   * Clears all ratings from the store.
   * This method is particularly useful for resetting the store state during
   * testing or other maintenance operations.
   */
  public void clear() {
    ratings.clear();
  }

  /**
   * Calculates the average rating.
   *
   * @return The average rating as a double.
   */
  public double getAverageRating() {
    if (ratings.isEmpty()) {
      return 0.0; // Return 0 if there are no ratings.
    }
    double total = 0.0;
    for (Rating rating : ratings.values()) {
      total += rating.ratingValue();
    }
    return total / ratings.size();
  }
}