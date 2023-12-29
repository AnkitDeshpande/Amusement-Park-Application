package com.masai.service;

import com.masai.exception.ParkNotFoundException;
import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.Park;
import com.masai.model.Review;
import com.masai.model.User;
import com.masai.repository.ParkRepository;
import com.masai.repository.ReviewRepository;
import com.masai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class IReviewService implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<Review> getReview(Integer parkId) throws ReviewNotFoundException {
        Optional<Park> park = parkRepository.findById(parkId);
        if (!park.isPresent()) {
            throw new ReviewNotFoundException("Park with id " + parkId + " not found.");
        }

        return park.get().getReviews();
    }

    @Override
    public Review createReview(Integer parkId, Integer userId, Review review) throws SomethingWentWrongException {
        Park park = parkRepository.findById(parkId).orElseThrow(() -> new ParkNotFoundException("Park not found."));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

        review.setPark(park);
        review.setUser(user);

        return reviewRepository.save(review);
    }

    @Override
    public String updateReview(Integer parkId, Integer userId, Review review)
            throws ReviewNotFoundException, SomethingWentWrongException {
        Park park = parkRepository.findById(parkId).orElseThrow(() -> new ParkNotFoundException("Park not found."));
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        Review existingReview = reviewRepository.findById(review.getId())
                .orElseThrow(() -> new ReviewNotFoundException("Review Not Found"));

        existingReview.setPark(park);
        existingReview.setUser(user);
        existingReview.setRating(review.getRating());
        existingReview.setComment(review.getComment());

        reviewRepository.save(existingReview);

        return "Review updated successfully.";
    }

    @Override
    public String deleteReview(Integer parkId, Integer userId, Integer reviewId) throws ReviewNotFoundException {
        parkRepository.findById(parkId).orElseThrow(() -> new ParkNotFoundException("Park not found."));
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found."));
        review.setDeleted(true);
        reviewRepository.save(review);

        return "Review deleted successfully.";
    }

    /**
     * Retrieves paginated reviews for a specific park.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting
     *                 information.
     * @return Page object containing a set of reviews associated with the specified
     *         park.
     * @throws ReviewNotFoundException If no reviews are found for the specified
     *                                 park.
     */
    @Override
    public Page<Review> getReview(Integer parkId, Pageable pageable) throws ReviewNotFoundException {
        Optional<Park> park = parkRepository.findById(parkId);
        if (!park.isPresent()) {
            throw new ReviewNotFoundException("Park with id " + parkId + " not found.");
        }

        return reviewRepository.findByPark(park.get(), pageable);
    }

    /**
     * Retrieves paginated and sorted reviews for a specific park in ascending
     * order.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting
     *                 information.
     * @return Page object containing a set of reviews associated with the specified
     *         park sorted in ascending order.
     * @throws ReviewNotFoundException If no reviews are found for the specified
     *                                 park.
     */
    @Override
    public Page<Review> getReviewSortedAsc(Integer parkId, Pageable pageable) throws ReviewNotFoundException {
        Optional<Park> park = parkRepository.findById(parkId);
        if (!park.isPresent()) {
            throw new ReviewNotFoundException("Park with id " + parkId + " not found.");
        }

        return reviewRepository.findByParkOrderByIdAsc(park.get(), pageable);
    }

    /**
     * Retrieves paginated and sorted reviews for a specific park in descending
     * order.
     *
     * @param parkId   ID of the park for which reviews are requested.
     * @param pageable Pageable object containing pagination and sorting
     *                 information.
     * @return Page object containing a set of reviews associated with the specified
     *         park sorted in descending order.
     * @throws ReviewNotFoundException If no reviews are found for the specified
     *                                 park.
     */
    @Override
    public Page<Review> getReviewSortedDesc(Integer parkId, Pageable pageable) throws ReviewNotFoundException {
        Optional<Park> park = parkRepository.findById(parkId);
        if (!park.isPresent()) {
            throw new ReviewNotFoundException("Park with id " + parkId + " not found.");
        }

        return reviewRepository.findByParkOrderByIdDesc(park.get(), pageable);
    }

    /**
     * Adds a list of reviews in bulk.
     *
     * @param reviews List of Review objects to be added in bulk.
     * @return List of added Review objects.
     * @throws SomethingWentWrongException If an unexpected issue occurs during bulk
     *                                     review addition.
     */
    @Override
    public List<Review> addReviewsInBulk(List<Review> reviews) throws SomethingWentWrongException {
        try {
            return reviewRepository.saveAll(reviews);
        } catch (Exception e) {
            throw new SomethingWentWrongException("An error occurred while adding reviews in bulk.");
        }
    }

}
