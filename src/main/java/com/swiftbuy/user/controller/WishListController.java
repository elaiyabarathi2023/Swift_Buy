package com.swiftbuy.user.controller;

import java.util.List;
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
 
import com.swiftbuy.user.model.WishList;
 
import com.swiftbuy.user.service.WishListService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController

@RequestMapping("api/wishlist")

public class WishListController {

    @Autowired

    private WishListService wishlistService;

    @PostMapping("/add")

    public ResponseEntity<WishList> addToWishlist(@RequestBody WishList wishlist,HttpServletRequest request) {

        try {
        	Claims claims = (Claims) request.getAttribute("claims");
            String userIdString = claims.get("userId", String.class);
    		Long userId = Long.valueOf(userIdString);
             // Check if userId is null
             if (userId == null) {
                 throw new IllegalArgumentException("User ID cannot be null");
             }

            WishList addedWishlist = wishlistService.addToWishlist(wishlist,userId);

            return ResponseEntity.ok(addedWishlist);

        } catch (Exception e) {

        	e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }


    @GetMapping("/{wishlistId}")

    public ResponseEntity<WishList> getWishListById(@PathVariable Long wishlistId) {

        WishList wishlist = wishlistService.getWishListById(wishlistId);

        return ResponseEntity.ok(wishlist);

    }
    
    @GetMapping("user/{userId}")
    public List<WishList> getwishlistByUserId(@PathVariable Long userId) {
        return wishlistService.getWishlistByUserId(userId);
    }

    @DeleteMapping("/{wishlistId}")

    public ResponseEntity<String> removeFromWishlist(@PathVariable String wishlistId) {

        try {

            Long id = Long.parseLong(wishlistId);

            boolean deleted = wishlistService.removeFromWishlist(id);

            if (deleted) {

            	return ResponseEntity.ok("Item successfully deleted from wishlist");

            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found");

            }

        } catch (NumberFormatException e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid wishlist ID", e);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error removing from wishlist", e);

        }

    }

}
