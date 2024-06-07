package com.swiftbuy.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.ShoppingCartRequest;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private ProductRepository productRepository;
    private AddressDetailsRepo addressRepository;
    private UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, AddressDetailsRepo addressRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Double> calculateTotalPrice(List<ShoppingCart> cartItems) {
        double totalPrice = 0.0;
        double totalCouponDiscount = 0.0;
        double totalOfferDiscount = 0.0;

        for (ShoppingCart item : cartItems) {
            double basePrice = item.getProduct().getProductPrice() * item.getQuantity();
            double itemPrice = basePrice;

            // Apply offer discount if available
            Offer productOffer = item.getProduct().getOffer();
            if (productOffer != null) {
                double offerDiscountPercentage = productOffer.getDiscountPercentage();
                double offerDiscountAmount = basePrice * (offerDiscountPercentage / 100);
                totalOfferDiscount += offerDiscountAmount;
                itemPrice -= offerDiscountAmount; // Subtract the discount amount
            }

            // Apply coupon discount if applicable
            if (item.getSelectedCouponId() != null) {
                CouponCodes itemCoupon = item.getProduct().getCoupons().stream()
                        .filter(coupon -> coupon.getCouponId().equals(item.getSelectedCouponId()))
                        .findFirst()
                        .orElse(null);

                if (itemCoupon != null) {
                    double couponDiscountPercentage = itemCoupon.getDiscountPercentage();
                    double couponDiscountAmount = itemPrice * (couponDiscountPercentage / 100);
                    totalCouponDiscount += couponDiscountAmount;
                    itemPrice -= couponDiscountAmount; // Subtract the discount amount
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

        if (cartRequest.getSelectedCouponId() != null) {
            product.getCoupons().stream()
                    .filter(coupon -> coupon.getCouponId().equals(cartRequest.getSelectedCouponId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Selected coupon is not valid for this product"));
        }

        Optional<ShoppingCart> cartItemOptional = shoppingCartRepository.findByUserIdAndProductProductId(userId, cartRequest.getProductId());

        if (cartRequest.getQuantity() == 0) {
            if (cartItemOptional.isPresent()) {
                shoppingCartRepository.delete(cartItemOptional.get());
            }
            return null;
        }

        if (cartRequest.getQuantity() > product.getProductQuantity()) {
            throw new IllegalArgumentException("Cannot add more products to the cart than available quantity!");
        }

        ShoppingCart cartItem = cartItemOptional.orElseGet(() -> {
            ShoppingCart newItem = new ShoppingCart();
            newItem.setProduct(product);
            newItem.setUserId(userId);
            return newItem;
        });

        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setSelectedCouponId(cartRequest.getSelectedCouponId());

        ShoppingCart savedCartItem = shoppingCartRepository.save(cartItem);

        // No need to recalculate total price here, as it's done in the getCartByUserId method
        return savedCartItem;
    }

    public AddressDetails selectAddress(Long addressId, Long userId) {
        // No need to fetch user if not using it
        // Optional<UserDetails> user = userRepository.findById(userId);

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