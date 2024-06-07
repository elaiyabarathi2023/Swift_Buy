
package com.swiftbuy.user.service;
 
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.WishList;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.repository.WishlistRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import java.util.List;
import java.util.Optional;
 
@Service
public class WishListService {
 
    @Autowired
    private WishlistRepository wishlistRepository;
 
    @Autowired
    private UserRepository userRepository;
 
    @Autowired
    private ProductRepository productRepository;
 
    public WishList addToWishlist(WishList wishlist, Long userId) {
        Long productId = wishlist.getProduct().getProductId();
 
        Optional<UserDetails> userFromDB = userRepository.findById(userId);
     
 
        Optional<ProductDetails> productFromDB = productRepository.findById(productId);
        if (productFromDB.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
 
        wishlist.setUserId(userId);
        wishlist.setProduct(productFromDB.get());
 
        return wishlistRepository.save(wishlist);
    }
    
   
    
    public List<WishList> getWishlistByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }
 
    public boolean removeFromWishlist(Long wishlistId,Long userId) {
    	 userRepository.findById(userId);
        Optional<WishList> wishlistItem = wishlistRepository.findById(wishlistId);
        if (wishlistItem.isPresent()) {
            wishlistRepository.delete(wishlistItem.get());
            return true;
        } else {
            return false;
        }
    }
}
 