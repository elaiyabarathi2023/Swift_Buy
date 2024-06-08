package com.swiftbuy.Testcases;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.JwtGenerator;
import com.swiftbuy.user.service.TokenService;
import com.swiftbuy.user.service.UserService;

import jakarta.transaction.Transactional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class UserAuthenticationTesting {
    
    
//    @Autowired
//    private UserService userService;
    
    @Autowired
    TokenService tokenService;
    
    @Autowired
    private MockMvc mockMvc;

    // Positive Test Cases

    @Test
    public void Get_UserDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPost_UserWithCorrectResponse() throws Exception {
        JSONObject json = new JSONObject();

        json.put("firstname", "rima");
        json.put("phoneNumber", "9873345008");
        json.put("email", "rima123@gmail.com");
        json.put("password", "mO8x@123");

        String user = json.toString();

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/user/signupuser").content(user)
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
    public void signupWith_ExistingEmail() throws Exception {

        JSONObject json = new JSONObject();
        json.put("firstname", "ameha");
        json.put("password", "mO8x@123");
        json.put("email", "ameha123@gmail.com ");
        json.put("phoneNumber", "9873125008");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void signupWith_EmptyDetails() throws Exception {

        JSONObject json = new JSONObject();
        json.put("firstname", " ");
        json.put("password", " ");
        json.put("email", "  ");
        json.put("phoneNumber", " ");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void signupWithNullDetails() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", " ");
        json.put("password", " ");
        json.put("email", " ");
        json.put("phoneNumber", " ");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }



    @Test
    public void invalid_Email() throws Exception {

        JSONObject json = new JSONObject();
        json.put("firstname", "anu");
        json.put("password", "Hs7x@123");
        json.put("email", "anu123gmail.com");
        json.put("phoneNumber", "9123456783");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void invalid_Phone() throws Exception {

        JSONObject json = new JSONObject();
        json.put("firstname", "anu");
        json.put("password", null);
        json.put("email", "anu123@gmail.com");
        json.put("phoneNumber", "9123456783999999");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

    }

    @Test
    public void forgotPassword_ResponseOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "ameha123@gmail.com");
        json.put("newPassword", "Rs7x@123");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password").contentType(MediaType.APPLICATION_JSON)
                .content(user)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void forgotPassword_InvalidEmail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "sheena1223@gmail.com");
        json.put("newPassword", "Rs7x@123");
        // Assuming this email does not exist in the database

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password").contentType(MediaType.APPLICATION_JSON)
                .content(user)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

    @Test
    public void forgotPassword_NullEmail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", null);
        json.put("newPassword", "Rs7x@123");
        // Assuming this email does not exist in the database

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password").contentType(MediaType.APPLICATION_JSON)
                .content(user)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    @Test
    public void forgotPassword_EmptyEmail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email","");
        json.put("newPassword", "Rs7x@123");
        // Assuming this email does not exist in the database

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password").contentType(MediaType.APPLICATION_JSON)
                .content(user)).andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(jsonPath("$.error").value("Please enter a valid email address."));
    }

    @Test
    public void null_EmailError() throws Exception {
        JSONObject json = new JSONObject();
        json.put("user_id", 345);
        json.put("username", "anu");
        json.put("password", "anuahhhg");
        json.put("email", JSONObject.NULL);
        json.put("name", JSONObject.NULL);

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").content(user).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Expecting Internal Server Error status
                                                                           // after signup attempt with null email
    }

    @Test
    public void whenPost_ExistingUserDetails() throws Exception {
        JSONObject json = new JSONObject();

        json.put("firstname", "ameha");
        json.put("phoneNumber", "9873125008");
        json.put("email", "ameha123@gmail.com");
        json.put("password", "mO8x@123");

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").content(user).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Expecting Internal Server Error status
                                                                           // after signup attempt with null email
    }

    // New Test Cases

    @Test
    public void loginWith_ValidCredentials() throws Exception {
        JSONObject signupJson = new JSONObject();
        signupJson.put("firstname", "jane");
        signupJson.put("phoneNumber", "9800910004");
        signupJson.put("password", "mO8x@123");
        signupJson.put("email", "jane123@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .content(signupJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "jane123@gmail.com");
        loginJson.put("password", "mO8x@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void loginWith_InvalidCredentials() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "invalidemail@gmail.com");
        loginJson.put("password", "wrongpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // New Test Cases

    @Test
    public void loginUser_WithBothEmailAndPhoneNumber_ShouldThrowException() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("phoneNumber", "1234567890");
        loginJson.put("password", "mO8x@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void loginUser_WithValidEmail_ShouldSearchByEmail() throws Exception {
        // Assuming the user with email "test@example.com" exists in the database
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "ameha123@gmail.com");
        loginJson.put("password", "mO8x@123");

        

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void loginUser_WithValidEmail_ShouldSearchByPhone() throws Exception {
        // Assuming the user with email "test@example.com" exists in the database
        JSONObject loginJson = new JSONObject();
        loginJson.put("phoneNumber", "9873125008");
        loginJson.put("password", "mO8x@123");

        

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void loginUser_WithNullValues() throws Exception {
        // Assuming the user with email "test@example.com" exists in the database
        JSONObject loginJson = new JSONObject();
        loginJson.put("phoneNumber", null);
        loginJson.put("password", null);

        

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void loginUser_WithInvalidPassword() throws Exception {
        // Assuming the user with email "test@example.com" exists in the database
        JSONObject loginJson = new JSONObject();
        loginJson.put("phoneNumber", "9873125008");
        loginJson.put("password", "mO8x@1238997jnbb");

        

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void loginUser_WithEmptyPassword() throws Exception {
        // Assuming the user with email "test@example.com" exists in the database
        JSONObject loginJson = new JSONObject();
        loginJson.put("userId", 6);
        loginJson.put("phoneNumber", "9873125008");
        loginJson.put("password", " ");

        

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void forgotPassword_ValidEmail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "ameha123@gmail.com");
        json.put("newPassword", "Rs7x@123");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Your password has been updated successfully."));
    }

    

   
    @Test
    public void forgotPassword_NullNewPassword() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "ameha123@gmail.com");
        json.put("newPassword", JSONObject.NULL); // Sending null new password

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Your password has been updated successfully."));
    }
    
    @Test
    public void signupWithMissingFields() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "John");
        json.put("email", "john@example.com");
        // Missing password and phone number

        String user = json.toString();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
    
    @Test
    public void loginWithInvalidEmailFormat() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "invalid-email-format");
        loginJson.put("password", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    public void loginWithBothEmptyEmailAndPhoneNumber() throws Exception {
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "");
        loginJson.put("phoneNumber", "");
        loginJson.put("password", "password");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    public void forgotPasswordWithNonMatchingEmail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "nonexistent@example.com");
        json.put("newPassword", "newPassword123!");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON).content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Please enter a valid email address."));
    }
    @Test
    public void updatePasswordAfterLogin() throws Exception {
        // Sign up user
        JSONObject signupJson = new JSONObject();
        signupJson.put("firstname", "jane");
        signupJson.put("phoneNumber", "9800910004");
        signupJson.put("password", "mO8x@123");
        signupJson.put("email", "jane123@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .content(signupJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Login user
        JSONObject loginJson = new JSONObject();
        loginJson.put("email", "jane123@gmail.com");
        loginJson.put("password", "mO8x@123");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject responseJson = new JSONObject(responseString);
        String token = responseJson.getString("token");

        // Update password
        JSONObject updatePasswordJson = new JSONObject();
        updatePasswordJson.put("email", "jane123@gmail.com");
        updatePasswordJson.put("newPassword", "NewP@ssword123");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .content(updatePasswordJson.toString()).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Your password has been updated successfully."));
    }
    
    @Test
    public void loginWithCorrectPhoneNumberAndIncorrectPassword() throws Exception {
        JSONObject signupJson = new JSONObject();
        signupJson.put("firstname", "jane");
        signupJson.put("phoneNumber", "9800910004");
        signupJson.put("password", "mO8x@123");
        signupJson.put("email", "jane123@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .content(signupJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        JSONObject loginJson = new JSONObject();
        loginJson.put("phoneNumber", "9800910004");
        loginJson.put("password", "wrongpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .content(loginJson.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
    @Test
    public void getAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    
    
  

}
