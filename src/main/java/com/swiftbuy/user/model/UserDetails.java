package com.swiftbuy.user.model;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.swiftbuy.user.CustomValidations.*;
import com.swiftbuy.user.CustomValidations.ValidPhone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@NotBlank
@Size(min = 3, max = 5, message = "Firstname must be between 3 and 20 characters long")
private String firstname;
@PasswordValidations
private String password;
@ValidEmail
private String email;
//@OneToOne(cascade=CascadeType.REFRESH)
//@JsonIgnore
//private ShoppingCart shoppingCart;
public String getPhoneNumber() {
return phoneNumber;
}
public void setPhoneNumber(String phoneNumber) {
this.phoneNumber = phoneNumber;
}
//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//@JsonIgnore
//private List<Order> orders;
@ValidPhone
private String phoneNumber;
//public List<Order> getOrders() {
//return orders;
//}
//public void setOrders(List<Order> orders) {
//this.orders = orders;
//}
//public ShoppingCart getShoppingCart() {
//return shoppingCart;
//}
//public void setShoppingCart(ShoppingCart shoppingCart) {
//this.shoppingCart = shoppingCart;
//}
public Long getUserId() {
return userId;
}
//public void setUserId(Long userId) {
//this.userId = userId;
//}
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
//@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//private AddressDetails address;
//public AddressDetails getAddress() {
//return address;
//}
//public void setAddress(AddressDetails address) {
//this.address = address;
//}
public void setCreatedAt(Date date) {
// TODO Auto-generated method stub
}
}