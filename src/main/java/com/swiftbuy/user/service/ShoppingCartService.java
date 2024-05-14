package com.swiftbuy.user.service;


import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.model.ShoppingCartRequest;
import com.swiftbuy.admin.repository.CouponCodeRepository;
import com.swiftbuy.product.repository.ProductRepository;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressDetailsRepo addressDetailsRepository;

    public double calculateTotalPrice(List<ShoppingCart> cartItems) {
        double totalPrice = 0.0;

        for (ShoppingCart item : cartItems) {
            double itemPrice = item.getProduct().getProductPrice() * item.getQuantity();
            if (item.getSelectedCouponId() != null) {
                CouponCodes itemCoupon = item.getProduct().getCoupons().stream()
                        .filter(coupon -> coupon.getCouponId().equals(item.getSelectedCouponId()))
                        .findFirst()
                        .orElse(null);
                if (itemCoupon != null) {
                	
                    double discount = item.getProduct().getProductPrice() * itemCoupon.getDiscountPercentage() / 100;
                    itemPrice -= discount;
                }
            }
            totalPrice += itemPrice;
           
        }
        return totalPrice;
    }

    public ShoppingCart addToCart(ShoppingCartRequest cartrequest, Long userId) {
    	
        // Retrieve the product from the database
        ProductDetails product = productRepository.findById(cartrequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check if the selected coupon ID is valid for the product
        CouponCodes selectedCoupon = null;
        if (cartrequest.getSelectedCouponId() != null) {
            selectedCoupon = product.getCoupons().stream()
                    .filter(coupon -> coupon.getCouponId().equals(cartrequest.getSelectedCouponId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Selected coupon is not valid for this product"));
        }

        // Retrieve the user from the database
        UserDetails user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Retrieve the address from the database
        AddressDetails address = addressDetailsRepository.findById(cartrequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // Retrieve the existing cart item
        Optional<ShoppingCart> cartItemOptional = shoppingCartRepository.findByUserUserIdAndProductProductId(userId, cartrequest.getProductId());
        ShoppingCart cartItem;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
        } else {
            cartItem = new ShoppingCart();
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }

        // If the updated quantity is 0, remove the product from the cart
        if (cartrequest.getQuantity() == 0) {
            shoppingCartRepository.delete(cartItem);
            return null; // or return a suitable response indicating the product was removed
        }

        // Check if the updated quantity is more than the available product quantity
        if (cartrequest.getQuantity() > product.getProductQuantity()) {
            throw new IllegalArgumentException("{\"message\": \"Can't add anymore products\"}");
        }

        // Update the cart item
        cartItem.setAddress(address);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartrequest.getQuantity());
        cartItem.setUser(user);
        cartItem.setSelectedCouponId(cartrequest.getSelectedCouponId());

     // Save the cart item
        ShoppingCart savedCartItem = shoppingCartRepository.save(cartItem);

        // Calculate the total price for all cart items of the user
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserUserId(userId);
        double totalPrice = calculateTotalPrice(cartItems);
        System.out.println(totalPrice);

        return savedCartItem;
    }



   
 
    
    public List<ShoppingCart> getCartUserId(Long userId) {
        Optional<UserDetails> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return shoppingCartRepository.findByUser(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
 
 
}