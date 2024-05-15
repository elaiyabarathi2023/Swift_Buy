package com.swiftbuy.user.model;



 
 
// postman Query:
// {    "user": {
//     "userId": 1
//     // Add other user details if necessary
// },
// "product": {
//     "productId": 1
// }
// 
//}




 
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swiftbuy.admin.model.ProductDetails;
 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "wishlistpart")
public class WishList {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wishlistId;
 
    @ManyToOne
    @JsonIgnoreProperties(value = {"wishList"})  
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails user;
 
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private  ProductDetails product;
 
    // Constructors, getters, and setters
 
    public Long getWishlistId() {
        return wishlistId;
    }
 
    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }
 
    public UserDetails getUser() {
        return user;
    }
 
    public void setUser(UserDetails user) {
        this.user = user;
    }
 
    public ProductDetails getProduct() {
        return product;
    }
 
    public void setProduct(ProductDetails product) {
        this.product = product;
    }
}
 