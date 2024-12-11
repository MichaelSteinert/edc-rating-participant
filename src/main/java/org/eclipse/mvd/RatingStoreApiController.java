package org.eclipse.mvd;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;

import java.util.Collection;
import java.util.UUID;

/**
 * API Controller for managing Ratings in an in-memory store
 */
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Path("/ratings")
public class RatingStoreApiController {

  private final Monitor monitor;
  private final RatingStore ratingStore;

  public RatingStoreApiController(Monitor monitor) {
    this.monitor = monitor;
    this.ratingStore = RatingStore.getInstance();
  }

  @GET
  @Path("health")
  public String checkHealth() {
    monitor.info("Received a health request");
    return "{\"response\":\"Rating API server is running and ready for requests\"}";
  }

  @POST
  @Path("add")
  public String addRating(RatingDto ratingDto) {
    monitor.info("Adding a rating: " + ratingDto);
    // Create a new Rating object using the default constructor to ensure UUID and
    // createdAt are set
    Rating rating = new Rating(
        ratingDto.userId(),
        ratingDto.serviceProviderId(),
        ratingDto.ratingValue(),
        ratingDto.review());
    ratingStore.addRating(rating);
    return "{\"response\":\"Rating added successfully\"}";
  }

  @GET
  @Path("list")
  public Collection<Rating> getAllRatings() {
    monitor.info("Retrieving all ratings");
    return ratingStore.getAllRatings().values();
  }

  @DELETE
  @Path("remove")
  public String removeRating(@QueryParam("id") String id) {
    monitor.info("Removing rating with ID: " + id);
    try {
      UUID ratingId = UUID.fromString(id);
      Rating rating = ratingStore.getRating(ratingId);
      if (rating != null) {
        ratingStore.getAllRatings().remove(ratingId);
        return "{\"response\":\"Rating removed successfully\"}";
      } else {
        return "{\"response\":\"Rating not found\"}";
      }
    } catch (IllegalArgumentException e) {
      monitor.warning("Invalid UUID format", e);
      return "{\"response\":\"Invalid UUID format\"}";
    }
  }

  @GET
  @Path("average")
  public String getAverageRating() {
    monitor.info("Calculating average rating");
    double averageRating = ratingStore.getAverageRating();
    return "{\"averageRating\":" + averageRating + "}";
  }
}