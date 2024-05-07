package com.swiftbuy.admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ProductDetails1")
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	public enum ProductStatus {
		ACTIVE, INACTIVE, DISCONTINUED
	}

	public enum ProductStock {
		IN_STOCK, OUT_OF_STOCK, LOW_STOCK
	}

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Product stock is mandatory")
	private ProductStock productStock;

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

//    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
//    private UserDetails userdetails;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id", nullable = false)
	private SubCategory subcategory;

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
		this.productStatus = productStatus;
	}

	public String getProductName() {
		return productName;
	}

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
		this.estimatedDelivery = estimatedDelivery;
	}

	public ProductStock getProductStock() {
		return productStock;
	}

	public void setProductStock(ProductStock productStock) {
		this.productStock = productStock;
	}

	public SubCategory getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubCategory subcategory) {
		this.subcategory = subcategory;
	}

}