package com.swiftbuy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        Map<String, Object> response = new HashMap<>();
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);

            ReviewDetails savedReview = reviewService.addReviewForUserOrder(orderId, userId, review);
            response.put("status", true);
            response.put("message", "Review added successfully");
           
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", "Failed to add review: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getReviewsByUserId(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
      
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);

            List<ReviewDetails> reviews = reviewService.getReviewsByUserId(userId);
            response.put("status", true);
            response.put("message", "Reviews retrieved successfully");
            response.put("reviews", reviews);
            return ResponseEntity.ok(response);
       
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Map<String, Object>> deleteReview(HttpServletRequest request, @PathVariable Long reviewId) {
        Map<String, Object> response = new HashMap<>();
      
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);

            reviewService.deleteReview(reviewId, userId);
            response.put("status", true);
            response.put("message", "Review deleted successfully");
            return ResponseEntity.ok(response);
        
    }
}