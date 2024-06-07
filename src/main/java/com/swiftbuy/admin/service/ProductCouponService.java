package com.swiftbuy.admin.service;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.CouponCodeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductCouponService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponCodeRepository couponCodeRepository;

    public ProductDetails addCouponsToProduct(Long productId, List<Long> couponIds) {
        ProductDetails product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        Set<CouponCodes> coupons = new HashSet<>();
        for (Long couponId : couponIds) {
            CouponCodes coupon = couponCodeRepository.findByCouponId(couponId)
                    .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + couponId));
            coupons.add(coupon);
        }

        product.setCoupons(coupons);
        return productRepository.save(product);
    }
}