package com.swiftbuy.admin.model;

import java.util.Set;

//import com.swiftbuy.user.model.ShoppingCart;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ProductDetails2")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    public enum ProductStatus {
        ACTIVE,
        INACTIVE,
        DISCONTINUED
    }

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "shopping_cart_id")
//    private ShoppingCart shoppingCart;

//    public ShoppingCart getShoppingCart() {
//        return shoppingCart;
//    }
//
//    public void setShoppingCart(ShoppingCart shoppingCart) {
//        this.shoppingCart = shoppingCart;
  //  }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Product status is mandatory")
    private ProductStatus productStatus;

    @NotBlank(message = "Product name is mandatory")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String productName;

    @NotBlank(message = "Product description is mandatory")
    private String productDescription;

    @NotBlank(message = "Product image URL is mandatory")
    private String productImage;

    @NotNull(message = "Product price is mandatory")
    private Double productPrice;

    @NotNull(message = "Product quantity is mandatory")
    private Integer productQuantity;

    private String productOffers;
    private String cancellationReason;

    @NotBlank(message = "Estimated delivery is mandatory")
    private String estimatedDelivery;

    @NotNull(message = "Product stock is mandatory")
    private Integer productStock;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategory subcategory;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
        name = "product_coupons",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "coupon_id",nullable=true)
    )
    private Set<CouponCodes> coupons;

    public Set<CouponCodes> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<CouponCodes> coupons) {
        this.coupons = coupons;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        if (productStatus == null) {
            throw new IllegalArgumentException("Product status cannot be null");
        }
        this.productStatus = productStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        if (productDescription == null || productDescription.isBlank()) {
            throw new IllegalArgumentException("Product description cannot be null or blank");
        }
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        if (productImage == null || productImage.isBlank()) {
            throw new IllegalArgumentException("Product image URL cannot be null or blank");
        }
        this.productImage = productImage;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        if (productPrice == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        if (productQuantity == null) {
            throw new IllegalArgumentException("Product quantity cannot be null");
        }
        this.productQuantity = productQuantity;
    }

    public String getProductOffers() {
        return productOffers;
    }

    public void setProductOffers(String productOffers) {
        this.productOffers = productOffers;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(String estimatedDelivery) {
        if (estimatedDelivery == null || estimatedDelivery.isBlank()) {
            throw new IllegalArgumentException("Estimated delivery cannot be null or blank");
        }
        this.estimatedDelivery = estimatedDelivery;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        if (productStock == null) {
            throw new IllegalArgumentException("Product stock cannot be null");
        }
        this.productStock = productStock;
    }

    public SubCategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubCategory subcategory) {
        if (subcategory == null) {
            throw new IllegalArgumentException("Subcategory cannot be null");
        }
        this.subcategory = subcategory;
    }
}