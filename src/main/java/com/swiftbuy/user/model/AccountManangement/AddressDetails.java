
package com.swiftbuy.user.model.AccountManangement;
 
 
 
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.UserDetails;
 
import jakarta.persistence.*;
 
 
//
//Postman Query:
//	
//{
//"addressType": "work",
//"permanentAddress": "123 Main St",
//"currentAddress": "456 Oak St",
//"streetAddress": "789 Elm St",
//"city": "AnyCity",
//"state": "AnyState",
//"zipCode": "12345",
//"country": "AnyCountry",
//"user": {
//"userId": 1
//}
//}
 
@Entity
@Table(name = "addresses")
public class AddressDetails {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
	@Column(nullable = false)
    private String addressType;
    
    @Column(nullable = false)
    private String permanentAddress;
    
    @Column(nullable = false)
    private String currentAddress;
    
 
    @Column(nullable = false)
    private String streetAddress;
 
    @Column(nullable = false)
    private String city;
 
    @Column(nullable = false)
    private String state;
 
    @Column(nullable = false)
    private String zipCode;
 
    @Column(nullable = false)
    private String country;
 
    @ManyToOne(fetch = FetchType.EAGER)
    
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails user;
 
    
 
    // Getters and Setters
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getAddressType() {
        return addressType;
    }
 
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
    
   
    
    public String getPermanentAddress() {
        return permanentAddress;
    }
 
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
 
    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }
 
    public String getStreetAddress() {
        return streetAddress;
    }
 
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
 
    public String getCity() {
        return city;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
 
    public String getState() {
        return state;
    }
 
    public void setState(String state) {
        this.state = state;
    }
 
    public String getZipCode() {
        return zipCode;
    }
 
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
 
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
 
 
    public UserDetails getUser() {
        return user;
    }
 
    public void setUser(UserDetails user) {
        this.user = user;
    }
}
 
