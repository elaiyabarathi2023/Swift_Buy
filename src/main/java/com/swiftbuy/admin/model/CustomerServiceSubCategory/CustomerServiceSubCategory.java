
package com.swiftbuy.admin.model.CustomerServiceSubCategory;
 
import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
 
@Entity

@Table(name = "customer_service_sub_categories_part")

public class CustomerServiceSubCategory {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
 
    @Column(nullable = false, unique = false)

    private String name;
 
    @Column(nullable = false,unique = false)

    private String description;
 
    @ManyToOne

    @JoinColumn(name = "category_id", nullable = false)

    private CustomerServiceCategory category;

 
	public CustomerServiceCategory getCategory() {

		return category;

	}
 
	public void setCategory(CustomerServiceCategory category) {

		this.category = category;

	}
 
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
 
	
 
    

}