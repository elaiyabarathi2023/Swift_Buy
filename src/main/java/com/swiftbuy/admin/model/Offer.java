package com.swiftbuy.admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "offer2")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    
    @NotBlank(message = "Offer name cannot be blank")
    private String offerName;

    @NotBlank(message = "Offer description cannot be blank")
    private String offerDescription;

    @NotNull(message = "Discount percentage cannot be null")
    @DecimalMin(value = "0.0", message = "Discount percentage must be greater than or equal to 0")
    @DecimalMax(value = "100.0", message = "Discount percentage must be less than or equal to 100")
    private Double discountPercentage;

    private boolean isActive;

//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    private Long category_id;

//    @ManyToOne
//    @JoinColumn(name = "subcategory_id", nullable = false)
//    private SubCategory subcategory;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetails product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

//	public Long getCategory_id() {
//		return category_id;
//	}
//
//	public void setCategory_id(Long category_id) {
//		this.category_id = category_id;
//	}

//	public SubCategory getSubcategory() {
//		return subcategory;
//	}
//
//	public void setSubcategory(SubCategory subcategory) {
//		this.subcategory = subcategory;
//	}

	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	


	

}
