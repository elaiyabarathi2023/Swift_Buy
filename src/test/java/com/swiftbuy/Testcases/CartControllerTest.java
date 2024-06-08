package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.model.CouponCodes;
import com.swiftbuy.admin.model.Offer;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.service.ShoppingCartService;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
	@Autowired
	private ShoppingCartService cartService;
	@MockBean
	private ShoppingCartRepository cartRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	private ProductDetails mockProduct;
	private ShoppingCart existingCartItem;
	@Mock
	private ProductRepository productRepository;
	

	@Test
	public void testAddCart() throws Exception {
		// Login and get the token
		JSONObject loginJson = new JSONObject();
		loginJson.put("email", "alan789@gmail.com");
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
		loginJson.put("email", "alan789@gmail.com");
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
		addressRequest.put("addressId", 1L);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/address").content(addressRequest.toString())
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testGetCartByUserId() throws Exception {
		// Login and get the token
		JSONObject loginJson = new JSONObject();
		loginJson.put("email", "alan789@gmail.com");
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

	@Test
	public void testCalculateTotalPrice() throws Exception {
	    // Prepare login request JSON
	    JSONObject loginJson = new JSONObject();
	    loginJson.put("email", "alan789@gmail.com");
        loginJson.put("password", "mO8x@123");
	    String loginUser = loginJson.toString();

	    // Perform login and retrieve token
	    MvcResult mvcResult = mockMvc
	            .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andReturn();

	    String responseString = mvcResult.getResponse().getContentAsString();
	    JSONObject responseJson = new JSONObject(responseString);
	    String token = responseJson.getString("token");

	    // Create mock products
	    ProductDetails product1 = new ProductDetails();
	    product1.setProductId(752L);
	    product1.setProductPrice(50.0);

	    ProductDetails product2 = new ProductDetails();
	    product2.setProductId(753L);
	    product2.setProductPrice(100.0);

	    // Create mock offers
	    Offer offer1 = new Offer();
	    offer1.setDiscountPercentage(10.0);
	    product1.setOffer(offer1);

	    // Create mock coupons
	    CouponCodes coupon1 = new CouponCodes();
	    coupon1.setCouponId(1L);
	    coupon1.setDiscountPercentage(20.0);

	    CouponCodes coupon2 = new CouponCodes();
	    coupon2.setCouponId(1L);
	    coupon2.setDiscountPercentage(15.0);

	    // Set coupons for products
	    product1.setCoupons(new HashSet<>(Arrays.asList(coupon1)));
	    product2.setCoupons(new HashSet<>(Arrays.asList(coupon2)));

	    // Create mock cart items
	    ShoppingCart item1 = new ShoppingCart();
	    item1.setProduct(product1);
	    item1.setQuantity(2);
	    item1.setSelectedCouponId(1L);

	    ShoppingCart item2 = new ShoppingCart();
	    item2.setProduct(product2);
	    item2.setQuantity(1);
	    item2.setSelectedCouponId(1L);

	    List<ShoppingCart> cartItems = Arrays.asList(item1, item2);

	    // Mock the repository to return our cart items
	    when(cartRepository.findByUserId(1L)).thenReturn(cartItems);

	    // Get the cart
	    MvcResult cartResult = mockMvc
	            .perform(MockMvcRequestBuilders.get("/api/shoppingcart").header("Authorization", "Bearer " + token))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andReturn();

	    String cartResponseString = cartResult.getResponse().getContentAsString();
	    JSONObject cartJson = new JSONObject(cartResponseString);

	    // Assertions
	    assertTrue(cartJson.has("cartItems"));
	    assertTrue(cartJson.has("totalPrice"));
	    assertTrue(cartJson.has("totalCouponDiscount"));
	    assertTrue(cartJson.has("totalOfferDiscount"));
	    assertTrue(cartJson.has("addressDetails"));

	    // Verify the total price and discounts
	    Map<String, Double> result = cartService.calculateTotalPrice(cartItems);

	    // Calculate expected values
	 // Calculate expected values
	 // Calculate expected values
	    double expectedTotalPrice = 157.0;
	    double expectedTotalCouponDiscount = 33.0; // 20%
	    double expectedTotalOfferDiscount = 10.0; // 10%

	    // Assertions
	    assertEquals(expectedTotalPrice, result.get("totalPrice"), 0.001);
	    assertEquals(expectedTotalCouponDiscount, result.get("totalCouponDiscount"), 0.001);
	    assertEquals(expectedTotalOfferDiscount, result.get("totalOfferDiscount"), 0.001);

	    // Assertions
	    assertEquals(expectedTotalPrice, result.get("totalPrice"), 0.001);
	    assertEquals(expectedTotalCouponDiscount, result.get("totalCouponDiscount"), 0.001);
	    assertEquals(expectedTotalOfferDiscount, result.get("totalOfferDiscount"), 0.001);
	}

	@Test
	public void testClearCart() {
		// Create mock cart items
		ProductDetails product1 = new ProductDetails();
		product1.setProductId(752L);
		product1.setProductPrice(50.0);

		ShoppingCart item1 = new ShoppingCart();
		item1.setProduct(product1);
		item1.setQuantity(2);

		ShoppingCart item2 = new ShoppingCart();
		item2.setProduct(product1);
		item2.setQuantity(1);

		List<ShoppingCart> cartItems = Arrays.asList(item1, item2);

		// Mock the repository to return the cart items for the user
		when(cartRepository.findByUserId(1L)).thenReturn(cartItems);

		// Call the clearCart method
		cartService.clearCart(1L);

		// Verify that the delete method was called for each cart item
		for (ShoppingCart cartItem : cartItems) {
			verify(cartRepository).delete(cartItem);
		}

		// Verify that findByUserId was called once
		verify(cartRepository, times(1)).findByUserId(1L);
	}

	@Test
	public void testAddAddress_NotFound() throws Exception {
		// Login and get the token
		JSONObject loginJson = new JSONObject();
		loginJson.put("email", "alan789@gmail.com");
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
		addressRequest.put("addressId", 900);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/address").content(addressRequest.toString())
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testAddCart_QuantityGreaterThanProductQuantity() throws Exception {
		// Login and get the token
		JSONObject loginJson = new JSONObject();
		loginJson.put("email", "alan789@gmail.com");
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
		cartItemJson.put("quantity", 1000L);
		cartItemJson.put("selectedCouponId", 1L);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add").content(cartItemJson.toString())
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn();

	}

	@Test
	public void testAddCart_ProductNotFound() throws Exception {
		// Login and get the token
		JSONObject loginJson = new JSONObject();
		loginJson.put("email", "alan789@gmail.com");
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
		cartItemJson.put("productId", 1000L);
		cartItemJson.put("quantity", 1L);
		cartItemJson.put("selectedCouponId", 1L);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add").content(cartItemJson.toString())
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

	}
	@Test
	public void testAddToCart_QuantityZero() throws Exception {
	    // Mocking the behavior of the repository
	    ShoppingCart shoppingCartMock = new ShoppingCart();
	    when(cartRepository.findByUserIdAndProductProductId(227L, 1L)).thenReturn(Optional.of(shoppingCartMock));
	    
	    // Login and get the token
	    JSONObject loginJson = new JSONObject();
	    loginJson.put("email", "alan789@gmail.com");
        loginJson.put("password", "mO8x@123");
	    String loginUser = loginJson.toString();

	    MvcResult mvcResult = mockMvc
	            .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andReturn();

	    String responseString = mvcResult.getResponse().getContentAsString();
	    JSONObject responseJson = new JSONObject(responseString);
	    String token = responseJson.getString("token");

	    // Add an item with quantity 0 to the cart
	    JSONObject cartItemJson = new JSONObject();
	    cartItemJson.put("productId", 1L);
	    cartItemJson.put("quantity", 0L);
	    cartItemJson.put("selectedCouponId", 1L);

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add")
	            .content(cartItemJson.toString())
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + token))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andReturn();

	    // Verify that the delete method was called on the repository
	    verify(cartRepository, times(1)).delete(any(ShoppingCart.class));
	}

	@Test
	public void testAddCart_ValidCoupon() throws Exception {
	    // Login and get the token
	    JSONObject loginJson = new JSONObject();
	    loginJson.put("email", "alan789@gmail.com");
        loginJson.put("password", "mO8x@123");
	    String loginUser = loginJson.toString();

	    MvcResult mvcResult = mockMvc
	            .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andReturn();

	    String responseString = mvcResult.getResponse().getContentAsString();
	    JSONObject responseJson = new JSONObject(responseString);
	    String token = responseJson.getString("token");

	    // Create mock product with coupons
	    ProductDetails product = new ProductDetails();
	    product.setProductId(752L);
	    product.setProductPrice(100.0);

	    CouponCodes coupon = new CouponCodes();
	    coupon.setCouponId(1L);
	    coupon.setDiscountPercentage(20.0);

	    product.setCoupons(new HashSet<>(Arrays.asList(coupon)));

	    // Mock the repository to return the product
	    when(productRepository.findById(752L)).thenReturn(Optional.of(product));

	    // Add an item to the cart with a valid coupon
	    JSONObject cartItemJson = new JSONObject();
	    cartItemJson.put("productId", 1L);
	    cartItemJson.put("quantity", 1L);
	    cartItemJson.put("selectedCouponId", 1L);

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add")
	            .content(cartItemJson.toString())
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + token))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn();
	}

	@Test
	public void testAddCart_InvalidCoupon() throws Exception {
	    // Login and get the token
	    JSONObject loginJson = new JSONObject();
	    loginJson.put("email", "alan789@gmail.com");
        loginJson.put("password", "mO8x@123");
	    String loginUser = loginJson.toString();

	    MvcResult mvcResult = mockMvc
	            .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
	                    .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	            .andReturn();

	    String responseString = mvcResult.getResponse().getContentAsString();
	    JSONObject responseJson = new JSONObject(responseString);
	    String token = responseJson.getString("token");

	    // Create mock product without coupons
	    ProductDetails product = new ProductDetails();
	    product.setProductId(752L);
	    product.setProductPrice(100.0);
	    product.setCoupons(new HashSet<>());

	    // Mock the repository to return the product
	    when(productRepository.findById(752L)).thenReturn(Optional.of(product));

	    // Add an item to the cart with an invalid coupon
	    JSONObject cartItemJson = new JSONObject();
	    cartItemJson.put("productId", 752L);
	    cartItemJson.put("quantity", 1L);
	    cartItemJson.put("selectedCouponId", 9L);

	    mockMvc.perform(MockMvcRequestBuilders.post("/api/shoppingcart/add")
	            .content(cartItemJson.toString())
	            .contentType(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Bearer " + token))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}
}
