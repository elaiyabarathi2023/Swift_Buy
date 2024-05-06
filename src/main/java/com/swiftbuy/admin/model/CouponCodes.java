package com.swiftbuy.admin.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class CouponCodes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long coup_id;
	
	private String name;
	
	private String description;

	public Long getCoup_id() {
		return coup_id;
	}

	public void setCoup_id(Long coup_id) {
		this.coup_id = coup_id;
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