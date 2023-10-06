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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review createReview(Review review) throws SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateReview(Review review) throws ReviewNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteReview(Integer reviewId) throws ReviewNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
