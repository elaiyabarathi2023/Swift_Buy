//package com.swiftbuy.AdminTestCases.CustomerService;
//
////public class CustomerServiceCategory {
////
////}
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.swiftbuy.admin.controller.CustomerServiceCategory.CustomerServiceCategoryController;
//import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
//import com.swiftbuy.admin.repository.CustomerServiceCategory.CustomerServiceCategoryRepo;
//import com.swiftbuy.admin.service.CustomerServiceCategory.CustomerServiceCategoryService;
//import com.swiftbuy.user.controller.AccountManangement.AddressDetailsController;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(CustomerServiceCategoryController.class)
//public class CustomerServiceCategoryTest {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private CustomerServiceCategoryRepo categoryRepository;
//
//    @MockBean
//    private CustomerServiceCategoryService categoryService;
//
//    @MockBean
//    private CustomerServiceCategoryController categoryController;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void testGetAllCategories() throws Exception {
//        
//
//        // Perform the GET request with the JSONArray as the expected response body
//        mockMvc.perform(get("/api/categories"))
//            .andExpect(status().isOk());
////            .andExpect(content().json(categoriesJson.toString()));
//    }
//
//    @Test
//    public void testGetCategoryById() throws Exception {
//        Long categoryId = 1L;
////        CustomerServiceCategoryTest category = new CustomerServiceCategoryTest();
////
////      
//
//        mockMvc.perform(get("/api/categories/{id}", categoryId))
//                .andExpect(status().isOk());
//                
//    }
//
//    @Test
//    public void testCreateCategory() throws Exception {
//        // Create a JSONObject for the category
//        JSONObject categoryJson = new JSONObject();
//        categoryJson.put("name", "New Category");
//        categoryJson.put("description", "New Description");
//
//        String requestBody = categoryJson.toString();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void testUpdateCategory() throws Exception {
//        // Create a JSONObject for the category
//        JSONObject categoryJson = new JSONObject();
//        categoryJson.put("name", "Updated Category");
//        categoryJson.put("description", "Updated Description");
//
//        String requestBody = categoryJson.toString();
//
//        Long categoryId = 3L;
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/{id}", categoryId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(requestBody))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//
//    @Test
//    public void testDeleteCategory() throws Exception {
//        Long categoryId = 1L;
////        CustomerServiceCategoryTest category = new CustomerServiceCategoryTest();
//
//        
//
//        mockMvc.perform(delete("/api/categories/{id}", categoryId))
//                .andExpect(status().isOk());
//    }
//}
