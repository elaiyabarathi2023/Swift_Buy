package com.swiftbuy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.user.model.ReviewDetails;
import com.swiftbuy.user.service.ReviewService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> addReviewForUserOrder(@PathVariable Long orderId,
                                                                      @RequestBody ReviewDetails review,
                                                                      HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        try {
            ReviewDetails savedReview = reviewService.addReviewForUserOrder(orderId, userId, review);
            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("review", savedReview);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public List<ReviewDetails> getReviewsByUserId( HttpServletRequest request) {
    	  Claims claims = (Claims) request.getAttribute("claims");
          String userIdString = claims.get("userId", String.class);
          Long userId = Long.valueOf(userIdString);

        return reviewService.getReviewsByUserId(userId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(HttpServletRequest request, @PathVariable Long reviewId) {
    	  Claims claims = (Claims) request.getAttribute("claims");
          String userIdString = claims.get("userId", String.class);
          Long userId = Long.valueOf(userIdString);

        reviewService.deleteReview(reviewId, userId);
    }

}