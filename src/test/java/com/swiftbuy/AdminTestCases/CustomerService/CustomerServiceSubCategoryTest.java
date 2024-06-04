//package com.swiftbuy.AdminTestCases.CustomerService;
//
////public class CustomerServiceSubCategoryTest {
////
////}
//
//
//
//import com.swiftbuy.admin.controller.CustomerServiceSubCategory.CustomerServiceSubCategoryController;
//import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
//import com.swiftbuy.admin.service.CustomerServiceSubCategory.CustomerServiceSubCategoryService;
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
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.json.JSONObject;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CustomerServiceSubCategoryController.class)
//public class CustomerServiceSubCategoryTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CustomerServiceSubCategoryService subCategoryService;
//
//    @MockBean
//    private CustomerServiceSubCategoryController subCategoryController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(subCategoryController).build();
//    }
//
//    @Test
//    public void testGetAllCustomerServiceSubCategories() throws Exception {
//        mockMvc.perform(get("/api/customer-service-subcategories"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testGetCustomerServiceSubCategoryById() throws Exception {
//        Long subCategoryId = 1L;
//        mockMvc.perform(get("/api/customer-service-subcategories/{id}", subCategoryId))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testCreateCustomerServiceSubCategory() throws Exception {
//        JSONObject subCategoryJson = new JSONObject();
//        subCategoryJson.put("name", "New Subcategory");
//        subCategoryJson.put("description", "New Subcategory Description");
//
//        when(subCategoryService.createCustomerServiceSubCategory(any())).thenReturn(new CustomerServiceSubCategory());
//
//        mockMvc.perform(post("/api/customer-service-subcategories")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(subCategoryJson.toString()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testUpdateCustomerServiceSubCategory() throws Exception {
//        JSONObject subCategoryJson = new JSONObject();
//        subCategoryJson.put("name", "Updated Subcategory");
//        subCategoryJson.put("description", "Updated Subcategory Description");
//
//      
//
//        mockMvc.perform(put("/api/customer-service-subcategories/{id}", 1L)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(subCategoryJson.toString()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void testDeleteCustomerServiceSubCategory() throws Exception {
//        mockMvc.perform(delete("/api/customer-service-subcategories/{id}", 1L))
//                .andExpect(status().isOk());
//    }
//}
