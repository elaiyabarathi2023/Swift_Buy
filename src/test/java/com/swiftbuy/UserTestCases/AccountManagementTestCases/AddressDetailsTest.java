package com.swiftbuy.UserTestCases.AccountManagementTestCases;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.user.controller.AccountManangement.AddressDetailsController;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.AccountManangement.AddressDetailsRepo;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.AccountManangement.AddressDetailsService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AddressDetailsController.class)
public class AddressDetailsTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AddressDetailsRepo addressDetailsRepository;

  @MockBean
  private UserRepository userDetailsRepository;

  @MockBean
  private AddressDetailsService addressDetailsService;

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
      objectMapper = new ObjectMapper();
  }

  @Test
  public void testGetAllAddressDetails() throws Exception {
      List<AddressDetails> addressDetails = new ArrayList<>();
      addressDetails.add(new AddressDetails());
      addressDetails.add(new AddressDetails());
//
//      when(addressDetailsService.getAllAddressDetails()).thenReturn(addressDetails);

      mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses"))
              .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetAddressDetailsById() throws Exception {
      Long addressDetailsId = 1L;
      AddressDetails addressDetails = new AddressDetails();
      addressDetails.setId(addressDetailsId);

//      when(addressDetailsService.getAddressDetailsById(addressDetailsId)).thenReturn(addressDetails);

      mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses/{id}", addressDetailsId))
              .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testCreateAddressDetails() throws Exception {
      AddressDetails addressDetails = new AddressDetails();
      addressDetails.setAddressType(null);
      addressDetails.setPermanentAddress("123 Main St");
      addressDetails.setCurrentAddress("456 Oak St");
      addressDetails.setStreetAddress("789 Elm St");
      addressDetails.setCity("AnyCity");
      addressDetails.setState("AnyState");
      addressDetails.setZipCode("12345");
      addressDetails.setCountry("AnyCountry");
      UserDetails user = new UserDetails();
      user.setUserId(1L);
      addressDetails.setUser(user);

//      when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(user));
//      when(addressDetailsService.createAddressDetails(any(AddressDetails.class))).thenReturn(addressDetails);

      JSONObject requestBody = new JSONObject();
      requestBody.put("addressType", "Home");
      requestBody.put("permanentAddress", "123 Main St");
      requestBody.put("currentAddress", "456 Oak St");
      requestBody.put("streetAddress", "789 Elm St");
      requestBody.put("city", "AnyCity");
      requestBody.put("state", "AnyState");
      requestBody.put("zipCode", "12345");
      requestBody.put("country", "AnyCountry");
      requestBody.put("user", new JSONObject().put("userId", 1));

      mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody.toString()))
              .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void testUpdateAddressDetails() throws Exception {
      Long addressDetailsId = 1L;
      AddressDetails addressDetails = new AddressDetails();
      addressDetails.setId(addressDetailsId);
      addressDetails.setAddressType(null);
      addressDetails.setPermanentAddress("123 Main St");
      addressDetails.setCurrentAddress("456 Oak St");
      addressDetails.setStreetAddress("789 Elm St");
      addressDetails.setCity("AnyCity");
      addressDetails.setState("AnyState");
      addressDetails.setZipCode("12345");
      addressDetails.setCountry("AnyCountry");
      UserDetails user = new UserDetails();
      user.setUserId(1L);
      addressDetails.setUser(user);

//      when(addressDetailsService.getAddressDetailsById(addressDetailsId)).thenReturn(addressDetails);
//      when(userDetailsRepository.findById(1L)).thenReturn(Optional.of(user));
//      when(addressDetailsService.updateAddressDetails(addressDetailsId, any(AddressDetails.class))).thenReturn(addressDetails);

      JSONObject requestBody = new JSONObject();
      requestBody.put("addressType", "Home");
      requestBody.put("permanentAddress", "123 Main St");
      requestBody.put("currentAddress", "456 Oak St");
      requestBody.put("streetAddress", "789 Elm St");
      requestBody.put("city", "AnyCity");
      requestBody.put("state", "AnyState");
      requestBody.put("zipCode", "12345");
      requestBody.put("country", "AnyCountry");
      requestBody.put("user", new JSONObject().put("userId", 1));

      mockMvc.perform(MockMvcRequestBuilders.put("/api/addresses/{id}", addressDetailsId)
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody.toString()))
              .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testDeleteAddressDetails() throws Exception {
      Long addressDetailsId = 1L;
      AddressDetails addressDetails = new AddressDetails();
      addressDetails.setId(addressDetailsId);

//      when(addressDetailsService.getAddressDetailsById(addressDetailsId)).thenReturn(addressDetails);
//      Mockito.doNothing().when(addressDetailsService).deleteAddressDetails(addressDetailsId);

      mockMvc.perform(MockMvcRequestBuilders.delete("/api/addresses/{id}", addressDetailsId))
              .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
  
  
  
}
