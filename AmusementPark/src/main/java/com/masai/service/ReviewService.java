package com.masai.service;

import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service interface for managing park reviews.
 */
public interface ReviewService {

    /**
     * Retrieves reviews for a specific park.
     *
     * @param parkId ID of the park for which reviews are requested.
     * @return Set of reviews associated with the specified park.
     * @throws ReviewNotFoundException If no reviews are found for the specified park.
     */
    Set<Review> getReview(Integer parkId) throws ReviewNotFoundException;

    /**
     * Creates a new review for a park by a user.
     *
     * @param parkId ID of the park for which the review is to be created.
     * @param userId ID of the user creating the review.
     * @param review Review object containing details of the review to be created.
     * @return The created Review object.
     * @throws SomethingWentWrongException If an unexpected issue occurs during review creation.
     */
    Review createReview(Integer parkId, Integer userId, Review review) throws SomethingWentWrongException;

    /**
     * Updates an existing review of a park by a user.
     *
     * @param parkId ID of the park for which the review is updated.
     * @param userId ID of the user who owns the review.
     * @param review Updated Review object.
     * @return Message indicating the status of the update operation.
     * @throws ReviewNotFoundException     If the specified review is not found.
     * @throws SomethingWentWrongException If an unexpected issue occurs during the update process.
     */
    String updateReview(Integer parkId, Integer userId, Review review)
            throws ReviewNotFoundException, SomethingWentWrongException;

    /**
     * Deletes a specific review of a park by a user.
     *
     * @param parkId   ID of the park for which the review is deleted.
     * @param userId   ID of the user who owns the review.
     * @param reviewId ID of the review to be deleted.
     * @return Message indicating the status of the deletion operation.
     * @throws ReviewNotFoundException If the specified review is not found.
     */
    String deleteReview(Integer parkId, Integer userId, Integer reviewId) throws ReviewNotFoundException;

    /**
     * Retrieves paginated reviews for a specific park.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a set of reviews associated with the specified park.
     * @throws ReviewNotFoundException If no reviews are found for the specified park.
     */
    Page<Review> getReview(Integer parkId, Pageable pageable) throws ReviewNotFoundException;

    /**
     * Retrieves paginated and sorted reviews for a specific park in ascending order.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a set of reviews associated with the specified park sorted in ascending order.
     * @throws ReviewNotFoundException If no reviews are found for the specified park.
     */
    Page<Review> getReviewSortedAsc(Integer parkId, Pageable pageable) throws ReviewNotFoundException;

    /**
     * Retrieves paginated and sorted reviews for a specific park in descending order.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting information.
     * @return Page object containing a set of reviews associated with the specified park sorted in descending order.
     * @throws ReviewNotFoundException If no reviews are found for the specified park.
     */
    Page<Review> getReviewSortedDesc(Integer parkId, Pageable pageable) throws ReviewNotFoundException;

    /**
     * Adds a list of reviews in bulk.
     *
     * @param reviews List of Review objects to be added in bulk.
     * @return List of added Review objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk review addition.
     */
    List<Review> addReviewsInBulk(List<Review> reviews) throws SomethingWentWrongException;
}
