package com.swiftbuy.user.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;
import com.swiftbuy.user.model.ReviewDetails;
import com.swiftbuy.user.repository.OrderItemRepository;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.ReviewRepository;
import com.swiftbuy.user.repository.UserRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private OrderItemRepository orderItemRepo;
    
    @Autowired
    private ProductRepository productRepository;

    public ReviewDetails addReviewForUserOrder(Long orderId, Long userId, ReviewDetails orderReview) {
        Order order = orderRepository.findByOrderIdAndUserId(orderId, userId);
        Long productId = orderReview.getProductId();
        
        userRepository.findById(userId);
               
        
        // Check if the product exists in the repository
        ProductDetails product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        // Check if the product is part of the user's order
        boolean productInOrder = order.getOrderItems().stream()
                .anyMatch(item -> item.getProduct().getProductId().equals(productId));
        
       
        
        orderReview.setOrder(order);
        orderReview.setUserId(userId);
        return reviewRepository.save(orderReview);
    }

    public List<ReviewDetails> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public void deleteReview(Long reviewId, Long userId) {
        ReviewDetails review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        if (!review.getUserId().equals(userId)) {
            // Handle the case where the review does not belong to the specified user
            throw new ResourceNotFoundException("Review does not belong to the specified user");
        }
        reviewRepository.deleteById(reviewId);
    }
}