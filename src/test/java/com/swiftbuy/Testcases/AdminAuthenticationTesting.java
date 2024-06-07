package com.swiftbuy.Testcases;
 
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
 
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
 
import com.swiftbuy.admin.model.AdminDetails;
import com.swiftbuy.admin.repository.AdminRepository;
import com.swiftbuy.admin.service.AdminService;
 
import jakarta.transaction.Transactional;
 
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AdminAuthenticationTesting {
 
	@MockBean
	private AdminRepository adminRepository;
 
	@Autowired
	AdminService adminService;
 
	@Autowired
	private MockMvc mockMvc;
 
	// Positive test cases for signupUser
	@Test
	public void signupUser_WithValidDetails() throws Exception {
		JSONObject json = new JSONObject();
		json.put("firstname", "Aneeta");
		json.put("lastname", "Antony");
		json.put("username", "Aneet");
		json.put("password", "AN02m@n123");
		String admin = json.toString();
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/signupuser").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Admin signed up successfully."));
	}
 
	// Negative test cases for signupUser
	@Test
 
	public void signupUser_WithExistingUsername() throws Exception {
		JSONObject json = new JSONObject();
		json.put("userId", 452);
		json.put("firstname", "Akshay");
		json.put("lastname", "Kumar");
		json.put("username", "akshay@2000");
		json.put("password", "Am07@n234");
		String admin = json.toString();
 
		Mockito.when(adminRepository.existsByUsername("akshay@2000")).thenReturn(true);
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/signupuser").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(jsonPath("$.message").value("username already exists."));
	}
 
	@Test
	public void signupUser_WithEmptyUsername() throws Exception {
		JSONObject json = new JSONObject();
		json.put("firstname", "Tiger");
		json.put("lastname", "Shrof");
		json.put("username", "");
		json.put("password", "Tm07@n234");
		String admin = json.toString();
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/signupuser").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Username cannot be empty."));
	}
 
	@Test
	public void signupUser_WithEmptyPassword() throws Exception {
		JSONObject json = new JSONObject();
		json.put("firstname", "Theertha");
		json.put("lastname", "Sukumaran");
		json.put("username", "theerth@2001");
		json.put("password", "");
		String admin = json.toString();
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/signupuser").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Password cannot be empty."));
	}
 
	// Positive test cases for loginUser
	@Test
	public void loginUser_WithValidCredentials() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "tiger@2000");
		json.put("password", "Tm07@n234");
		String admin = json.toString();
 
		AdminDetails adminDetails = new AdminDetails();
		adminDetails.setUsername("tiger@2000");
		adminDetails.setPassword(adminService.testHashPassword("Tm07@n234"));
		Mockito.when(adminRepository.findByUsername("tiger@2000")).thenReturn(adminDetails);
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Login successful")).andExpect(jsonPath("$.status").value(true));
	}
 
	// Negative test cases for loginUser
	@Test
	public void loginUser_WithInvalidPassword() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "tiger@2000");
		json.put("password", "Tz08@n234");
		String admin = json.toString();
 
		AdminDetails adminDetails = new AdminDetails();
		adminDetails.setUsername("tiger@2000");
		adminDetails.setPassword(adminService.testHashPassword("Tm07@n234"));
		Mockito.when(adminRepository.findByUsername("tiger@2000")).thenReturn(adminDetails);
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Incorrect password."));
	}
 
	@Test
	public void loginUser_WithNonExistingUsername() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "jithin@2001");
		json.put("password", "JN20@N123");
		String admin = json.toString();
 
		Mockito.when(adminRepository.findByUsername("jithin@2001")).thenReturn(null);
 
		mockMvc.perform(
				MockMvcRequestBuilders.post("/admin/login").contentType(MediaType.APPLICATION_JSON).content(admin))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Details Provided"));
	}
 
	// Positive test cases for forgotPassword
	@Test
	public void forgotPassword_ResponseOk() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "adminreset@gmail.com");
		json.put("newPassword", "Admin@123");
 
		String admin = json.toString();
 
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/forgot-password").contentType(MediaType.APPLICATION_JSON)
				.content(admin)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Password recovery not implemented."));
	}
 
	// Negative test cases for forgotPassword
	@Test
	public void forgotPassword_InvalidEmail() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", "invalidadmin@gmail.com");
		json.put("newPassword", "Admin@123");
 
		String admin = json.toString();
 
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/forgot-password").contentType(MediaType.APPLICATION_JSON)
				.content(admin)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Password recovery not implemented."));
	}
 
	@Test
	public void forgotPassword_NullEmail() throws Exception {
		JSONObject json = new JSONObject();
		json.put("email", null);
		json.put("newPassword", "Admin@123");
 
		String admin = json.toString();
 
		mockMvc.perform(MockMvcRequestBuilders.post("/admin/forgot-password").contentType(MediaType.APPLICATION_JSON)
				.content(admin)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.message").value("Password recovery not implemented."));
	}
	// Test cases for empty username and password in signupUser

	@Test
	public void signupUser_WithNullUsername() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("firstname", "Test");
	    json.put("lastname", "User");
	    json.put("username", JSONObject.NULL);
	    json.put("password", "Test@123");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/signupuser")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Username cannot be empty."));
	}

	@Test
	public void signupUser_WithWhitespaceUsername() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("firstname", "Test");
	    json.put("lastname", "User");
	    json.put("username", "   ");
	    json.put("password", "Test@123");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/signupuser")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Username cannot be empty."));
	}

	@Test
	public void signupUser_WithNullPassword() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("firstname", "Test");
	    json.put("lastname", "User");
	    json.put("username", "testuser");
	    json.put("password", JSONObject.NULL);
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/signupuser")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Password cannot be empty."));
	}

	@Test
	public void signupUser_WithWhitespacePassword() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("firstname", "Test");
	    json.put("lastname", "User");
	    json.put("username", "testuser");
	    json.put("password", "   ");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/signupuser")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andExpect(jsonPath("$.message").value("Password cannot be empty."));
	}

	// Test cases for empty username and password in loginUser

	@Test
	public void loginUser_WithNullUsername() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("username", JSONObject.NULL);
	    json.put("password", "Test@123");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/login")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
	            .andExpect(jsonPath("$.error").value("Invalid Details Provided"));
	}

	@Test
	public void loginUser_WithWhitespaceUsername() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("username", "   ");
	    json.put("password", "Test@123");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/login")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
	            .andExpect(jsonPath("$.error").value("Invalid Details Provided"));
	}

	@Test
	public void loginUser_WithNullPassword() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("username", "testuser");
	    json.put("password", JSONObject.NULL);
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/login")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
	            .andExpect(jsonPath("$.error").value("Invalid Details Provided"));
	}

	@Test
	public void loginUser_WithWhitespacePassword() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("username", "testuser");
	    json.put("password", "   ");
	    String admin = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/admin/login")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(admin))
	            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
	            .andExpect(jsonPath("$.error").value("Invalid Details Provided"));
	}

 
}