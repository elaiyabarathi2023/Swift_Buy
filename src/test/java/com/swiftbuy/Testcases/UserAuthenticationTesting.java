package com.swiftbuy.Testcases;

import org.hamcrest.Matchers;
import org.json.JSONArray;
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

	@Test
	public void whenPost_UserWithCorrectResponse() throws Exception {
		JSONObject json = new JSONObject();
		json.put("userId", 15L);
		json.put("firstname", "anu");
		json.put("password", "Hs7x@123");
		json.put("email", "anu123@gmail.com");
		json.put("phoneNumber", "9123456783");
		String user = json.toString();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
		.andReturn();
	}

	@Test
	public void signupWith_ExistingEmail() throws Exception {

		JSONObject json = new JSONObject();
		json.put("firstname", "anu");
		json.put("password", "Hs7x@123");
		json.put("email", "simu23@gmail.com ");
		json.put("phoneNumber", "9123456783");
	
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
		json.put("firstname", null);
		json.put("password", null);
		json.put("email", null);
		json.put("phoneNumber", null);
	
		String user = json.toString();

		mockMvc.perform(
				MockMvcRequestBuilders.post("/user/signupuser").contentType(MediaType.APPLICATION_JSON).content(user))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

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
		json.put("password", "Hs7x@123");
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
	    json.put("email", "sheena23@gmail.com");
	    json.put("newPassword", "Rs7x@123");
	

	    String user = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/user/forgot-password")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(user))
	            .andExpect(MockMvcResultMatchers.status().isCreated())
	            .andReturn();
	}

	@Test
	public void forgotPassword_InvalidEmail() throws Exception {
	    JSONObject json = new JSONObject();
	    json.put("email", "sheena1223@gmail.com"); 
	    json.put("newPassword", "Rs7x@123");
	   // Assuming this email does not exist in the database

	    String user = json.toString();

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/user/forgot-password")
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .content(user))
	            .andExpect(MockMvcResultMatchers.status().isBadRequest())
	            .andReturn();
	}
//
//	@Test
//	public void Invalid_User_Login() throws Exception {
//		JSONObject json = new JSONObject();
//		json.put("password", "Stringhghh");
//		json.put("email", "stringhhh@gmail.com");
//		String user = json.toString();
//
//		mockMvc.perform(
//				MockMvcRequestBuilders.post("/user/login").content(user).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expecting OK status after login attempt
//				.andReturn();
//	}
//
//	@Test
//	public void null_EmailError() throws Exception {
//		JSONObject json = new JSONObject();
//		json.put("user_id", 345);
//		json.put("username", "anu");
//		json.put("password", "anuahhhg");
//		json.put("email", JSONObject.NULL);
//		json.put("name", JSONObject.NULL);
//
//		String user = json.toString();
//
//		mockMvc.perform(
//				MockMvcRequestBuilders.post("/user/signup").content(user).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isBadRequest()); // Expecting Internal Server Error status
//																			// after signup attempt with null email
//	}
//
//	@Test
//	public void User_Unauthorized() throws Exception {
//		JSONObject json = new JSONObject();
//
//		json.put("user_id", 345);
//		json.put("username", "anuh");
//		json.put("password", "Thilothama@12");
//		json.put("email", "anusree@gmail.com");
//		json.put("name", "Sanu");
//
//		String user = json.toString();
//
//		MvcResult mvcResult = mockMvc
//				.perform(MockMvcRequestBuilders.post("/user/signup").content(user)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isCreated())
//				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
//
//		String responseString = mvcResult.getResponse().getContentAsString();
//
//		JSONObject reponsejson = new JSONObject(responseString);
//		System.out.println("reponsejson.get(\"token\"):" + reponsejson.get("token"));
//
//		JSONObject department_list = new JSONObject();
//		department_list.put("id", 352);
//
//		JSONArray phones = new JSONArray();
//		JSONObject phone1 = new JSONObject();
//		phone1.put("id", "9");
//		phone1.put("number", "9876543210");
//		phones.put(phone1);
//
//		JSONObject phone2 = new JSONObject();
//		phone2.put("id", "2");
//		phone2.put("number", "9876543210");
//		phones.put(phone2);
//
//		JSONObject person = new JSONObject();
//
//		person.put("name", "anamika");
//		person.put("age", 30);
//		person.put("departmentlist", department_list);
//		person.put("phones", phones);
//
//		String personRequestBody = person.toString();
//		mockMvc.perform(
//				MockMvcRequestBuilders.post("/person/add").header("Authorization", "BEARER " + reponsejson.get("token"))
//						.contentType(MediaType.APPLICATION_JSON).content(personRequestBody))
//				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
//	}
//
//	@Test
//	public void verify_TokenAuthentication() throws Exception {
//		JSONObject json = new JSONObject();
//
//		json.put("user_id", 345);
//		json.put("username", "anuh");
//		json.put("password", "Anua#hhg");
//		json.put("email", "anusree@gmail.com");
//		json.put("name", "Sanu");
//
//		String user = json.toString();
//		String invalid_token = "invalid_token";
//
//		JSONObject department_list = new JSONObject();
//		department_list.put("id", 352);
//
//		JSONArray phones = new JSONArray();
//		JSONObject phone1 = new JSONObject();
//		phone1.put("id", "9");
//		phone1.put("number", "9876543210");
//		phones.put(phone1);
//
//		JSONObject phone2 = new JSONObject();
//		phone2.put("id", "2");
//		phone2.put("number", "9876543210");
//		phones.put(phone2);
//
//		JSONObject person = new JSONObject();
//
//		person.put("name", "anamika");
//		person.put("age", 30);
//		person.put("departmentlist", department_list);
//		person.put("phones", phones);
//
//		String personRequestBody = person.toString();
//		mockMvc.perform(MockMvcRequestBuilders.post("/person/add").contentType(MediaType.APPLICATION_JSON)
//				.content(personRequestBody)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
//
//	}
//
//	@Test
//	public void whenPost_ExistingUserDetails() throws Exception {
//		JSONObject json = new JSONObject();
//
//		json.put("username", "Jijithaa");
//		json.put("password", "Thilothama@12");
//		json.put("email", "jijithaaaa@gmail.com");
//		json.put("name", "Sanu");
//
//		String user = json.toString();
//
//		mockMvc.perform(
//				MockMvcRequestBuilders.post("/user/signup").content(user).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isBadRequest()); // Expecting Internal Server Error status
//																			// after signup attempt with null email
//	}
//
}
