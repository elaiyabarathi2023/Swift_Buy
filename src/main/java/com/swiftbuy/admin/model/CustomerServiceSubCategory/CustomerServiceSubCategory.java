package com.swiftbuy.admin.model.CustomerServiceSubCategory;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_service_sub_categories")
public class CustomerServiceSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CustomerServiceCategory category;

    // Constructors

    public CustomerServiceSubCategory() {
    }

    public CustomerServiceSubCategory(String name, String description, CustomerServiceCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerServiceCategory getCategory() {
        return category;
    }

    public void setCategory(CustomerServiceCategory category) {
        this.category = category;
    }
}