package com.swiftbuy.user.service;


import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.repository.CouponCodeRepository;
import com.swiftbuy.product.repository.ProductRepository;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.ShoppingCartRequest;
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
import java.util.Set;

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
        double totalCouponDiscount = 0.0;
        double totalOfferDiscount = 0.0;

        for (ShoppingCart item : cartItems) {
            double itemPrice = item.getProduct().getProductPrice() * item.getQuantity();
            double discountPercentage = 0.0;

            // Check if the product has an active offer
            Offer productOffer = item.getProduct().getOffer();
            if (productOffer != null ) {
                discountPercentage = productOffer.getDiscountPercentage();
                double offerDiscount = discountPercentage / 100;
                totalOfferDiscount += offerDiscount ;
                itemPrice *= (1 - offerDiscount);
            }

            // Apply coupon discount if applicable
            if (item.getSelectedCouponId() != null) {
                CouponCodes itemCoupon = item.getProduct().getCoupons().stream()
                        .filter(coupon -> coupon.getCouponId().equals(item.getSelectedCouponId()))
                        .findFirst()
                        .orElse(null);

                if (itemCoupon != null) {
                    double couponDiscountPercentage = itemCoupon.getDiscountPercentage();
                    double couponDiscount = couponDiscountPercentage/ 100;
                    totalCouponDiscount += couponDiscount;
                    itemPrice -= couponDiscount;
                }

            }

            totalPrice += itemPrice;
        }

        Map<String, Double> result = new HashMap<>();
        result.put("totalPrice", totalPrice);
        result.put("totalCouponDiscount", totalCouponDiscount);
        result.put("totalOfferDiscount", totalOfferDiscount);
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
         
        }
        if (cartrequest.getQuantity() == 0) {
            shoppingCartRepository.delete(cartItem);
            return null; 
        }
        if (cartrequest.getQuantity() > product.getProductQuantity()) {
            throw new IllegalArgumentException("{\"message\": \"Can't add anymore products\"}");
        }
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartrequest.getQuantity());
        cartItem.setSelectedCouponId(cartrequest.getSelectedCouponId());

    

        AddressDetails address = addressDetailsRepository.findByIdAndUserUserId(cartrequest.getAddressId(), userId);
        	    //.orElseThrow(() -> new IllegalArgumentException("The selected address does not belong to the user"));
            // Set the address in the ShoppingCart object
            cartItem.setAddress(address);
        // Set the address in the ShoppingCart object
        cartItem.setAddress(address);

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