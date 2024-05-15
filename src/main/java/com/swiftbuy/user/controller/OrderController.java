package com.swiftbuy.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.service.OrderService;

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
        Long userId = (Long) request.getAttribute("userId");

        try {
            Order order = orderService.createOrder(userId);
            return new ResponseEntity<>("Order created successfully: " + order.getOrderId(), HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Internal Server Error: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Get all orders for a user
//    @GetMapping("/{userId}/orders")
//    public ResponseEntity<List<Order>> getAllOrdersByUser(@PathVariable(value = "userId") Long userId) {
//        List<Order> orders = userService.getAllOrdersByUser(userId);
//        return new ResponseEntity<>(orders, HttpStatus.OK);
//    }


}