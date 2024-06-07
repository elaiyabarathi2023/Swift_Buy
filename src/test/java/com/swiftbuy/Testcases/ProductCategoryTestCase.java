package com.swiftbuy.Testcases;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Optional;
 
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
 
import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.repository.CategoryRepository;

import jakarta.transaction.Transactional;
 
 
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductCategoryTestCase {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private CategoryRepository categoryRepository;
 
    @BeforeEach
    public void setUp() {
        Category testCategory = new Category();
        testCategory.setCategory_id(1L);
        testCategory.setName("Test Category");
 
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
    }
 
    @Test
    public void testGetCategoryById_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testCreateCategory_Success() throws Exception {
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("name", "New Category");
 
        mockMvc.perform(MockMvcRequestBuilders.post("/api/categories/add")
                .content(categoryJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
 
    @Test
    public void testUpdateCategory_Success() throws Exception {
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("name", "Updated Category");
 
        mockMvc.perform(MockMvcRequestBuilders.put("/api/categories/1")
                .content(categoryJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 
    @Test
    public void testDeleteCategory_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
 
    @Test
    public void testGetAllCategories_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testUpdateCategory_NotFound() throws Exception {
        // Mock behavior for findById
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Prepare the request body
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("name", "Updated Category");
 
        // Perform the PUT request and expect NOT_FOUND status
        mockMvc.perform(put("/api/categories/1")
                        .content(categoryJson.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
 
    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        // Mock behavior for findById
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Perform the GET request and expect NOT_FOUND status
        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isNotFound());
    }
 
    @Test
    public void testDeleteCategory_NotFound() throws Exception {
        // Mock behavior for findById
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Perform the DELETE request and expect NOT_FOUND status
        mockMvc.perform(delete("/api/categories/9999"))
                .andExpect(status().isNotFound());
    }
 
   
}
