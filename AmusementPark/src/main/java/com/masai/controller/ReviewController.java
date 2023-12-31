package com.masai.controller;

import com.masai.model.Review;
import com.masai.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/park/{parkId}")
    public ResponseEntity<Set<Review>> getReviewsByParkId(@PathVariable Integer parkId) {
        Set<Review> reviews = reviewService.getReview(parkId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/park/{parkId}/user/{userId}")
    public ResponseEntity<Review> createReview(@PathVariable Integer parkId, @PathVariable Integer userId,
                                               @RequestBody Review review) {
        Review createdReview = reviewService.createReview(parkId, userId, review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PutMapping("/park/{parkId}/user/{userId}/review/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Integer parkId, @PathVariable Integer userId,
                                               @PathVariable Integer reviewId, @RequestBody Review review) {
        review.setId(reviewId);
        String message = reviewService.updateReview(parkId, userId, review);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/park/{parkId}/user/{userId}/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer parkId, @PathVariable Integer userId,
                                               @PathVariable Integer reviewId) {
        String message = reviewService.deleteReview(parkId, userId, reviewId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
