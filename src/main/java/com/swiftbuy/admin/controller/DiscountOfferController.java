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

import com.swiftbuy.admin.model.DiscountAndOffers;
import com.swiftbuy.admin.service.DiscountService;

@RestController
@RequestMapping("/api/discounts")
public class DiscountOfferController {

    @Autowired
    private DiscountService discountAndOffersService;

    @GetMapping
    public ResponseEntity<List<DiscountAndOffers>> getAllDiscounts() {
        List<DiscountAndOffers> discounts = discountAndOffersService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{discountId}")
    public ResponseEntity<DiscountAndOffers> getDiscountById(@PathVariable Long discountId) {
        DiscountAndOffers discount = discountAndOffersService.getDiscountById(discountId);
        return discount != null ? ResponseEntity.ok(discount) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<DiscountAndOffers> createDiscount(@RequestBody DiscountAndOffers discount) {
        DiscountAndOffers createdDiscount = discountAndOffersService.createDiscount(discount);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountAndOffers> updateDiscount(@PathVariable Long discountId, @RequestBody DiscountAndOffers discount) {
        discount.setDiscountId(discountId);
        DiscountAndOffers updatedDiscount = discountAndOffersService.updateDiscount(discount);
        return ResponseEntity.ok(updatedDiscount);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId) {
        discountAndOffersService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}