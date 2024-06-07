package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;
import com.swiftbuy.user.model.ReviewDetails;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.ReviewRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.ReviewService;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTestCase {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Order mockOrder;

    @BeforeEach
    public void setUp() {
        mockOrder = new Order();
        mockOrder.setOrderId(1L);
        when(orderRepo.findById(1L)).thenReturn(Optional.of(mockOrder));
    }

    @Test
    public void testAddReview() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        Long orderId = 1L;

        // Create a review JSON object
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("productId", 1L);
        reviewJson.put("review", "product is bad");
        reviewJson.put("reviewImage", "http://example.com/image.jpg");

        // Mock the user and product existence
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserDetails()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new ProductDetails()));

        // Mock the order and order items
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        ProductDetails product = new ProductDetails();
        product.setProductId(1L); // Set the product id to match with the reviewJson
        orderItem.setProduct(product);
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        when(orderRepo.findByOrderIdAndUserId(anyLong(), anyLong())).thenReturn(order);

        // Perform the POST request to add a review
        mockMvc.perform(post("/review/{orderId}", orderId)
                .content(reviewJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn();
    }
    @Test
    public void GetReview() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        // Perform the POST request to add a review
        mockMvc.perform(get("/review")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void testAddReview_ProductNotFound() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2"; Long orderId = 402L;

        // Create a review JSON object
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("productId", 1000L);
        reviewJson.put("review", "product is bad");
        reviewJson.put("reviewImage", "http://example.com/image.jpg");

        // Mock the user and product existence
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserDetails()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new ProductDetails()));

        // Mock the order and order items
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        ProductDetails product = new ProductDetails();
        product.setProductId(1000L); // Set the product id to match with the reviewJson
        orderItem.setProduct(product);
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        when(orderRepo.findByOrderIdAndUserId(anyLong(), anyLong())).thenReturn(order);

        // Perform the POST request to add a review
        mockMvc.perform(post("/review/{orderId}", orderId)
                .content(reviewJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
    @Test
    public void testDeleteReview_Success() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";     Long reviewId = 1L;
        Long userId = 1L;

        // Mocking the review
        ReviewDetails review = new ReviewDetails();
        review.setUserId(userId);
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // Deleting the review
        mockMvc.perform(MockMvcRequestBuilders.delete("/review/{reviewId}", reviewId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the deleteById method was called with the correct reviewId
        verify(reviewRepository).deleteById(reviewId);
    }

    @Test
    public void testDeleteReview_ReviewNotFound() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";   Long reviewId = 1L;
        Long userId = 1L;

        // Mocking the absence of the review
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        // Attempting to delete the review should throw ResourceNotFoundException
        mockMvc.perform(MockMvcRequestBuilders.delete("/review/{reviewId}", reviewId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();

        // Verify that deleteById method was never called
        verify(reviewRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testDeleteReview_ReviewNotBelongsToUser() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";  Long reviewId = 1L;
        Long userId = 1L;

        // Mocking a review with a different userId
        ReviewDetails review = new ReviewDetails();
        review.setUserId(userId + 1); // Different userId
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        // Attempting to delete the review should throw ResourceNotFoundException
        mockMvc.perform(MockMvcRequestBuilders.delete("/review/{reviewId}", reviewId)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();

        // Verify that deleteById method was never called
        verify(reviewRepository, never()).deleteById(anyLong());
    }
    @Test
    public void testAddReview_ProductNotInOrder() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";  Long orderId = 402L;

        // Create a review JSON object
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("productId", 1000L); // Product ID not present in the order
        reviewJson.put("review", "product is bad");
        reviewJson.put("reviewImage", "http://example.com/image.jpg");

        // Mock necessary dependencies to throw ResourceNotFoundException
        when(productRepository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Product not found"));
        when(orderRepo.findByOrderIdAndUserId(anyLong(), anyLong())).thenReturn(new Order());

        // Perform the POST request to add a review
        mockMvc.perform(post("/review/{orderId}", orderId)
                .content(reviewJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isInternalServerError()) // Expecting a 404 status
                .andReturn();
    }




}