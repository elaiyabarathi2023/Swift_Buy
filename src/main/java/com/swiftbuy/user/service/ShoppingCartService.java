package com.swiftbuy.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.CouponCodes;	
import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.product.repository.ProductRepository;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.ShoppingCartRequest;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;

import io.swagger.v3.core.util.Json;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressDetailsRepo addressRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Double> calculateTotalPrice(List<ShoppingCart> cartItems) {
        double totalPrice = 0.0;
        double totalCouponDiscount = 0.0;
        double totalOfferDiscount = 0.0;

        for (ShoppingCart item : cartItems) {
            double itemPrice = item.getProduct().getProductPrice() * item.getQuantity();
            double discountPercentage = 0.0;

            // Check if the product has an active offer
            Offer productOffer = item.getProduct().getOffer();
            if (productOffer != null) {
                discountPercentage = productOffer.getDiscountPercentage();
                double offerDiscount = discountPercentage / 100;
                totalOfferDiscount += offerDiscount;
                itemPrice -= ( offerDiscount);
            }

            // Apply coupon discount if applicable
            if (item.getSelectedCouponId() != null) {
                CouponCodes itemCoupon = item.getProduct().getCoupons().stream()
                        .filter(coupon -> coupon.getCouponId().equals(item.getSelectedCouponId()))
                        .findFirst()
                        .orElse(null);

                if (itemCoupon != null) {
                    double couponDiscountPercentage = itemCoupon.getDiscountPercentage();
                    double couponDiscount = couponDiscountPercentage / 100;
                    totalCouponDiscount += couponDiscount;
                    itemPrice -= (couponDiscount);
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

    public ShoppingCart addToCart(ShoppingCartRequest cartRequest, Long userId) {
        ProductDetails product = productRepository.findById(cartRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CouponCodes selectedCoupon = null;
        if (cartRequest.getSelectedCouponId() != null) {
            selectedCoupon = product.getCoupons().stream()
                    .filter(coupon -> coupon.getCouponId().equals(cartRequest.getSelectedCouponId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Selected coupon is not valid for this product"));
        }

        ShoppingCart cartItem;
        Optional<ShoppingCart> cartItemOptional = shoppingCartRepository.findByUserIdAndProductProductId(userId, cartRequest.getProductId());
        if (cartItemOptional.isPresent()) {
            cartItem = cartItemOptional.get();
        } else {
            cartItem = new ShoppingCart();
            cartItem.setProduct(product);
        }

        if (cartRequest.getQuantity() == 0) {
            shoppingCartRepository.delete(cartItem);
            return null;
           
        }

        if (cartRequest.getQuantity() > product.getProductQuantity()) {
            throw new IllegalArgumentException("Cannot add more products to the cart!!!!");
        }

        cartItem.setUserId(userId);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setSelectedCouponId(cartRequest.getSelectedCouponId());

        ShoppingCart savedCartItem = shoppingCartRepository.save(cartItem);
        List<ShoppingCart> cartItems = shoppingCartRepository.findByUserId(userId);
        Map<String, Double> priceAndDiscount = calculateTotalPrice(cartItems);

        return savedCartItem;
    }

    public AddressDetails selectAddress(Long addressId, Long userId) {
        UserDetails user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Unselect any previously selected address for the user
        List<AddressDetails> userAddresses = addressRepository.findByUserId(userId);
        for (AddressDetails address : userAddresses) {
            address.setSelected(false);
        }
        addressRepository.saveAll(userAddresses);

        AddressDetails selectedAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        // Set the user and mark the address as selected
        selectedAddress.setUserId(userId);
        selectedAddress.setSelected(true);

        return addressRepository.save(selectedAddress);
    }

    public AddressDetails getAddressForUserByUserId(Long userId) {
        return addressRepository.findByUserIdAndIsSelectedTrue(userId);
                
    }

             
    

    public List<ShoppingCart> getCartUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public void clearCart(Long userId) {
        List<ShoppingCart> cartItems = getCartUserId(userId);
        for (ShoppingCart cartItem : cartItems) {
            shoppingCartRepository.delete(cartItem);
        }
    }
}