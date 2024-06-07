package com.swiftbuy.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.ShoppingCartRequest;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;
import com.swiftbuy.user.service.ShoppingCartService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
@RestController
@Configuration
@SecurityScheme(   name = "Bearer Authentication",   type = SecuritySchemeType.HTTP,   bearerFormat = "JWT",   scheme = "bearer" )
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;
   @Autowired
   private AddressDetailsRepo addressDetailsRepository;
   @PostMapping("/add")
   public ResponseEntity<Map<String, Object>> addToCart(@RequestBody ShoppingCartRequest cartrequest, HttpServletRequest request) {
       Claims claims = (Claims) request.getAttribute("claims");
       String userIdString = claims.get("userId", String.class);
       long userId = Long.parseLong(userIdString);
 
       Map<String, Object> response = new HashMap<>();
 
       try {
           // Call the service method with the userId
           cartService.addToCart(cartrequest, userId);
 
           if(cartrequest.getQuantity()==0)
           {
        	   response.put("message", "Product deleted successfully!!!");
               response.put("status", true);
               return new ResponseEntity<>(response, HttpStatus.OK);
           }
           else {
           response.put("message", "Product added to cart successfully");
           response.put("status", true);
 
           return new ResponseEntity<>(response, HttpStatus.CREATED);
       }
       }
       catch(ResourceNotFoundException e) {
           // Create a failure response
           response.put("message", "Failed to add product to cart");
           response.put("status", false);
 
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       }
   }
   @PostMapping("/address")
   public ResponseEntity<Map<String, Object>>  addAddress(@RequestBody Map<String, Long> addressRequest, HttpServletRequest request) {
       Claims claims = (Claims) request.getAttribute("claims");
       String userIdString = claims.get("userId", String.class);
       Long userId = Long.valueOf(userIdString);
       Map<String, Object> response = new HashMap<>();
 
       try {
           Long addressId = addressRequest.get("addressId");
           cartService.selectAddress(addressId, userId);
           response.put("message", "Address Selected successfully!!!");
           response.put("status", true);
           return new ResponseEntity<>(response, HttpStatus.OK);
       } catch (ResourceNotFoundException e) {
    	   response.put("message", "Unable To Select Address");
           response.put("status", false);
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       }
   }
 

   @GetMapping
   public ResponseEntity<Map<String, Object>> getCartByUserId(HttpServletRequest request) {
       Claims claims = (Claims) request.getAttribute("claims");
       String userIdString = claims.get("userId", String.class);
       Long userId = Long.valueOf(userIdString);
 
       List<ShoppingCart> cartItems = cartService.getCartUserId(userId);
       Map<String, Double> priceAndDiscount = cartService.calculateTotalPrice(cartItems);
 
       // Fetch address details for the user by userId
       AddressDetails addressDetails = cartService.getAddressForUserByUserId(userId);
 
       Map<String, Object> response = new TreeMap<>();
       response.put("cartItems", cartItems);
       response.put("totalPrice", priceAndDiscount.get("totalPrice"));
       response.put("totalCouponDiscount", priceAndDiscount.get("totalCouponDiscount"));
       response.put("totalOfferDiscount", priceAndDiscount.get("totalOfferDiscount"));
       response.put("addressDetails", addressDetails);
 
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
 
 

}