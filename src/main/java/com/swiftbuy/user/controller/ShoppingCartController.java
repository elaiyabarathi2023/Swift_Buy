package com.swiftbuy.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.admin.model.ShoppingCartRequest;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.service.ShoppingCartService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
 

 
@RestController
 
@RequestMapping("/api/shoppingcart")
@Configuration
@SecurityScheme(   name = "Bearer Authentication",   type = SecuritySchemeType.HTTP,   bearerFormat = "JWT",   scheme = "bearer" )
public class ShoppingCartController {
 
    @Autowired
 
    private ShoppingCartService cartService;
 
   

    @PostMapping("/add")
    public ResponseEntity<ShoppingCart> addToCart(@RequestBody ShoppingCartRequest cartrequest,
                                                  HttpServletRequest request) {
    	  Long userId = (Long) request.getAttribute("userId");
        // Check if userId is null
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        ShoppingCart cartItem = cartService.addToCart(cartrequest, userId);
        return ResponseEntity.ok(cartItem);
    }


 
    @GetMapping("/cart")
    public ResponseEntity<Map<String, Object>> getCartByUserId(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<ShoppingCart> cartItems = cartService.getCartUserId(userId);
        Map<String, Double> priceAndDiscount = cartService.calculateTotalPrice(cartItems);
        //ShoppingCart address=cartService.
        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartItems);

        response.put("totalPrice", priceAndDiscount.get("totalPrice"));
        response.put("totalDiscount", priceAndDiscount.get("totalDiscount"));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


 
}