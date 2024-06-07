package com.swiftbuy.UserTestCases.AccountManagementTestCases;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.model.AccountManangement.AddressType;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;
import com.swiftbuy.user.service.AccountManangement.AddressDetailsService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AddressDetailsTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    @Autowired
    private AddressDetailsService addressDetailsService;
 
    @Autowired
    private AddressDetailsRepo addressDetailsRepository;
   
 
    
//    
    @Test
    public void testCreateAddressDetails() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();
 
        MvcResult loginResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginUser)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
 
        String loginResponseString = loginResult.getResponse().getContentAsString();
        JSONObject loginResponseJson = new JSONObject(loginResponseString);
        String token = loginResponseJson.getString("token");
 
        // Create the addressJson object with all required details
        JSONObject addressJson = new JSONObject();
        addressJson.put("addressType", "HOME");
        addressJson.put("permanentAddress", "123 Maddinf St");
        addressJson.put("currentAddress", "456 Oak fSkdt");
        addressJson.put("streetAddress", "789 Elmf Stjd");
        addressJson.put("city", "AnfyCitdy");
        addressJson.put("state", "AnyfStadte");
        addressJson.put("zipCode", "12f345df");
        addressJson.put("country", "AnyfCoduntry");
 
        // Mock the service call and expected behavior
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setAddressType(AddressType.HOME);
        addressDetails.setPermanentAddress("123 Maddinf St");
        addressDetails.setCurrentAddress("456 Oak fSkdt");
        addressDetails.setStreetAddress("789 Elmf Stjd");
        addressDetails.setCity("AnfyCitdy");
        addressDetails.setState("AnyfStadte");
        addressDetails.setZipCode("12f345df");
        addressDetails.setCountry("AnyfCoduntry");
        addressDetails.setUserId(2L); // Assuming a user ID of 2 for this example
 
        AddressDetails savedAddressDetails = new AddressDetails();
        savedAddressDetails.setId(5L);
        savedAddressDetails.setAddressType(AddressType.HOME);
        savedAddressDetails.setPermanentAddress("123 Maddinf St");
        savedAddressDetails.setCurrentAddress("456 Oak fSkdt");
        savedAddressDetails.setStreetAddress("789 Elmf Stjd");
        savedAddressDetails.setCity("AnfyCitdy");
        savedAddressDetails.setState("AnyfStadte");
        savedAddressDetails.setZipCode("12f345df");
        savedAddressDetails.setCountry("AnyfCoduntry");
        savedAddressDetails.setUserId(2L);
 
     
 
        mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                .content(addressJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated());
  }
   
   
 
 
