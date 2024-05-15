package com.swiftbuy.user.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.server.ResponseStatusException;
 
import com.swiftbuy.admin.model.ProductDetails;

import com.swiftbuy.product.repository.ProductRepository;

import com.swiftbuy.user.model.UserDetails;

import com.swiftbuy.user.model.WishList;

import com.swiftbuy.user.repository.UserRepository;

import com.swiftbuy.user.repository.WishlistRepository;

import java.util.List;
import java.util.Optional;

@Service

public class WishListService {

    @Autowired

    private WishlistRepository wishlistRepository;

    @Autowired

    private UserRepository userRepository;

    @Autowired

    private ProductRepository productRepository;

    public WishList addToWishlist(WishList wishlist, Long userId) {

         userId = wishlist.getUser().getUserId();

        Long productId = wishlist.getProduct().getProductId();

        // Find user by ID

        Optional<UserDetails> userFromDB = userRepository.findById(userId);

        if (userFromDB.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }

        wishlist.setUser(userFromDB.get());

        // Find product by ID

        Optional<ProductDetails> productFromDB = productRepository.findById(productId);

        if (productFromDB.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        }

        wishlist.setProduct(productFromDB.get());

        // Save cart item

        return wishlistRepository.save(wishlist);

    }

    public WishList getWishListById(Long wishlistId) {

        Optional<WishList> wishlistItem = wishlistRepository.findById(wishlistId);

        if (wishlistItem.isPresent()) {

            return wishlistItem.get();

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wishlist item not found");

        }

    }
    
    public List<WishList> getWishlistByUserId(Long userId) {
        Optional<UserDetails> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return wishlistRepository.findByUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }


    public boolean removeFromWishlist(Long wishlistId) {

        // Find the wishlist item by ID

        Optional<WishList> wishlistItem = wishlistRepository.findById(wishlistId);
 
        if (wishlistItem.isPresent()) {

            wishlistRepository.delete(wishlistItem.get());

            return true;

        } else {

            return false;

        }

    }
 
    }


