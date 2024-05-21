package com.swiftbuy.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.service.OrderService;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Configuration
@SecurityScheme(   name = "Bearer Authentication",   type = SecuritySchemeType.HTTP,   bearerFormat = "JWT",   scheme = "bearer" )
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(HttpServletRequest request) {
    	 // Get claims from request
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
		
         
        try {
            Order order = orderService.createOrder(userId);
            return new ResponseEntity<>("Order created successfully: " + order.getOrderId(), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Internal Server Error: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/orders/get")
    public ResponseEntity<List<Order>> getAllOrdersByUser(HttpServletRequest request){
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
        List<Order> orders = orderService.getAllOrdersByUser(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,HttpServletRequest request) {
    	
 		
        try {
        	 Claims claims = (Claims) request.getAttribute("claims");
             String userIdString = claims.get("userId", String.class);
     		Long userId = Long.valueOf(userIdString);
            orderService.cancelOrder(orderId,userId);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error cancelling order: " + e.getMessage());
        }
    }
    @PutMapping("/{orderId}/shipped")
    public ResponseEntity<Order> markOrderAsShipped(@PathVariable Long orderId,HttpServletRequest request) {
    	 Claims claims = (Claims) request.getAttribute("claims");
         String userIdString = claims.get("userId", String.class);
 		Long userId = Long.valueOf(userIdString);
 		
        try {
            Order order = orderService.markOrderAsShipped(orderId,userId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<Order> markOrderAsDelivered(@PathVariable Long orderId,HttpServletRequest request) {
    	 Claims claims = (Claims) request.getAttribute("claims");
         String userIdString = claims.get("userId", String.class);
 		Long userId = Long.valueOf(userIdString);
 		
        try {
            Order order = orderService.markOrderAsDelivered(orderId,userId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId,HttpServletRequest request) {
    	

   	 Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
        try {
            Order order = orderService.getOrderById(orderId,userId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}