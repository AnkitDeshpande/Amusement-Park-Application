package com.masai.service;

import java.util.Set;

import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Review;

public interface ReviewService {

	public Set<Review> getReview(Integer parkId) throws ReviewNotFoundException;

	public Review createReview(Integer parkId, Integer userId, Review review) throws SomethingWentWrongException;

	public String updateReview(Integer parkId, Integer userId, Review review)
			throws ReviewNotFoundException, SomethingWentWrongException;

	public String deleteReview(Integer parkId, Integer userId, Integer reviewId) throws ReviewNotFoundException;

}
