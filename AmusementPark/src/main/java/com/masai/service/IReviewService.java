package com.masai.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Park park = parkRepository.findById(parkId).orElseThrow(() -> new ParkNotFoundException("Park not found."));
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));
		Review review = reviewRepository.findById(reviewId)
				.orElseThrow(() -> new ReviewNotFoundException("Review with id " + reviewId + " not found."));
		review.setDeleted(true);
		reviewRepository.save(review);

		return "Review deleted successfully.";
	}

}
