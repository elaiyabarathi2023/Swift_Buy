package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

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

import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.ProductCouponRequest;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.repository.CouponCodeRepository;
import com.swiftbuy.admin.service.ProductCouponService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductCouponTestcase {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductCouponService productCouponService;
    @MockBean
    private ProductRepository productRepo;
    @MockBean
    private CouponCodeRepository couponRepo;

    @BeforeEach
    public void setUpMocks() {
        ProductDetails product = new ProductDetails();
        product.setProductId(252L);
        when(productRepo.findById(252L)).thenReturn(Optional.of(product));

        CouponCodes coupon = new CouponCodes();
        coupon.setCouponId(2L);
        when(couponRepo.findByCouponId(2L)).thenReturn(Optional.of(coupon));
    }

    @Test
    public void testAddProductToCoupon() throws Exception {
        Long productId = 252L;
        Long couponId = 2L;

        JSONObject productCoupon = new JSONObject();
        JSONArray couponIdsArray = new JSONArray();
        couponIdsArray.put(couponId);
        productCoupon.put("couponIds", couponIdsArray);

        mockMvc.perform(post("/products/{productId}/coupons", productId)
                        .content(productCoupon.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddProductToCoupon_ProductNotFound() throws Exception {
        Long productId = 999L; // Non-existing product ID
        Long couponId = 2L;

        JSONObject productCoupon = new JSONObject();
        JSONArray couponIdsArray = new JSONArray();
        couponIdsArray.put(couponId);
        productCoupon.put("couponIds", couponIdsArray);

        mockMvc.perform(post("/products/{productId}/coupons", productId)
                        .content(productCoupon.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testAddProductToCoupon_CouponNotFound() throws Exception {
        Long productId = 252L;
        Long couponId = 999L; // Non-existing coupon ID

        JSONObject productCoupon = new JSONObject();
        JSONArray couponIdsArray = new JSONArray();
        couponIdsArray.put(couponId);
        productCoupon.put("couponIds", couponIdsArray);

        mockMvc.perform(post("/products/{productId}/coupons", productId)
                        .content(productCoupon.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testAddProductToCoupon_InvalidProductId() throws Exception {
        Long productId = null; // Invalid product ID
        Long couponId = 2L;

        JSONObject productCoupon = new JSONObject();
        JSONArray couponIdsArray = new JSONArray();
        couponIdsArray.put(couponId);
        productCoupon.put("couponIds", couponIdsArray);

        mockMvc.perform(post("/products/{productId}/coupons", productId)
                        .content(productCoupon.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testAddProductToCoupon_InvalidCouponId() throws Exception {
        Long productId = 252L;
        Long couponId = null; // Invalid coupon ID

        JSONObject productCoupon = new JSONObject();
        JSONArray couponIdsArray = new JSONArray();
        couponIdsArray.put(couponId);
        productCoupon.put("couponIds", couponIdsArray);

        mockMvc.perform(post("/products/{productId}/coupons", productId)
                        .content(productCoupon.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    // New tests for ProductCouponRequest getters and setters
    @Test
    public void testProductCouponRequestGettersAndSetters() {
        ProductCouponRequest request = new ProductCouponRequest();

        request.setProductcoupon_id(1L);
        assertEquals(1L, request.getProductcoupon_id());

        request.setProductId(2L);
        assertEquals(2L, request.getProductId());

        request.setCouponIds(Arrays.asList(1L, 2L, 3L));
        assertEquals(Arrays.asList(1L, 2L, 3L), request.getCouponIds());
    }
}
