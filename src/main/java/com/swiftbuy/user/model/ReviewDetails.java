//package com.swiftbuy.user.model;
//
//import com.swiftbuy.admin.model.ProductDetails;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//@Entity
//@Table(name = "ReviewDetails")
//public class ReviewDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long reviewId;
//
//   @NotBlank(message = "Review description is mandatory")
//    private String description;
//
//    @NotBlank(message = "Review image URL is mandatory")
//    private String image;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private ProductDetails product;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private OrderDetails order;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserDetails user;
//
//    @NotNull(message = "Ratings is mandatory")
//    private double ratings;
//    
//	// Getters and Setters
//    public Long getReviewId() {
//		return reviewId;
//	}
//
//	public void setReviewId(Long reviewId) {
//		this.reviewId = reviewId;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public String getImage() {
//		return image;
//	}
//
//	public void setImage(String image) {
//		this.image = image;
//	}
//
//	public ProductDetails getProduct() {
//		return product;
//	}
//
//	public void setProduct(ProductDetails product) {
//		this.product = product;
//	}
//
//	public OrderDetails getOrder() {
//		return order;
//	}
//
//	public void setOrder(OrderDetails order) {
//		this.order = order;
//	}
//
//	public UserDetails getUser() {
//		return user;
//	}
//
//	public void setUser(UserDetails user) {
//		this.user = user;
//	}
//
//	public double getRatings() {
//		return ratings;
//	}
//
//	public void setRatings(double ratings) {
//		this.ratings = ratings;
//	}
//
//	
//}
