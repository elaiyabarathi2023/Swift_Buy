package com.swiftbuy.user.controller;

import com.swiftbuy.user.model.WishList;
import com.swiftbuy.user.service.WishListService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlists")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addToWishlist(@RequestBody WishList wishlist, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
      
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);

            WishList createdWishlist = wishListService.addToWishlist(wishlist, userId);
            response.put("status", true);
            response.put("message", "Item added to wishlist successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getWishlistByUserId(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);

            List<WishList> wishlists = wishListService.getWishlistByUserId(userId);
            response.put("status", true);
            response.put("message", "Wishlist retrieved successfully");
            response.put("wishlists", wishlists);
            return ResponseEntity.ok(response);
        
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Map<String, Object>> removeFromWishlist(@PathVariable Long wishlistId, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
       
            Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
            Long userId = Long.valueOf(userIdString);
            
            boolean removed = wishListService.removeFromWishlist(wishlistId, userId);
           
                response.put("status", true);
                response.put("message", "Item removed from wishlist successfully");
           
                
            return ResponseEntity.ok(response);
        
        }
}