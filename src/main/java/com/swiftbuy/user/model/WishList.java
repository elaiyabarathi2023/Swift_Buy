
package com.swiftbuy.user.model;
 
import com.swiftbuy.admin.model.ProductDetails;
import jakarta.persistence.*;
 
@Entity
@Table(name = "wishlistpart")
public class WishList {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishlistId;
 
    @Column(name = "user_id", nullable = false)
    private Long userId;
 
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetails product;
 
    // Constructors, getters, and setters
    public WishList() {}
 
    public Long getWishlistId() {
        return wishlistId;
    }
 
    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }
 
    public Long getUserId() {
        return userId;
    }
 
    public void setUserId(Long userId) {
        this.userId = userId;
    }
 
    public ProductDetails getProduct() {
        return product;
    }
 
    public void setProduct(ProductDetails product) {
        this.product = product;
    }
}
 