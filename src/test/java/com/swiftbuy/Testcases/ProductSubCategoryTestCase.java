package com.swiftbuy.Testcases;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.Arrays;
 
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.controller.SubCategoryController;
import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.repository.CategoryRepository;
import com.swiftbuy.admin.repository.SubCategoryRepository;
import com.swiftbuy.admin.service.SubCategoryService;

import jakarta.transaction.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductSubCategoryTestCase {
 
 
    @Autowired
    private MockMvc mockMvc;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    @Autowired
    private SubCategoryService subCategoryService;
 
    @Autowired
    private SubCategoryRepository subCategoryRepository;
 
    @Autowired
    private CategoryRepository categoryRepository;
 
   
  
// Passed /////   Only create the Rechange the value before run
    @Test
    public void testCreateSubCategory() throws Exception {
        // Create a category first
        Category category = new Category();
        category.setName("Uamrrnasa");
        category = categoryRepository.save(category);
 
        // Prepare the SubCategory JSON
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("name", "Hyahsdishow");
 
        // Include the category in the SubCategory JSON
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("category_id", category.getCategory_id());
 
        subCategoryJson.put("category", categoryJson);
 
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subcategories")
                .content(subCategoryJson.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    
//     // Passed Negative pending
  @Test
  public void testCreateSubCategory_Failure() throws Exception {
      // Create a category first
      Category category = new Category();
      category.setName("Uamrna");
      category = categoryRepository.save(category);
 
      // Prepare the SubCategory JSON
      JSONObject subCategoryJson = new JSONObject();
      subCategoryJson.put("name", "Hyahsish");
 
      // Include the category in the SubCategory JSON
      JSONObject categoryJson = new JSONObject();
      categoryJson.put("category_id", category.getCategory_id());
 
//      subCategoryJson.put("category", categoryJson);
 
      mockMvc.perform(MockMvcRequestBuilders.post("/api/subcategories")
              .content(subCategoryJson.toString())
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
    
 
  
// Passed
 
    @Test
    public void testGetSubCategoryById() throws Exception {
        // Create and save a unique Category and SubCategory
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
 
        Category category = new Category();
        category.setName("TestCategory_" + uniqueSuffix);
        category = categoryRepository.save(category);
 
        SubCategory subCategory = new SubCategory();
        subCategory.setName("TestSubCategory_" + uniqueSuffix);
        subCategory.setCategory(category);
        subCategory = subCategoryRepository.save(subCategory);
 
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subcategories/" + subCategory.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
               
 
        // Clean up test data
        subCategoryRepository.delete(subCategory);
        categoryRepository.delete(category);
    }
    
    

    
    
// Passed
    
    @Test
    public void testDeleteSubCategory() throws Exception {
        // Create and save a SubCategory first
        Category category = new Category();
        category.setName("TestBulkCategory");
        category = categoryRepository.save(category);
 
        SubCategory subCategory = new SubCategory();
        subCategory.setName("TestSubBulkCategory");
        subCategory.setCategory(category);
        subCategory = subCategoryRepository.save(subCategory);
 
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subcategories/" + subCategory.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
 
//    // Passed   
@Test
  public void testDeleteSubCategory_isBadRequest() throws Exception {
    
      Long nonExistentId = 9999L;
 
      mockMvc.perform(MockMvcRequestBuilders.delete("/api/subcategories/" + nonExistentId)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }
 
//  // Passed
    @Test
    public void testUpdateSubCategory_Success() throws Exception {
        // Create a valid subcategory object
        SubCategory subCategory = new SubCategory();
        subCategory.setName("UpdatedSubCategory");
 
        Category category = new Category();
        category.setCategory_id(1L);
        category.setName("UpdatedCategory");
        subCategory.setCategory(category);
 
        // Mock the service to return the updated subcategory
//        Mockito.when(subCategoryService.updateSubCategory(Mockito.anyLong(), Mockito.any(SubCategory.class)))
//               .thenReturn(subCategory);
 
        // Convert SubCategory to JSON
        String subCategoryJson = objectMapper.writeValueAsString(subCategory);
 
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subcategories/1")
                .content(subCategoryJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
             
    }
 
    
// Passed
//    
    @Test
  public void testUpdateSubCategory_isNotFound() throws Exception {
      // Create a valid subcategory object
      SubCategory subCategory = new SubCategory();
      subCategory.setName("UpdatedSubCategory");
 
      Category category = new Category();
      category.setCategory_id(19999L);
      category.setName("UpdatedCategory");
      subCategory.setCategory(category);
 
      // Mock the service to return the updated subcategory
//      Mockito.when(subCategoryService.updateSubCategory(Mockito.anyLong(), Mockito.any(SubCategory.class)))
//             .thenReturn(subCategory);
 
      // Convert SubCategory to JSON
      String subCategoryJson = objectMapper.writeValueAsString(subCategory);
 
      mockMvc.perform(MockMvcRequestBuilders.put("/api/subcategories/1")
              .content(subCategoryJson)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
           
  }
 
//
// Passed Negative Not need
    @Test
    public void testGetAllSubCategories() throws Exception {
        // Create and save a Category
        Category category = new Category();
        category.setCategory_id(1552L);  // Use an existing category ID for consistency
        category.setName("TestCategory3");
 
        // Create and save a couple of SubCategories
        SubCategory subCategory1 = new SubCategory();
        subCategory1.setName("TestSubCategory1");
        subCategory1.setCategory(category);
 
        SubCategory subCategory2 = new SubCategory();
        subCategory2.setName("TestSubCategory2");
        subCategory2.setCategory(category);
 
//        // Mock the service to return the subcategories
//        Mockito.when(subCategoryService.getAllSubCategories())
//               .thenReturn(Arrays.asList(subCategory1, subCategory2));
 
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subcategories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
 
 
}
