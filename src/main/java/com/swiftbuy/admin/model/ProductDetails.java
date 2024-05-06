package com.swiftbuy.admin.model;

import com.swiftbuy.user.model.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ProductDetails")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

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

    @NotBlank(message = "Estimated delivery is mandatory")
    private String estimatedDelivery;

    @NotNull(message = "Product stock is mandatory")
    private Integer productStock;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private UserDetails userdetails;
    // Getters and Setters

    public UserDetails getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(UserDetails userdetails) {
		this.userdetails = userdetails;
	}

	public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    @NotNull(message = "Category ID is mandatory")
    private Long catId;

    @NotNull(message = "Subcategory ID is mandatory")
    private Long subCatId;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductOffers() {
        return productOffers;
    }

    public void setProductOffers(String productOffers) {
        this.productOffers = productOffers;
    }

    public String getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(String estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }
    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public Long getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Long subCatId) {
        this.subCatId = subCatId;
    }
}
