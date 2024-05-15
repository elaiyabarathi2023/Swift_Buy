package com.swiftbuy.user.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swiftbuy.CustomValidations.PasswordValidations;
import com.swiftbuy.CustomValidations.ValidEmail;
import com.swiftbuy.CustomValidations.ValidPhone;
import com.swiftbuy.CustomValidations.ValidUsername;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@OneToOne(cascade=CascadeType.REFRESH)
@JsonIgnore
private ShoppingCart shoppingCart;

@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
@JsonIgnore
private List<Order> orders;
	
	public List<Order> getOrders() {
	return orders;
}
public void setOrders(List<Order> orders) {
	this.orders = orders;
}
	public ShoppingCart getShoppingCart() {
	return shoppingCart;
}
public void setShoppingCart(ShoppingCart shoppingCart) {
	this.shoppingCart = shoppingCart;
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
	
	public void setCreatedAt(Date date) {
		// TODO Auto-generated method stub
		
	}
 

}