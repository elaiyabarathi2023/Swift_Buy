package com.swiftbuy.admin.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Category {
	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long category_id;
	
	private String name;
	
	

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	
 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
