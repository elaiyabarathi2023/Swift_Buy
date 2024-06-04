package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.user.repository.OrderItemRepository;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.service.ShoppingCartService;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
	  @Autowired
   private ShoppingCartService cartService;
	@MockBean
	  private ShoppingCartRepository cartRepository;
	@MockBean
	  private OrderRepository orderRepository;
	@MockBean
	  private OrderItemRepository orderItemRepository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
   
    @Test
    public void  testAddCart() throws Exception {
    	  // Login and get the token
    	  JSONObject loginJson = new JSONObject();
    	  loginJson.put("email", "ssss567@gmail.com");
    	  loginJson.put("password", "mO8x@123");
    	  String loginUser = loginJson.toString();

    	  MvcResult mvcResult = mockMvc
    	          .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
    	                  .contentType(MediaType.APPLICATION_JSON))
    	          .andExpect(MockMvcResultMatchers.status().isCreated())
    	          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

    	  String responseString = mvcResult.getResponse().getContentAsString();
    	  JSONObject responseJson = new JSONObject(responseString);
    	  String token = responseJson.getString("token");

    	  // Add an item to the cart
    	  JSONObject cartItemJson = new JSONObject();
    	  cartItemJson.put("productId", 1L);
    	  cartItemJson.put("quantity", 1L);
    	  cartItemJson.put("selectedCouponId", 1L);

    	  mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add").content(cartItemJson.toString())
    	          .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
    	          .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

    	 
    	}
    @Test
    public void testAddAddress() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ssss567@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject responseJson = new JSONObject(responseString);
        String token = responseJson.getString("token");

        // Add an address
        JSONObject addressRequest = new JSONObject();
        addressRequest.put("addressId", 2L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/address").content(addressRequest.toString())
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void testGetCartByUserId() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ssss567@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject responseJson = new JSONObject(responseString);
        String token = responseJson.getString("token");

        // Get the cart
        MvcResult cartResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/shoppingcart").header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String cartResponseString = cartResult.getResponse().getContentAsString();
        JSONObject cartJson = new JSONObject(cartResponseString);

        // Assertions
        assertTrue(cartJson.has("cartItems"));
        assertTrue(cartJson.has("totalPrice"));
        assertTrue(cartJson.has("totalCouponDiscount"));
        assertTrue(cartJson.has("totalOfferDiscount"));
        assertTrue(cartJson.has("addressDetails"));
    }
   
}
