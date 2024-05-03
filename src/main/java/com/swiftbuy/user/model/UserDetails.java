package com.swiftbuy.user.model;

import java.util.List;

import com.swiftbuy.admin.model.ProductDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "UserDetails")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Username should not be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

  
    private String password;

 
    private String email;

    @NotBlank(message = "Name should not be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;

 
    private String phoneNumber;
	@OneToMany(mappedBy = "userdetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductDetails> productdetails;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<ProductDetails> getProductdetails() {
		return productdetails;
	}
	public void setProductdetails(List<ProductDetails> productdetails) {
		this.productdetails = productdetails;
	}
 

}