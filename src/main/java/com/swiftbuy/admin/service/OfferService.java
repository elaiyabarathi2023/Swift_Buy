package com.swiftbuy.admin.service;

//import com.swiftbuy.admin.model.Category;
//import com.swiftbuy.admin.model.Offer;
//import com.swiftbuy.admin.model.ProductDetails;
//import com.swiftbuy.admin.model.SubCategory;
//import com.swiftbuy.admin.repository.OfferRepository;
//import com.swiftbuy.product.repository.ProductRepository;
//import com.swiftbuy.repository.CategoryRepository;
//import com.swiftbuy.subcrepository.SubCategoryRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OfferService {
//
//    @Autowired
//    private OfferRepository offerRepository;
//    
//    @Autowired
//    private CategoryRepository categoryRepository; 
//    
//    @Autowired
//    private SubCategoryRepository subCategoryRepository; 
//    
//    @Autowired
//    private ProductRepository productRepository; 
//    
//
//    public Offer createOffer(Offer offer) {
//    	 if (offer.getSubcategory().getId()==null) {
//         	System.out.println("77777777777777777777777777777777777777777777777777");
//         }
//        Long subCategoryId = offer.getSubcategory().getId();
//        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
//                .orElseThrow(() -> new RuntimeException("Sub Category not found with id " + subCategoryId));
//
//        Long categoryId = offer.getCategory().getCategory_id();
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
//        
//        Long productId = offer.getProduct().getProductId();
//        ProductDetails product= productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Category not found with id " + productId));
//        
//        if (subCategoryId==null) {
//        	System.out.println(subCategoryId);
//        }
//        offer.setSubcategory(subCategory);
//        offer.setCategory(category);
//        offer.setProduct(product);
//
//        return offerRepository.save(offer);
//    }
//
//    public List<Offer> getAllOffers() {
//        return offerRepository.findAll();
//    }
//
//    public Offer getOfferById(Long offerId) {
//        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
//        return optionalOffer.orElseThrow(() -> new RuntimeException("Offer not found with ID: " + offerId));
//    }
//
//    public Offer updateOffer(Long offerId, Offer updatedOffer) {
//        Offer existingOffer = getOfferById(offerId);
//        existingOffer.setOfferName(updatedOffer.getOfferName());
//        existingOffer.setOfferDescription(updatedOffer.getOfferDescription());
//        existingOffer.setDiscountPercentage(updatedOffer.getDiscountPercentage());
//        existingOffer.setActive(updatedOffer.isActive());
//        existingOffer.setCategory(updatedOffer.getCategory());
//        existingOffer.setSubcategory(updatedOffer.getSubcategory());
//        existingOffer.setProduct(updatedOffer.getProduct());
//        return offerRepository.save(existingOffer);
//    }
//
//    public void deleteOffer(Long offerId) {
//        Offer existingOffer = getOfferById(offerId);
//        offerRepository.delete(existingOffer);
//    }
//}

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.repository.OfferRepository;
import com.swiftbuy.product.repository.ProductRepository;
import com.swiftbuy.repository.CategoryRepository;
import com.swiftbuy.subcrepository.SubCategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    
    @Autowired
    private ProductRepository productRepository;

    public List<Offer> getAllOffers() {
        Iterable<Offer> offersIterable = offerRepository.findAll();
        List<Offer> offers = new LinkedList<>();
        offersIterable.forEach(offers::add);
        return offers;
    }

    public Offer getOfferById(Long offerId) {
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        return optionalOffer.orElse(null);
    }

    public Offer createOffer(Offer offer) {
        // Ensure the product, category, and subcategory are fetched from the database
//        Long subCategoryId = offer.getSubcategory().getId();
//        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
//                .orElseThrow(() -> new RuntimeException("Sub Category not found with id " + subCategoryId));

//            Long categoryId = offer.getCategory_id();
//	        Category category = categoryRepository.findById(categoryId)
//	                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
        
        Long productId = offer.getProduct().getProductId();
        ProductDetails product= productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        // Set the fetched entities
//        offer.setSubcategory(subCategory);
//        offer.setCategory_id(categoryId);
        offer.setProduct(product);

        // Save the offer (ID will be automatically generated by the database)
        return offerRepository.save(offer);
    }


    public Offer updateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public void deleteOffer(Long offerId) {
        offerRepository.deleteById(offerId);
    }
}