package com.swiftbuy.Testcases;

import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.product.repository.ProductRepository;
import com.swiftbuy.admin.product.service.ProductService;
import com.swiftbuy.admin.repository.CategoryRepository;
import com.swiftbuy.admin.repository.SubCategoryRepository;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTestCase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() throws Exception {
        Category category = new Category();
        category.setCategory_id(1L);
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1L);

        ProductDetails product = new ProductDetails();
        product.setProductName("Test Product");
        product.setCategory(category);
        product.setSubcategory(subCategory);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(subCategory));
        when(productRepository.save(ArgumentMatchers.any(ProductDetails.class))).thenReturn(product);

        JSONObject productJson = new JSONObject();
        productJson.put("productName", "Test Product");
        JSONObject categoryJson = new JSONObject();
        categoryJson.put("category_id", 1L);
        productJson.put("category", categoryJson);
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 1L);
        productJson.put("subcategory", subCategoryJson);

        mockMvc.perform(post("/admin/productpart/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }
    @Test
    public void testGetProduct() throws Exception {
        Long productId = 1L;
        ProductDetails product = new ProductDetails();
        product.setProductId(productId);
        product.setProductName("Test Product");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/admin/productpart/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Test Product"));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDetails product1 = new ProductDetails();
        product1.setProductId(1L);
        ProductDetails product2 = new ProductDetails();
        product2.setProductId(2L);

        when(productRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(product1, product2)));

        mockMvc.perform(get("/admin/productpart/allproducts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDetails existingProduct = new ProductDetails();
        existingProduct.setProductId(productId);
        existingProduct.setProductName("Old Product Name");

        ProductDetails updatedProduct = new ProductDetails();
        updatedProduct.setProductName("Updated Product Name");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(ArgumentMatchers.any(ProductDetails.class))).thenReturn(updatedProduct);

        JSONObject productJson = new JSONObject();
        productJson.put("productName", "Updated Product Name");

        mockMvc.perform(put("/admin/productpart/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated Product Name"));
    }

    @Test
    public void testGetActiveProducts() throws Exception {
        ProductDetails product1 = new ProductDetails();
        product1.setProductId(1L);
        product1.setProductStatus(ProductDetails.ProductStatus.ACTIVE);

        ProductDetails product2 = new ProductDetails();
        product2.setProductId(2L);
        product2.setProductStatus(ProductDetails.ProductStatus.ACTIVE);

        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findByProductStatus(ProductDetails.ProductStatus.ACTIVE, pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(product1, product2)));

        mockMvc.perform(get("/admin/productpart/active")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void testGetInactiveProducts() throws Exception {
        ProductDetails product1 = new ProductDetails();
        product1.setProductId(1L);
        product1.setProductStatus(ProductDetails.ProductStatus.INACTIVE);

        ProductDetails product2 = new ProductDetails();
        product2.setProductId(2L);
        product2.setProductStatus(ProductDetails.ProductStatus.INACTIVE);

        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findByProductStatus(ProductDetails.ProductStatus.INACTIVE, pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(product1, product2)));

        mockMvc.perform(get("/admin/productpart/inactive")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isNotEmpty());
    }
    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;
        ProductDetails product = new ProductDetails();
        product.setProductId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(delete("/admin/productpart/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testSearchProducts() throws Exception {
        // Define test data
        ProductDetails product1 = new ProductDetails();
        product1.setProductId(552L);
        product1.setProductName("Apple");
        product1.setProductStatus(ProductDetails.ProductStatus.ACTIVE);
        product1.setProductDescription("Ferocious Black Design");
        product1.setProductPrice(300000.0);
        product1.setProductQuantity(1);
     
        ProductDetails product2 = new ProductDetails();
        product2.setProductId(553L);
        product2.setProductName("Orange");
        product2.setProductStatus(ProductDetails.ProductStatus.ACTIVE);
        product2.setProductDescription("Ferocious Black Design");
        product2.setProductPrice(300000.0);
        product2.setProductQuantity(1);
     
        Pageable pageable = PageRequest.of(0, 10);
     
        // Mock repository method
        when(productRepository.findByProductNameContainingIgnoreCase("Apple", pageable))
                .thenReturn(new PageImpl<>(Arrays.asList(product1, product2)));
     
        // Perform request and assert results
        mockMvc.perform(get("/admin/productpart/search")
                .param("keyword", "Apple")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
