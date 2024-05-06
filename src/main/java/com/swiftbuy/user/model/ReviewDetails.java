package com.swiftbuy.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ReviewDetails")
public class ReviewDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @NotBlank(message = "Review title is mandatory")
    private String title;

    @NotBlank(message = "Review description is mandatory")
    private String description;

    @NotBlank(message = "Review image URL is mandatory")
    private String image;

    @NotNull(message = "Product ID is mandatory")
    private Long productId;

    @NotNull(message = "Category ID is mandatory")
    private Long catId;

    @NotNull(message = "Subcategory ID is mandatory")
    private Long subCatId;

    // Getters and Setters

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