//    
//    
    @Test
    public void testGetAllAddressDetails() throws Exception {
    	// Login and get the token
		JSONObject loginJson = new JSONObject();
//		loginJson.put("email", "bharathiyaril@gmail.com");
//		loginJson.put("password", "SO8x@123882");
		
		
		loginJson.put("email", "ameha123@gmail.com");
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
 
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/list")
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testGetAddresDetailsById() throws Exception {
    	// Login and get the token
		JSONObject loginJson = new JSONObject();
//		loginJson.put("email", "bharathiyaril@gmail.com");
//		loginJson.put("password", "SO8x@123882");
		
		
		loginJson.put("email", "ameha123@gmail.com");
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
 
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/1")
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testGetAddresDetailsById_NotFound() throws Exception {
    	// Login and get the token
		JSONObject loginJson = new JSONObject();
//		loginJson.put("email", "bharathiyaril@gmail.com");
//		loginJson.put("password", "SO8x@123882");
		
		loginJson.put("email", "ameha123@gmail.com");
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
 
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/999")
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
//    
//    
//    
   @Test
    public void testCreateAndUpdateAddressDetails() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();
 
        MvcResult loginResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginUser)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
 
        String loginResponseString = loginResult.getResponse().getContentAsString();
        JSONObject loginResponseJson = new JSONObject(loginResponseString);
        String token = loginResponseJson.getString("token");
 
        // Create the addressJson object with all required details
        JSONObject addressJson = new JSONObject();
        addressJson.put("addressType", "HOME");
        addressJson.put("permanentAddress", "123 Maddinf St");
        addressJson.put("currentAddress", "456 Oak fSkdt");
        addressJson.put("streetAddress", "789 Elmf Stjd");
        addressJson.put("city", "AnfyCitdy");
        addressJson.put("state", "AnyfStadte");
        addressJson.put("zipCode", "12f345df");
        addressJson.put("country", "AnyfCoduntry");
 
        // Create the address
        MvcResult createAddressResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                .content(addressJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
 
        String createAddressResponseString = createAddressResult.getResponse().getContentAsString();
        JSONObject createAddressResponseJson = new JSONObject(createAddressResponseString);
        long createdAddressId = createAddressResponseJson.getLong("id");
 
        // Update the created address
        JSONObject updatedAddressJson = new JSONObject();
        updatedAddressJson.put("addressType", "OFFICE");
        updatedAddressJson.put("permanentAddress", "321 Main St");
        updatedAddressJson.put("currentAddress", "654 Oak St");
        updatedAddressJson.put("streetAddress", "987 Elm St");
        updatedAddressJson.put("city", "NewCity");
        updatedAddressJson.put("state", "NewState");
        updatedAddressJson.put("zipCode", "54321");
        updatedAddressJson.put("country", "NewCountry");
 
        mockMvc.perform(MockMvcRequestBuilders.put("/api/addresses/" + createdAddressId)
                        .content(updatedAddressJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isOk());
   }
////    
//    
//    
//    
    @Test
    public void testCreateAndUpdateAddressDetails_InvalidId() throws Exception {
        // Login and get the token
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("password", "mO8x@123");
        String loginUser = loginJson.toString();
 
        MvcResult loginResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginUser)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
 
        String loginResponseString = loginResult.getResponse().getContentAsString();
        JSONObject loginResponseJson = new JSONObject(loginResponseString);
        String token = loginResponseJson.getString("token");
 
        // Create the addressJson object with all required details
        JSONObject addressJson = new JSONObject();
        addressJson.put("addressType", "HOME");
        addressJson.put("permanentAddress", "123 Maddinf St");
        addressJson.put("currentAddress", "456 Oak fSkdt");
        addressJson.put("streetAddress", "789 Elmf Stjd");
        addressJson.put("city", "AnfyCitdy");
        addressJson.put("state", "AnyfStadte");
        addressJson.put("zipCode", "12f345df");
        addressJson.put("country", "AnyfCoduntry");
//
        // Create the address
        MvcResult createAddressResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
                .content(addressJson.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
 
        String createAddressResponseString = createAddressResult.getResponse().getContentAsString();
        JSONObject createAddressResponseJson = new JSONObject(createAddressResponseString);
        long createdAddressId = createAddressResponseJson.getLong("id");
 
        // Update the created address
        JSONObject updatedAddressJson = new JSONObject();
        updatedAddressJson.put("addressType", "OFFICE");
        updatedAddressJson.put("permanentAddress", "321 Main St");
        updatedAddressJson.put("currentAddress", "654 Oak St");
        updatedAddressJson.put("streetAddress", "987 Elm St");
        updatedAddressJson.put("city", "NewCity");
        updatedAddressJson.put("state", "NewState");
        updatedAddressJson.put("zipCode", "54321");
        updatedAddressJson.put("country", "NewCountry");
 
        mockMvc.perform(MockMvcRequestBuilders.put("/api/addresses/89")
                        .content(updatedAddressJson.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
//    
    @Test
  public void testDeleteAddressDetails_InvalidId() throws Exception {
  	// Login and get the token
		JSONObject loginJson = new JSONObject();
//		loginJson.put("email", "bharathiyarilen@gmail.com");
//		loginJson.put("password", "SO8x@123881");
		loginJson.put("email", "ameha123@gmail.com");
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
 
      // Assuming an address with id 1 exists for the test user
      mockMvc.perform(MockMvcRequestBuilders.delete("/api/addresses/99")
                      .header("Authorization", "Bearer " + token))
             .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
    
    
 
 
  @Test
  public void testDeleteAddressDetails() throws Exception {
  	// Login and get the token
		JSONObject loginJson = new JSONObject();
//		loginJson.put("email", "bharathiyarilen@gmail.com");
//		loginJson.put("password", "SO8x@123881");
		loginJson.put("email", "ameha123@gmail.com");
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
 
      // Assuming an address with id 1 exists for the test user
      mockMvc.perform(MockMvcRequestBuilders.delete("/api/addresses/1")
                      .header("Authorization", "Bearer " + token))
             .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
    
    
    @Test
    public void testGetAddressDetailsById_NotFound() throws Exception {
    	// Login and get the token
		JSONObject loginJson = new JSONObject();
 
		
		loginJson.put("email", "ameha123@gmail.com");
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
    	
        mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/78")
                        .header("Authorization", "Bearer " + token))
               .andExpect(MockMvcResultMatchers.status().isNotFound());
   }
       
//    PASSED covverage Also
 
		
		@Test
		public void testGetAddressDetailsById_NotFound_ThrowsException() throws Exception {
		    // Login and get the token
		    JSONObject loginJson = new JSONObject();
		    loginJson.put("email", "ameha123@gmail.com");
	        loginJson.put("password", "mO8x@123");
		    String loginUser = loginJson.toString();
		    MvcResult mvcResult = this.mockMvc
		        .perform(MockMvcRequestBuilders.post("/user/loginuser").content(loginUser)
		                 .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(MockMvcResultMatchers.status().isCreated())
		        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		        .andReturn();
		    String responseString = mvcResult.getResponse().getContentAsString();
		    JSONObject responseJson = new JSONObject(responseString);
		    String token = responseJson.getString("token");
 
		    // Perform the request and assert the status
		    this.mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/")
		                .header("Authorization", "Bearer " + token))
		        .andExpect(MockMvcResultMatchers.status().isNotFound());
		}
 
    
}