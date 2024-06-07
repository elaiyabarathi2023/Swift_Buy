package com.swiftbuy.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.service.CouponCodeService;

@RestController
@RequestMapping("/api/coupons")
public class CouponCodeController {

    @Autowired
    private CouponCodeService couponCodesService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCouponCodes() {
        Map<String, Object> response = new HashMap<>();
     
            List<CouponCodes> couponCodes = couponCodesService.getAllCouponCodes();
            response.put("status", true);
            response.put("message", "All coupon codes retrieved successfully");
            response.put("coupons", couponCodes);
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> getCouponCodeById(@PathVariable Long couponId) {
        Map<String, Object> response = new HashMap<>();
      
            CouponCodes couponCode = couponCodesService.getCouponCodeById(couponId);
            
                response.put("status", true);
                response.put("message", "Coupon code retrieved successfully");
                response.put("coupon", couponCode);
                return ResponseEntity.ok(response);
            } 
    

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createCouponCode(@RequestBody CouponCodes couponCode) {
        Map<String, Object> response = new HashMap<>();
       
            CouponCodes createdCouponCode = couponCodesService.createCouponCode(couponCode);
            response.put("status", true);
            response.put("message", "Coupon code created successfully");
         
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> updateCouponCode(@PathVariable Long couponId, @RequestBody CouponCodes couponCode) {
        Map<String, Object> response = new HashMap<>();
      
            couponCode.setCouponId(couponId);
            CouponCodes updatedCouponCode = couponCodesService.updateCouponCode(couponCode);
            response.put("status", true);
            response.put("message", "Coupon code updated successfully");
         
            return ResponseEntity.ok(response);
        
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Map<String, Object>> deleteCouponCode(@PathVariable Long couponId) {
        Map<String, Object> response = new HashMap<>();
       
            couponCodesService.deleteCouponCode(couponId);
            response.put("status", true);
            response.put("message", "Coupon code deleted successfully");
            return ResponseEntity.ok(response);
        
}
}