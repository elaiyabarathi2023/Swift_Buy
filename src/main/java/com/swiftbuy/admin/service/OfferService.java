
package com.swiftbuy.admin.service;
import java.util.LinkedList;
 
import java.util.List;
 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;
import com.swiftbuy.admin.model.Category;
 
import com.swiftbuy.admin.model.Offer;
 
import com.swiftbuy.admin.model.ProductDetails;
 
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.CategoryRepository;
import com.swiftbuy.admin.repository.OfferRepository;
import com.swiftbuy.admin.repository.SubCategoryRepository;
@Service
 
public class OfferService {
    @Autowired
 
    private OfferRepository offerRepository;
 
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
 
        // Ensure the product is managed by the persistence context
 
        if (offer.getProduct() != null && offer.getProduct().getProductId() != null) {
 
            ProductDetails product = productRepository.findById(offer.getProduct().getProductId())
 
                    .orElseThrow(() -> new RuntimeException("Product not found"));
 
            offer.setProduct(product);
 
        } else {
 
            throw new RuntimeException("Product information is missing");
 
        }
 
        return offerRepository.save(offer);
 
    }
    public Offer updateOffer(Long offerId, Offer offer) {
 
        // Ensure the product is managed by the persistence context
 
        if (offer.getProduct() != null && offer.getProduct().getProductId() != null) {
 
            ProductDetails product = productRepository.findById(offer.getProduct().getProductId())
 
                    .orElseThrow(() -> new RuntimeException("Product not found"));
 
            offer.setProduct(product);
 
        } else {
 
            throw new RuntimeException("Product information is missing");
 
        }
 
        offer.setId(offerId); // Ensure the correct offer ID is set
 
        return offerRepository.save(offer);
 
    }

    public void deleteOffer(Long offerId) {
 
        offerRepository.deleteById(offerId);
 
    }
 
}