package com.swiftbuy.admin.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.DiscountAndOffers;
import com.swiftbuy.admin.repository.DiscountAndOffer;


@Service
public class DiscountService {

    @Autowired
    private DiscountAndOffer discountAndOffersRepository;

    public List<DiscountAndOffers> getAllDiscounts() {
        Iterable<DiscountAndOffers> discountsIterable = discountAndOffersRepository.findAll();
        List<DiscountAndOffers> discounts = new LinkedList<>();
        discountsIterable.forEach(discounts::add);
        return discounts;
    }

    public DiscountAndOffers getDiscountById(Long discountId) {
        return discountAndOffersRepository.findById(discountId).orElse(null);
    }

    public DiscountAndOffers createDiscount(DiscountAndOffers discount) {
        return discountAndOffersRepository.save(discount);
    }

    public DiscountAndOffers updateDiscount(DiscountAndOffers discount) {
        return discountAndOffersRepository.save(discount);
    }

    public void deleteDiscount(Long discountId) {
        discountAndOffersRepository.deleteById(discountId);
    }
}