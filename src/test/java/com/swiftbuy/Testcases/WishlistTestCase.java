package com.swiftbuy.Testcases;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.WishList;
import com.swiftbuy.user.repository.WishlistRepository;
import com.swiftbuy.user.service.WishListService;

@SpringBootTest
@AutoConfigureMockMvc
public class WishlistTestCase {
@MockBean
private WishlistRepository wishlistRepo;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WishListService wishlistService;
    @BeforeEach
    public void setUpMocks() {
        WishList wishlist = new WishList();
        wishlist.setWishlistId(752L);
        when(wishlistRepo.findById(752L)).thenReturn(Optional.of(wishlist));
       
    }
    @Test
    public void testAddToWishlist() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        // Add an item to the wishlist
        JSONObject wishlistJson = new JSONObject();
        JSONObject productJson = new JSONObject();
        productJson.put("productId", 752L);
        wishlistJson.put("product", productJson);

        mockMvc.perform(post("/api/wishlists")
                        .content(wishlistJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andReturn();
    }
    @Test
    public void testAddToWishlist_Unauthorized() throws Exception {
        // Given an invalid or expired user token (or no token at all)
        String invalidToken = "invalid_token_here";

        // Create a JSON object representing the wishlist item
        JSONObject wishlistJson = new JSONObject();
        JSONObject productJson = new JSONObject();
        productJson.put("productId", 752L);
        wishlistJson.put("product", productJson);

        // Perform the HTTP request to add the item to the wishlist
        mockMvc.perform(post("/api/wishlists")
                        .content(wishlistJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized());
    }
  



    @Test
    public void testGetWishlistByUserId() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();

        MvcResult loginResult = mockMvc
                .perform(post("/user/loginuser")
                        .content(loginUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String loginResponseString = loginResult.getResponse().getContentAsString();
        JSONObject loginResponseJson = new JSONObject(loginResponseString);
        String token = loginResponseJson.getString("token");

        mockMvc.perform(get("/api/wishlists/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        
    }
    
    @Test
    public void testRemoveFromWishlist() throws Exception {
        
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        Long wishlistId = 752L; // Ensure this ID exists in the setup

        // Perform the HTTP request to remove the item from the wishlist
        mockMvc.perform(delete("/api/wishlists/{wishlistId}", wishlistId)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    public void testAddToWishlist_ProductNotFound() throws Exception {
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        // Add an item to the wishlist
        JSONObject wishlistJson = new JSONObject();
        JSONObject productJson = new JSONObject();
        productJson.put("productId", 10000L);
        wishlistJson.put("product", productJson);

        mockMvc.perform(post("/api/wishlists")
                        .content(wishlistJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testRemoveFromWishlist_IdNotPresent() throws Exception {
        
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJmaXJzdG5hbWUiOiJhbWVoYSIsInVzZXJJZCI6IjEiLCJlbWFpbCI6ImFtZWhhMTIzQGdtYWlsLmNvbSIsInBob25lTnVtYmVyIjoiOTg3MzEyNTAwOCIsInN1YiI6IjEiLCJpc3MiOiJ0aGVlcnRoYSIsImlhdCI6MTcxNzcwODE2NSwiZXhwIjoxNzIwMzAwMTY1fQ.T75IyAq0U4R_iOEhrGHVck01aPfAEwwJxCnR9BADxe8Mi0HhcAhuEOVF8Bt2F6l2";
        Long wishlistId = 699L; // Ensure this ID exists in the setup

        // Perform the HTTP request to remove the item from the wishlist
        mockMvc.perform(delete("/api/wishlists/{wishlistId}", wishlistId)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                
                .andExpect(status().isNotFound())
                .andReturn();
    }
}