//package com.swiftbuy.user.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.swiftbuy.admin.model.ProductDetails;
//import com.swiftbuy.order.model.OrderDetails;
//import com.swiftbuy.order.service.OrderService;
//import com.swiftbuy.product.service.ProductService;
//import com.swiftbuy.user.model.ReviewDetails;
//import com.swiftbuy.user.model.UserDetails;
//import com.swiftbuy.user.repository.ReviewRepository;
//import com.swiftbuy.user.service.UserService;
//
//@Service
//public class ReviewService {
//
//    private final ReviewRepository reviewRepository;
//    private final ProductService productService;
//    private final UserService userService;
//    private final OrderService orderService;
//
//    @Autowired
//    public ReviewService(ReviewRepository reviewRepository, ProductService productService, UserService userService, OrderService orderService) {
//        this.reviewRepository = reviewRepository;
//        this.productService = productService;
//        this.userService = userService;
//        this.orderService = orderService;
//    }
//
//    public ReviewDetails saveOrUpdateReview(ReviewDetails review, Long userId, Long productId, Long orderId) {
//        Optional<ReviewDetails> existingReview = reviewRepository.findByUserIdAndProductIdAndOrderId(userId, productId, orderId);
//
//        if (existingReview.isPresent()) {
//            // Update the existing review
//            ReviewDetails updatedReview = existingReview.get();
//            updatedReview.setDescription(review.getDescription());
//            updatedReview.setImage(review.getImage());
//            updatedReview.setRatings(review.getRatings());
//            updatedReview = reviewRepository.save(updatedReview);
//            updateProductRating(updatedReview);
//            return updatedReview;
//        } else {
//            // Create a new review
//            // Retrieve existing instances of ProductDetails, UserDetails, and OrderDetails
//            ProductDetails product = productService.getProductById(productId);
//            UserDetails user = userService.getUserById(userId);
//            OrderDetails order = orderService.getOrderById(orderId);
//
//            // Set the retrieved instances to the ReviewDetails object
//            review.setProduct(product);
//            review.setUser(user);
//            review.setOrder(order);
//
//            // Save the ReviewDetails
//            ReviewDetails savedReview = reviewRepository.save(review);
//            updateProductRating(savedReview);
//            return savedReview;
//        }
//    }
//
//    private void updateProductRating(ReviewDetails review) {
//        ProductDetails product = review.getProduct();
//        List<ReviewDetails> productReviews = reviewRepository.findByProduct(product);
//        double sumOfRatings = productReviews.stream().mapToDouble(ReviewDetails::getRatings).sum();
//        double averageRating = sumOfRatings / productReviews.size();
//        product.setOverallRating(averageRating);
//        productService.saveProduct(product);
//    }
//}