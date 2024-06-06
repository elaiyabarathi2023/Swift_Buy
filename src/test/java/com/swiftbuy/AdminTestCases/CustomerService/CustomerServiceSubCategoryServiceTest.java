package com.swiftbuy.AdminTestCases.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;
import com.swiftbuy.admin.service.CustomerServiceSubCategory.CustomerServiceSubCategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceSubCategoryServiceTest {

    @MockBean
    private CustomerServiceSubCategoryRepo subCategoryRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerServiceSubCategoryService subCategoryService;

    private CustomerServiceSubCategory testSubCategory;

    @BeforeEach
    public void setUpMocks() {
        CustomerServiceSubCategory subCategory = new CustomerServiceSubCategory();
        subCategory.setId(2L);
        subCategory.setName("Sub-Category 2");
        subCategory.setDescription("Description 2");

        CustomerServiceCategory category = new CustomerServiceCategory();
        category.setCscategoryid(1L);
        subCategory.setCategory(category);

        when(subCategoryRepo.findById(2L)).thenReturn(Optional.of(subCategory));
    }
    @Test
    public void testGetAllCustomerServiceSubCategories() throws Exception {
        // Mock data
        CustomerServiceSubCategory subCategory1 = new CustomerServiceSubCategory();
        subCategory1.setId(1L);
        subCategory1.setName("SubCategory 1");
        subCategory1.setDescription("Description 1");

        CustomerServiceSubCategory subCategory2 = new CustomerServiceSubCategory();
        subCategory2.setId(2L);
        subCategory2.setName("SubCategory 2");
        subCategory2.setDescription("Description 2");

        List<CustomerServiceSubCategory> subCategoryList = Arrays.asList(subCategory1, subCategory2);

        // Mock repository behavior
        when(subCategoryRepo.findAll()).thenReturn(subCategoryList);

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer-service-sub-categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateSubCategory_Success() throws Exception {
        // Create the sub-category JSON object with all required details
        JSONObject subCategoryJson = new JSONObject();
        JSONObject categoryJson = new JSONObject();
        subCategoryJson.put("name", "New Sub-Category");
        subCategoryJson.put("description", "Description of the new sub-category");
        categoryJson.put("cscategoryid", 1L);
        subCategoryJson.put("category", categoryJson);

        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer-service-sub-categories")
                        .content(subCategoryJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateSubCategory_isBadRequest() throws Exception {
        // Create the sub-category JSON object with all required details
        JSONObject subCategoryJson = new JSONObject();
        JSONObject categoryJson = new JSONObject();
        subCategoryJson.put("name", "New Sub-Category");
        subCategoryJson.put("description", "Description of the new sub-category");
        categoryJson.put("cscategoryid", 900L); // Invalid category ID
        subCategoryJson.put("category", categoryJson);

        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/customer-service-sub-categories")
                        .content(subCategoryJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    
    @Test
    public void testGetCustomerServiceSubCategoryById() throws Exception {
        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer-service-sub-categories/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerServiceSubCategoryById_isNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer-service-sub-categories/987")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomerServiceSubCategory_Success() throws Exception {
        // Create the updated sub-category JSON object with valid category
        JSONObject updatedSubCategoryJson = new JSONObject();
        updatedSubCategoryJson.put("name", "Updated Sub-Category");
        updatedSubCategoryJson.put("description", "Updated description");
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("cscategoryid", 1L); // Valid category ID
        updatedSubCategoryJson.put("category", categoryJson);

        // Perform the PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customer-service-sub-categories/2")
                        .content(updatedSubCategoryJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomerServiceSubCategory_isNotFound() throws Exception {
        // Create the updated sub-category JSON object with valid category
        JSONObject updatedSubCategoryJson = new JSONObject();
        updatedSubCategoryJson.put("name", "Updated Sub-Category");
        updatedSubCategoryJson.put("description", "Updated description");
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("cscategoryid", 1L); // Valid category ID
        updatedSubCategoryJson.put("category", categoryJson);

        // Perform the PUT request with a non-existent sub-category ID and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/customer-service-sub-categories/987")
                        .content(updatedSubCategoryJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomerServiceSubCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer-service-sub-categories/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Expect 204 (No Content)
    }

    @Test
    public void testDeleteCustomerServiceSubCategory_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customer-service-sub-categories/987")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
