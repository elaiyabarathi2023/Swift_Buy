package com.swiftbuy.user.model;

import java.util.Date;
import java.util.List;

import com.swiftbuy.CustomValidations.PasswordValidations;
import com.swiftbuy.CustomValidations.ValidEmail;
import com.swiftbuy.CustomValidations.ValidPhone;
import com.swiftbuy.CustomValidations.ValidUsername;
import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.model.ProductDetails;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "UserDetails")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
@ValidUsername
    
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String firstname;

  @PasswordValidations
    private String password;
      
 @ValidEmail
 
    private String email;
 
@ValidPhone
    private String phoneNumber;
	@OneToMany(mappedBy = "userdetails", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	
	private List<ProductDetails> productdetails;
	@ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private AdminDetails admindetails;
	
	
	public AdminDetails getAdmindetails() {
		return admindetails;
	}
	public void setAdmindetails(AdminDetails admindetails) {
		this.admindetails = admindetails;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String username) {
		this.firstname = username;
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
	public void setCreatedAt(Date date) {
		// TODO Auto-generated method stub
		
	}
 

}