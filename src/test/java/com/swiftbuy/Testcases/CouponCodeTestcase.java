package com.swiftbuy.Testcases;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.repository.CouponCodeRepository;
import com.swiftbuy.user.model.WishList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CouponCodeTestcase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponCodeRepository couponCodeRepository;
    @BeforeEach
    public void setUpMocks() {
        CouponCodes coupon = new CouponCodes();
        coupon.setCouponId(1L);
        when(couponCodeRepository.findById(1L)).thenReturn(Optional.of(coupon));
       
    }
    @Test
    public void testGetAllCouponCodes() throws Exception {
        // Mock coupon codes repository behavior
        when(couponCodeRepository.findAll()).thenReturn(new ArrayList<>());

        // Perform the GET request to retrieve all coupon codes
        mockMvc.perform(MockMvcRequestBuilders.get("/api/coupons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testGetAllCouponCodesById() throws Exception {
        Long couponId=1L;
        when(couponCodeRepository.findAll()).thenReturn(new ArrayList<>());

        // Perform the GET request to retrieve all coupon codes
        mockMvc.perform(MockMvcRequestBuilders.get("/api/coupons/{couponId}",couponId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void testAddCoupon() throws Exception {
        
        // Add an item to the wishlist
        JSONObject couponJson = new JSONObject();
        
        couponJson.put("name", "Festival Coupons");
        couponJson.put("description", "10% of on coupons");
        couponJson.put("discountPercentage", 10.0);

        mockMvc.perform(post("/api/coupons/add")
                        .content(couponJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        
                .andExpect(status().isCreated())
                .andReturn();
    }
    @Test
    public void testUpdateCoupon() throws Exception {
        Long couponId = 1L;
        
        // Prepare the request body with updated coupon details
        JSONObject couponJson = new JSONObject();
        couponJson.put("name", "Updated Coupon Name");
        couponJson.put("description", "Updated coupon description.");
        couponJson.put("discountPercentage", 20.0); // Updated discount percentage

        mockMvc.perform(put("/api/coupons/{couponId}", couponId) // Include couponId as a path variable
                .content(couponJson.toString()) // Set the request body
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expecting a successful update (status code 200)
                .andReturn();
    }
    @Test
    public void testDeleteCouponCode() throws Exception {
        Long couponId = 1L; // Replace with the ID of an existing coupon code

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/coupons/{couponId}", couponId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Coupon deleted successfully"));
    }
    @Test
    public void testDeleteCouponCode_NonExistentCouponId() throws Exception {
        Long nonExistentCouponId = 999L; // Replace with a coupon ID that doesn't exist

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/coupons/{couponId}", nonExistentCouponId))
                .andExpect(status().isNotFound());
    }
}
