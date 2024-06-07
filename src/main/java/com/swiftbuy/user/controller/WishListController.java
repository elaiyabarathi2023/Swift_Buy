
package com.swiftbuy.user.controller;
 
import com.swiftbuy.user.model.WishList;
import com.swiftbuy.user.service.WishListService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
 
import java.util.List;
 
@RestController
@RequestMapping("/api/wishlists")
public class WishListController {
 
    @Autowired
    private WishListService wishListService;
 
    @PostMapping
    public ResponseEntity<WishList> addToWishlist(@RequestBody WishList wishlist, HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
 
        WishList createdWishlist = wishListService.addToWishlist(wishlist, userId);
        return new ResponseEntity<>(createdWishlist, HttpStatus.CREATED);
    }
 

 
    @GetMapping("/user")
    public ResponseEntity<List<WishList>> getWishlistByUserId(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String userIdString = claims.get("userId", String.class);
        Long userId = Long.valueOf(userIdString);
 
        List<WishList> wishlists = wishListService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlists);
    }
 
 
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long wishlistId,HttpServletRequest request) {
    	 Claims claims = (Claims) request.getAttribute("claims");
         String userIdString = claims.get("userId", String.class);
         Long userId = Long.valueOf(userIdString);
        boolean removed = wishListService.removeFromWishlist(wishlistId,userId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
 