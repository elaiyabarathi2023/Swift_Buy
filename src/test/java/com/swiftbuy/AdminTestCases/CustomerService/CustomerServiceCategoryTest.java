package com.swiftbuy.AdminTestCases.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
import com.swiftbuy.admin.repository.CustomerService.CustomerServiceCategoryRepository;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceCategoryService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerServiceCategoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerServiceCategoryService service;

    @Autowired
    private CustomerServiceCategoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Long> testCategoryIds = new ArrayList<>();

    private Long testCategoryId;

    @Test
    public void testCreateCategory_Success() throws Exception {
        CustomerServiceCategory category = new CustomerServiceCategory();
        category.setName("Poweris");
        category.setDescription("This category covers all aspects related to placing, managing, and tracking orders on the website");

        MvcResult result = mockMvc
                .perform(post("/api/customer-service-categories_part")
                        .content(objectMapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        CustomerServiceCategory responseCategory = objectMapper.readValue(responseString, CustomerServiceCategory.class);

        assertThat(responseCategory.getCscategoryid()).isNotNull();
        assertThat(responseCategory.getName()).isEqualTo("Poweris");
        assertThat(responseCategory.getDescription()).isEqualTo("This category covers all aspects related to placing, managing, and tracking orders on the website");

        testCategoryId = responseCategory.getCscategoryid();
    }

    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        Long nonExistentCategoryId = 999L;

        MvcResult result = mockMvc
                .perform(get("/api/customer-service-categories_part/" + nonExistentCategoryId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEmpty();
    }

    @Test
    public void testUpdateCategory_Success() throws Exception {
        CustomerServiceCategory category = new CustomerServiceCategory();
        category.setName("Electronics");
        category.setDescription("This category covers all aspects related to placing, managing, and tracking orders on the website");
        CustomerServiceCategory savedCategory = repository.save(category);

        savedCategory.setName("Updated Electronics");
        savedCategory.setDescription("Updated description of the electronics category");

        MvcResult result = mockMvc
                .perform(put("/api/customer-service-categories_part/" + savedCategory.getCscategoryid())
                        .content(objectMapper.writeValueAsString(savedCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        CustomerServiceCategory responseCategory = objectMapper.readValue(responseString, CustomerServiceCategory.class);

        assertThat(responseCategory.getCscategoryid()).isEqualTo(savedCategory.getCscategoryid());
        assertThat(responseCategory.getName()).isEqualTo("Updated Electronics");
        assertThat(responseCategory.getDescription()).isEqualTo("Updated description of the electronics category");

        repository.deleteById(savedCategory.getCscategoryid());
    }

    @Test
    public void testDeleteCategory_Success() throws Exception {
        CustomerServiceCategory category = new CustomerServiceCategory();
        category.setName("Electronics to Delete");
        category.setDescription("This category covers all aspects related to placing, managing, and tracking orders on the website");
        CustomerServiceCategory savedCategory = repository.save(category);
        Long categoryId = savedCategory.getCscategoryid();

        mockMvc.perform(delete("/api/customer-service-categories_part/" + categoryId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

       
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {
        CustomerServiceCategory category1 = new CustomerServiceCategory();
        category1.setName("Electronics");
        category1.setDescription("This category covers all aspects related to electronics.");
        repository.save(category1);
        testCategoryIds.add(category1.getCscategoryid());

        CustomerServiceCategory category2 = new CustomerServiceCategory();
        category2.setName("Books");
        category2.setDescription("This category covers all aspects related to books.");
        repository.save(category2);
        testCategoryIds.add(category2.getCscategoryid());

        MvcResult result = mockMvc
                .perform(get("/api/customer-service-categories_part"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        List<CustomerServiceCategory> responseCategories = objectMapper.readValue(responseString, new TypeReference<List<CustomerServiceCategory>>() {});

        assertThat(responseCategories).isNotEmpty();
        assertThat(responseCategories.size()).isGreaterThanOrEqualTo(2);

        testCategoryIds.add(category1.getCscategoryid());
        testCategoryIds.add(category2.getCscategoryid());
    }

    @Test
    public void testUpdateCategory_NullArguments() throws Exception {
        Long cscategoryid = null;

        mockMvc.perform(put("/api/customer-service-categories_part/{cscategoryid}", cscategoryid)
                        .content(objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testUpdateCategory_NotFound() throws Exception {
        Long nonExistentCategoryId = 999L;
        CustomerServiceCategory updatedCategory = new CustomerServiceCategory();
        updatedCategory.setName("Updated Category Name");
        updatedCategory.setDescription("This is an updated category description.");

        MvcResult result = mockMvc
                .perform(put("/api/customer-service-categories_part/{cscategoryid}", nonExistentCategoryId)
                        .content(objectMapper.writeValueAsString(updatedCategory))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEmpty();
    }

    @Test
    public void testDeleteCategory_NotFound() throws Exception {
        Long nonExistentCategoryId = 999L;

        MvcResult result = mockMvc
                .perform(delete("/api/customer-service-categories_part/" + nonExistentCategoryId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertThat(responseBody).isEmpty();
    }

    @Test
    public void testGetCategoryById() throws Exception {
        CustomerServiceCategory category = new CustomerServiceCategory();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        CustomerServiceCategory savedCategory = repository.save(category);
        Long categoryId = savedCategory.getCscategoryid();

        mockMvc.perform(get("/api/customer-service-categories_part/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        repository.deleteById(categoryId);
    }

    @AfterEach
    public void tearDown() {
        if (testCategoryId != null && repository.existsById(testCategoryId)) {
            repository.deleteById(testCategoryId);
            testCategoryId = null;
        }
        for (Long id : testCategoryIds) {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            }
        }
        testCategoryIds.clear();
    }
}
