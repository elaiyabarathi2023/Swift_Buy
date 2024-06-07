package com.swiftbuy.Testcases;
 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
 
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.transaction.Transactional;
 
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OfferControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @Test
    public void whenGet_AllOffersWithCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 
    @Test
    public void whenGet_OfferByIdWithCorrectResponse() throws Exception {
        Long offerId = 1L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                
    }
 
    @Test
    public void whenGet_OfferByIdNotFound() throws Exception {
        Long offerId = 999L; // Use an offerId that does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
 
    @Test
    public void whenPost_CreateOfferWithCorrectResponse() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);
 
        JSONObject productJson = new JSONObject();
        productJson.put("productId", 1); // Ensure this productId exists in your test database
        json.put("product", productJson);
 
        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/offers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offer))
                .andExpect(status().isCreated());
                
    }
    
    @Test
    public void whenPost_CreateOfferWithoutProduct_ThrowsRuntimeException() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);
        
        // Note: No product field included in the JSON
 
        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/offers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offer))
                .andExpect(status().isInternalServerError());
    }
 
 
    @Test
    public void whenPut_UpdateOfferWithCorrectResponse() throws Exception {
        Long offerId = 2L; // Ensure this offerId exists in your test database
        JSONObject json = new JSONObject();
        json.put("offerName", "Winter Sale");
        json.put("offerDescription", "Up to 50% off on all winter items");
        json.put("discountPercentage", 50.0);
 
        JSONObject productJson = new JSONObject();
        productJson.put("productId", 1); // Ensure this productId exists in your test database
        json.put("product", productJson);
 
        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}", offerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(offer))
                .andExpect(status().isOk());
                
    }
    @Test
    public void whenPut_OfferWithMissingProductInfo_ThrowsException() throws Exception {
        Long offerId = 2L; // Ensure this offerId exists in your test database
        JSONObject json = new JSONObject();
        json.put("offerName", "Spring Sale");
        json.put("offerDescription", "Up to 30% off on all spring items");
        json.put("discountPercentage", 30.0);
 
        // Intentionally omitting product information to simulate the error
        // json.put("product", productJson);
 
        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}", offerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(offer))
                .andExpect(status().isBadRequest());
               
    }
    
 
 
    @Test
    public void whenDelete_OfferWithCorrectResponse() throws Exception {
        Long offerId = 1L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}