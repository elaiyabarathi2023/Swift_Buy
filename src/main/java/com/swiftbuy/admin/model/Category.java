package com.swiftbuy.admin.model;
 
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
 
@Entity
public class Category {
	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryid;
	private String name;
	public Long getCategory_id() {
		return categoryid;
	}
 
	public void setCategory_id(Long category_id) {
		this.categoryid = category_id;
	}
 
	
public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
}