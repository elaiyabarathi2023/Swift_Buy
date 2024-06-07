package com.swiftbuy.Testcases;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.OfferRepository;
import com.swiftbuy.admin.service.OfferService;
import com.swiftbuy.admin.model.ProductDetails;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
   private  OfferService offerService;

    @MockBean
    private OfferRepository offerRepository;

    @BeforeEach
    public void setUpMocks() {
        // Mocking for offer with ID 53L
        Offer offer53 = new Offer();
        offer53.setId(53L);
        offer53.setActive(true);

        ProductDetails product53 = new ProductDetails();
        product53.setProductId(152L);
        offer53.setProduct(product53);
        when(productRepository.findById(152L)).thenReturn(Optional.of(product53));
        when(offerRepository.findByIdAndIsActiveTrue(53L)).thenReturn(Optional.of(offer53));
        
        // Mocking for offer with ID 56L
        Offer offer56 = new Offer();
        offer56.setId(56L);
        offer56.setActive(false); // Set the offer as inactive

        ProductDetails product56 = new ProductDetails();
        product56.setProductId(1L);
        offer56.setProduct(product56);

        when(offerRepository.findById(56L)).thenReturn(Optional.of(offer56));
       
    }
    @Test
    public void whenGet_AllOffersWithCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGet_OfferByIdWithCorrectResponse() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGet_OfferByIdNotFound() throws Exception {
        Long offerId = 999L; // Use an offerId that does not exist
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenPost_CreateOfferWithCorrectResponse() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);

        JSONObject productJson = new JSONObject();
        productJson.put("productId", 152L); // Ensure this productId exists in your test database
        json.put("product", productJson);

        String offer = json.toString();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/offers/add").contentType(MediaType.APPLICATION_JSON).content(offer))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void whenReactivateOffer_AlreadyActive_ThrowsRuntimeException() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        
        // Mocking the offer retrieval
        Offer offer53 = new Offer();
        offer53.setId(offerId);
        offer53.setActive(true); // Set the offer as active
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer53));
        
        // Perform the reactivation and expect a RuntimeException
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/reactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
               

    
  

    @Test
    public void whenDeactivateOffer_NotExists_ThrowsRuntimeException() throws Exception {
        Long offerId = 999L; // Use an offerId that does not exist
        
        // Mocking the offer retrieval
        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());
        
        // Perform the deactivation and expect a RuntimeException
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPost_CreateOfferWithoutProduct_ThrowsRuntimeException() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);

        // Note: No product field included in the JSON

        String offer = json.toString();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/offers/add").contentType(MediaType.APPLICATION_JSON).content(offer))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut_UpdateOfferWithCorrectResponse() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        JSONObject json = new JSONObject();
        json.put("offerName", "Winter Sale");
        json.put("offerDescription", "Up to 50% off on all winter items");
        json.put("discountPercentage", 50.0);

        JSONObject productJson = new JSONObject();
        productJson.put("productId", 152L); // Ensure this productId exists in your test database
        json.put("product", productJson);

        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}", offerId)
                .contentType(MediaType.APPLICATION_JSON).content(offer)).andExpect(status().isOk());
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
                .contentType(MediaType.APPLICATION_JSON).content(offer)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenDeactivateOffer_ExistsAndActive_Success() throws Exception {
        Long offerId = 53L;
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setActive(true);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Offer deactivated successfully")));

        assertFalse(offer.isActive());
        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    public void whenDeactivateOffer_ExistsAndInactive_ThrowsException() throws Exception {
        Long offerId = 56L;
        Offer offer = new Offer();
        offer.setId(offerId);
        offer.setActive(false);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The offer is already inactive and cannot be deactivated.")));

        verify(offerRepository, never()).save(any(Offer.class));
    }

    @Test
    public void whenDeactivateOffer_NotFound_ThrowsException() throws Exception {
        Long offerId = 999L;

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Offer not found")));

        verify(offerRepository, never()).save(any(Offer.class));
    }
    @Test
    public void whenPut_ReactivateOfferWithCorrectResponse() throws Exception {
        Long offerId = 56L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/reactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
    @Test
    public void whenPost_CreateOfferWithoutProduct_ThrowsBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);

        // Note: No product field included in the JSON

        String offer = json.toString();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/offers/add").contentType(MediaType.APPLICATION_JSON).content(offer))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void whenPut_UpdateOfferWithMissingProductInfo_ThrowsBadRequest() throws Exception {
        Long offerId = 2L; // Ensure this offerId exists in your test database
        JSONObject json = new JSONObject();
        json.put("offerName", "Spring Sale");
        json.put("offerDescription", "Up to 30% off on all spring items");
        json.put("discountPercentage", 30.0);

        // Intentionally omitting product information to simulate the error
        // json.put("product", productJson);

        String offer = json.toString();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}", offerId)
                .contentType(MediaType.APPLICATION_JSON).content(offer)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut_DeactivateNonexistentOffer_ReturnsNotFound() throws Exception {
        Long offerId = 999L; // Use an offerId that does not exist
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut_ReactivateNonexistentOffer_ReturnsNotFound() throws Exception {
        Long offerId = 999L; // Use an offerId that does not exist
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/reactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void whenPut_ReactivateActiveOffer_ThrowsBadRequest() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/reactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
      
}
    @Test
    public void whenGet_AllActiveOffers_ReturnsAllActive() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.offers").isArray());
    }

    @Test
    public void whenGet_OfferByIdActive_ReturnsOffer() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.offer").exists());
    }

    @Test
    public void whenPut_DeactivateOfferAlreadyInactive_ReturnsOk() throws Exception {
        Long offerId = 56L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    // Negative Test Cases

    @Test
    public void whenGet_OfferByIdInactive_ReturnsNotFound() throws Exception {
        Long offerId = 56L; // Ensure this offerId exists in your test database
        mockMvc.perform(MockMvcRequestBuilders.get("/api/offers/{offerId}", offerId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.error").value("Active offer not found"));
    }

    @Test
    public void whenDeactivateOffer_Exists_Success() throws Exception {
        Long offerId = 53L; // Ensure this offerId exists in your test database
        
        // Mocking the offer retrieval
        Offer offer53 = new Offer();
        offer53.setId(offerId);
        offer53.setActive(true);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer53));
        
        // Perform the deactivation
        mockMvc.perform(MockMvcRequestBuilders.put("/api/offers/{offerId}/deactivate", offerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
      
    }

    @Test
    public void whenCreateOffer_ActiveOfferExists_ThrowsException() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);

        JSONObject productJson = new JSONObject();
        productJson.put("productId", 1L);
        json.put("product", productJson);

        ProductDetails product = new ProductDetails();
        product.setProductId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Offer existingOffer = new Offer();
        existingOffer.setProduct(product);
        existingOffer.setActive(true);
        when(offerRepository.findByProductAndIsActiveTrue(product)).thenReturn(Optional.of(existingOffer));

        String offer = json.toString();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/offers/add").contentType(MediaType.APPLICATION_JSON).content(offer))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("An active offer already exists for this product")));
        verify(offerRepository, times(1)).findByProductAndIsActiveTrue(any(ProductDetails.class));
    }
    @Test
    public void whenCreateOffer_ProductNotFound_ThrowsException() throws Exception {
        JSONObject json = new JSONObject();
        json.put("offerName", "Summer Sale");
        json.put("offerDescription", "Up to 70% off on all summer items");
        json.put("discountPercentage", 70.0);

        JSONObject productJson = new JSONObject();
        productJson.put("productId", 999L); // Use a non-existent productId
        json.put("product", productJson);


        String offer = json.toString();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/offers/add").contentType(MediaType.APPLICATION_JSON).content(offer))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Product not found")));
    }

   

   

    


    
}
