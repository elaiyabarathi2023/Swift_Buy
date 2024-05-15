package com.swiftbuy.admin.model.CustomerServiceCategory;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;


//postman Query:
//{
//    "name": "New SubCategory Nssmsm,ame fm,eiemxmx,mc,m",
//    "description": "Description dkldljkdxsm,sd,mds,m of the new SubCategory",
//    "customerservicesubcategory": {
//        "id": 6
//    }}
@Entity
@Table(name = "customer_service_categories_final")
public class CustomerServiceCategory {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_service_subcategory_id", nullable = true)
    private CustomerServiceSubCategory customerservicesubcategory;



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



	public CustomerServiceSubCategory getCustomerservicesubcategory() {
		return customerservicesubcategory;
	}



	public void setCustomerservicesubcategory(CustomerServiceSubCategory customerservicesubcategory) {
		this.customerservicesubcategory = customerservicesubcategory;
	}
    
  


}