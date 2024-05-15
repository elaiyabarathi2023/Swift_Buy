//package com.swiftbuy.user.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.swiftbuy.user.model.ReviewDetails;
//import com.swiftbuy.user.repository.UserRepository;
//import com.swiftbuy.user.service.ReviewService;
//
//public class ReviewController {
//	
//	
//	@Autowired
//	private ReviewService reviewService;
//	@PostMapping("/reviews")
//	public ResponseEntity<ReviewDetails> createOrUpdateReview(@RequestBody ReviewDetails reviewRequest) {
//	    ReviewDetails review = new ReviewDetails();
//	    review.setDescription(reviewRequest.getDescription());
//	    review.setImage(reviewRequest.getImage());
//	    review.setRatings(reviewRequest.getRatings());
//
//	    ReviewDetails savedReview = reviewService.saveOrUpdateReview(review,
//	            reviewRequest.getUser(),
//	            reviewRequest.getProduct(),
//	            reviewRequest.getOrder());
//
//	    return ResponseEntity.ok(savedReview);
//	}
//}
