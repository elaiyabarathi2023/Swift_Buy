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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
   

    public Map<String, Double> calculateTotalPrice(List<ShoppingCart> cartItems) {
        double totalPrice = 0.0;
        double totalDiscount = 0.0;

        for (ShoppingCart item : cartItems) {
            double itemPrice = item.getProduct().getProductPrice() * item.getQuantity();

            if (item.getSelectedCouponId() != null) {
                CouponCodes itemCoupon = item.getProduct().getCoupons().stream()
                        .filter(coupon -> coupon.getCouponId().equals(item.getSelectedCouponId()))
                        .findFirst()
                        .orElse(null);

                if (itemCoupon != null) {
                    double discountPercentage = itemCoupon.getDiscountPercentage();
                    double discount = discountPercentage/100;
                    totalDiscount += discount;
                    itemPrice *= discount;
                }
            }

            totalPrice += itemPrice;
        }

        Map<String, Double> result = new HashMap<>();
        result.put("totalPrice", totalPrice);
        result.put("totalDiscount", totalDiscount);
        return result;
    }
    public ShoppingCart addToCart(ShoppingCartRequest cartrequest, Long userId) {
        ProductDetails product = productRepository.findById(cartrequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        CouponCodes selectedCoupon = null;
        if (cartrequest.getSelectedCouponId() != null) {
            selectedCoupon = product.getCoupons().stream()
                    .filter(coupon -> coupon.getCouponId().equals(cartrequest.getSelectedCouponId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Selected coupon is not valid for this product"));
        }
        UserDetails user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Optional<ShoppingCart> cartItemOptional = shoppingCartRepository.findByUserUserIdAndProductProductId(userId, cartrequest.getProductId());
        ShoppingCart cartItem;
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
        } else {
            cartItem = new ShoppingCart();
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }
        if (cartrequest.getQuantity() == 0) {
            shoppingCartRepository.delete(cartItem);
            return null; 
        }
        if (cartrequest.getQuantity() > product.getProductQuantity()) {
            throw new IllegalArgumentException("{\"message\": \"Can't add anymore products\"}");
        }
        cartItem.setProduct(product);
        cartItem.setQuantity(cartrequest.getQuantity());
        cartItem.setUser(user);
        cartItem.setSelectedCouponId(cartrequest.getSelectedCouponId());

        // New code to handle address
        AddressDetails address = addressDetailsRepository.findById(cartrequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        cartItem.setAddress(address);
        if (!address.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("The selected address does not belong to the user");
        }
        ShoppingCart savedCartItem = shoppingCartRepository.save(cartItem);
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserUserId(userId);
        Map<String, Double> priceAndDiscount = calculateTotalPrice(cartItems);
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
    public void clearCart(Long userId) {
        // Fetch the cart items for the user
        List<ShoppingCart> cartItems = getCartUserId(userId);

        // Remove each item from the cart
        for (ShoppingCart cartItem : cartItems) {
        	shoppingCartRepository.delete(cartItem);
        }
    }

 
}