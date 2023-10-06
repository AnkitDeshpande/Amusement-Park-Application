package com.masai.service;

import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Review;

public interface ReviewService {

	public Review getReview(Integer reviewId) throws ReviewNotFoundException;

	public Review createReview(Review review) throws SomethingWentWrongException;

	public String updateReview(Review review) throws ReviewNotFoundException, SomethingWentWrongException;

	public String deleteReview(Integer reviewId) throws ReviewNotFoundException;

}
