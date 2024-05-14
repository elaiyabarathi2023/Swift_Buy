package com.swiftbuy.admin.controller;

import com.swiftbuy.admin.model.ProductCouponRequest;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.service.ProductCouponService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductCouponController {

    @Autowired
    private ProductCouponService productService;
    @PostMapping("/{productId}/coupons")
    public ResponseEntity<ProductDetails> addCouponsToProduct(@PathVariable Long productId, @RequestBody ProductCouponRequest request) {
        ProductDetails product = productService.addCouponsToProduct(productId, request.getCouponIds());
        return ResponseEntity.ok(product);
    }
}