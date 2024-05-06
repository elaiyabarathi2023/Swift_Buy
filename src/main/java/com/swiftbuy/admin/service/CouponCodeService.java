package com.swiftbuy.admin.service;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.repository.CouponCodeRepository;

@Service
public class CouponCodeService {

    @Autowired
    private CouponCodeRepository couponCodesRepository;

    public List<CouponCodes> getAllCouponCodes() {
        Iterable<CouponCodes> couponCodesIterable = couponCodesRepository.findAll();
        List<CouponCodes> couponCodes = new LinkedList<>();
        couponCodesIterable.forEach(couponCodes::add);
        return couponCodes;
    }

    public CouponCodes getCouponCodeById(Long couponId) {
        return couponCodesRepository.findById(couponId).orElse(null);
    }

    public CouponCodes createCouponCode(CouponCodes couponCode) {
        return couponCodesRepository.save(couponCode);
    }

    public CouponCodes updateCouponCode(CouponCodes couponCode) {
        return couponCodesRepository.save(couponCode);
    }

    public void deleteCouponCode(Long couponId) {
        couponCodesRepository.deleteById(couponId);
    }
}