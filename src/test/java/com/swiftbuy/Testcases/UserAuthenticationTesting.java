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

import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.TokenService;
import com.swiftbuy.user.service.UserService;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserAuthenticationTesting {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private MockMvc mockMvc;

    // Positive Test Cases

    @Test
    public void getAllUsers_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
	public void signupUser_ShouldReturnCreated() throws Exception {
		// Login and get the token
    	 JSONObject json = new JSONObject();
         json.put("firstname", "John");
         json.put("password", "P@ssw0rd123");
         json.put("email", "john.doe@example.com");
         json.put("phoneNumber", "1234567890");
		String loginUser = json.toString();

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/user/signupuser").content(loginUser)
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
    public void forgotPassword_ShouldReturnOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "sheena23@gmail.com");
        json.put("newPassword", "Rs7x@123");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // Negative Test Cases

    @Test
    public void signupWith_ExistingEmail_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "anu");
        json.put("password", "Hs7x@123");
        json.put("email", "simu23@gmail.com");
        json.put("phoneNumber", "9123456783");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupWith_EmptyDetails_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "");
        json.put("password", "");
        json.put("email", "");
        json.put("phoneNumber", "");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void signupWithNullDetails_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", JSONObject.NULL);
        json.put("password", JSONObject.NULL);
        json.put("email", JSONObject.NULL);
        json.put("phoneNumber", JSONObject.NULL);

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void invalidEmail_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "anu");
        json.put("password", "Hs7x@123");
        json.put("email", "anu123gmail.com");
        json.put("phoneNumber", "9123456783");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void invalidPhoneNumber_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "anu");
        json.put("password", "Hs7x@123");
        json.put("email", "anu123@gmail.com");
        json.put("phoneNumber", "9123456783999999");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void forgotPassword_InvalidEmail_ShouldReturnOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", "sheena1223@gmail.com");
        json.put("newPassword", "Rs7x@123");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void forgotPassword_NullEmail_ShouldReturnOk() throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", JSONObject.NULL);
        json.put("newPassword", "Rs7x@123");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void invalidUserLogin_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("password", "invalidPassword");
        json.put("email", "invalidEmail@gmail.com");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loginuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void nullEmailError_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("user_id", 345);
        json.put("username", "anu");
        json.put("password", "anuahhhg");
        json.put("email", JSONObject.NULL);
        json.put("name", JSONObject.NULL);

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void postExistingUserDetails_ShouldReturnBadRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstname", "riya");
        json.put("phoneNumber", "9870910003");
        json.put("password", "mO8x@123");
        json.put("email", "riya567@gmail.com");

        String user = json.toString();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/signupuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
