package com.swiftbuy.user.controller;

import java.util.HashMap;
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
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.service.OrderService;
import com.swiftbuy.user.service.ShoppingCartService;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Configuration
@SecurityScheme(   name = "Bearer Authentication",   type = SecuritySchemeType.HTTP,   bearerFormat = "JWT",   scheme = "bearer" )
@RequestMapping("/order")
public class OrderController {
	private OrderService orderService;
	private ShoppingCartService cartService;
    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository,ShoppingCartService cartService ) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }
    

    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);

        try {
            List<ShoppingCart> cartItems = cartService.getCartUserId(userId);
            Order order = orderService.createOrder(userId, cartItems);
            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("message", "Order created successfully: " + order.getOrderId());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
    
    @GetMapping("/orders/get")
    public ResponseEntity<Map<String, Object>> getAllOrdersByUser(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
       
        Page<Order> orders = orderService.getAllOrdersByUser(userId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("orders:", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }
    

    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<Map<String, Object>> cancelOrder(@PathVariable Long orderId,HttpServletRequest request) {
    	
 		
        try {
        	 Claims claims = (Claims) request.getAttribute("claims");
             String userIdString = claims.get("userId", String.class);
     		Long userId = Long.valueOf(userIdString);
            orderService.cancelOrder(orderId,userId);
            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("message", "Order cancelled successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
		try {
        Order order = orderService.getOrderById(orderId, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("order:",order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", "Internal Server Error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @GetMapping("/cancelled/{orderId}")
    public ResponseEntity<Map<String, Object>> getCancelledOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
		try {
        CancellationDTO order = orderService.getCancelledOrder(orderId, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("cancelled order:",order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e) {
        Map<String, Object> errorResponse = 	new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", "Internal Server Error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
    @GetMapping("/delivered/{orderId}")
    public ResponseEntity<Map<String, Object>> getDeliveredOrderById(@PathVariable(value = "orderId") Long orderId,
           HttpServletRequest request) {
    	Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
		Long userId = Long.valueOf(userIdString);
		try {
		DeliveredDTO order = orderService.getDeliveredOrder(orderId, userId);
		 Map<String, Object> response = new HashMap<>();
	        response.put("status", true);
	        response.put("delivered order:",order);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("status", false);
	        errorResponse.put("error", "Internal Server Error: " + e.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
    @GetMapping("/cancelled")
    public ResponseEntity<Map<String, Object>> getCancelledOrders(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
        try {
        Page<CancellationDTO> cancelledOrders = orderService.getAllCancelledOrders(userId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("cancelled orders", cancelledOrders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", "Internal Server Error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }


   

    @GetMapping("/delivered")
    public ResponseEntity<Map<String, Object>>getDeliveredOrders(
            @PageableDefault(size = 10) Pageable pageable,
            HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
        try {
        Page<DeliveredDTO> deliveredOrders = orderService.getAllDeliveredOrders(userId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("delivered orders", deliveredOrders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }catch (Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", "Internal Server Error: " + e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
        
    }
    
}