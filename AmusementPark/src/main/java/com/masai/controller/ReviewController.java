package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ReviewNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.model.Review;
import com.masai.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Integer reviewId) {
        try {
            Review review = reviewService.getReview(reviewId);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            Review createdReview = reviewService.createReview(review);
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (SomethingWentWrongException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        try {
            String message = reviewService.updateReview(review);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (SomethingWentWrongException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId) {
        try {
            String message = reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}



