package com.swiftbuy.user.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.user.model.CancellationDTO;
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
    public ResponseEntity<Page<Order>> getAllOrdersByUser(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
        Page<Order> orders = orderService.getAllOrdersByUser(userId, pageable);
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
    
    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
        Order order = orderService.getOrderById(orderId, userId);
        return ResponseEntity.ok().body(order);
}
    @GetMapping("/cancelled/{orderId}")
    public ResponseEntity<CancellationDTO> getCancelledOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
        CancellationDTO order = orderService.getCancelledOrder(orderId, userId);
        return ResponseEntity.ok().body(order);
}
    @GetMapping("/delivered/{orderId}")
    public ResponseEntity<DeliveredDTO> getDeliveredOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
		DeliveredDTO order = orderService.getDeliveredOrder(orderId, userId);
        return ResponseEntity.ok().body(order);
}
    @GetMapping("/cancelled")
    public ResponseEntity<Page<CancellationDTO>> getCancelledOrders(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
        Page<CancellationDTO> cancelledOrders = orderService.getAllCancelledOrders(userId, pageable);
        return ResponseEntity.ok(cancelledOrders);
    }


   

    @GetMapping("/delivered")
    public ResponseEntity<Page<DeliveredDTO>> getDeliveredOrders(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
        Page<DeliveredDTO> deliveredOrders = orderService.getAllDeliveredOrders(userId, pageable);
        return ResponseEntity.ok(deliveredOrders);
    }
}