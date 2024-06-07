package com.swiftbuy.admin.model;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
		ACTIVE, INACTIVE, DISCONTINUED
	}
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	public Category getCategory() {
		return category;
	}
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Offer offer;
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
	@NotBlank(message = "Estimated delivery is mandatory")
	private String estimatedDelivery;
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id", nullable = false)
	private SubCategory subcategory;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "product_coupons", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "coupon_id", nullable = true))
	private Set<CouponCodes> coupons;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
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
	public String getEstimatedDelivery() {
		return estimatedDelivery;
	}
	public void setEstimatedDelivery(String estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}
	public SubCategory getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubCategory subcategory) {
		this.subcategory = subcategory;
	}
	public Set<CouponCodes> getCoupons() {
		return coupons;
	}
	public void setCoupons(Set<CouponCodes> coupons) {
		this.coupons = coupons;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}