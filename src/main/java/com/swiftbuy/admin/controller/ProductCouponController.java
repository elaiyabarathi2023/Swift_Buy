package com.swiftbuy.admin.controller;

import com.swiftbuy.admin.model.ProductCouponRequest;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.service.ProductCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductCouponController {
    @Autowired
    private ProductCouponService productService;

    @PostMapping("/{productId}/coupons")
    public ResponseEntity<Map<String, Object>> addCouponsToProduct(@PathVariable Long productId, @RequestBody ProductCouponRequest request) {
        Map<String, Object> response = new HashMap<>();
      
            ProductDetails product = productService.addCouponsToProduct(productId, request.getCouponIds());
            response.put("status", true);
            response.put("message", "Coupons added to product successfully");
            return ResponseEntity.ok(response);
       
    }
}