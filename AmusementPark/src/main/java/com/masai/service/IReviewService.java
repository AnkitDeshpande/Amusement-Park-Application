package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Review;
import com.masai.repository.ReviewRepository;

@Service
public class IReviewService implements ReviewService {

	@Autowired
	private ReviewRepository repo;

	@Override
	public Review getReview(Integer reviewId) throws ReviewNotFoundException {
		return repo.findById(reviewId)
				.orElseThrow(() -> new ReviewNotFoundException("Couldn't find review with id: " + reviewId));
	}

	@Override
	public Review createReview(Review review) throws SomethingWentWrongException {
		try {
			return repo.save(review);
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String updateReview(Review review) throws ReviewNotFoundException, SomethingWentWrongException {
		Review existingReview = repo.findById(review.getId())
				.orElseThrow(() -> new ReviewNotFoundException("Couldn't find review with id: " + review.getId()));
		try {
			// Update review properties here
			existingReview.setUser(review.getUser());
			existingReview.setPark(review.getPark());
			existingReview.setActivity(review.getActivity());
			existingReview.setRating(review.getRating());
			existingReview.setComment(review.getComment());

			// Save the updated review
			repo.save(existingReview);
			return "Review updated successfully.";
		} catch (Exception e) {
			throw new SomethingWentWrongException();
		}
	}

	@Override
	public String deleteReview(Integer reviewId) throws ReviewNotFoundException {
		Review review = repo.findById(reviewId)
				.orElseThrow(() -> new ReviewNotFoundException("Couldn't find review with id: " + reviewId));

		// Instead of deleting the review, mark it as deleted
		review.setDeleted(true);
		repo.save(review);

		return "Review deleted successfully.";
	}
}
