package com.swiftbuy.admin.model;




import jakarta.persistence.*;

@Entity
@Table(name = "AdminOfferForProducts")
public class AdminOfferForProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Define the join to ProductDetails
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "productId", nullable = false)
    private ProductDetails productDetails;

    // Additional fields
    private Double minOffer;
    private Double maxOffer;

    // Constructors, getters, and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public Double getMinOffer() {
        return minOffer;
    }

    public void setMinOffer(Double minOffer) {
        this.minOffer = minOffer;
    }

    public Double getMaxOffer() {
        return maxOffer;
    }

    public void setMaxOffer(Double maxOffer) {
        this.maxOffer = maxOffer;
    }
}
