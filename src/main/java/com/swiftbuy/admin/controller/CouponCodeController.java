package com.swiftbuy.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.service.CouponCodeService;

@RestController
@RequestMapping("/api/coupons")
public class CouponCodeController {

    @Autowired
    private CouponCodeService couponCodesService;

    @GetMapping
    public ResponseEntity<List<CouponCodes>> getAllCouponCodes() {
        List<CouponCodes> couponCodes = couponCodesService.getAllCouponCodes();
        return ResponseEntity.ok(couponCodes);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponCodes> getCouponCodeById(@PathVariable Long couponId) {
        CouponCodes couponCode = couponCodesService.getCouponCodeById(couponId);
        return couponCode != null ? ResponseEntity.ok(couponCode) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<CouponCodes> createCouponCode(@RequestBody CouponCodes couponCode) {
        CouponCodes createdCouponCode = couponCodesService.createCouponCode(couponCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCouponCode);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<CouponCodes> updateCouponCode(@PathVariable Long couponId, @RequestBody CouponCodes couponCode) {
        couponCode.setCoup_id(couponId);
        CouponCodes updatedCouponCode = couponCodesService.updateCouponCode(couponCode);
        return ResponseEntity.ok(updatedCouponCode);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCouponCode(@PathVariable Long couponId) {
        couponCodesService.deleteCouponCode(couponId);
        return ResponseEntity.noContent().build();
    }
}
