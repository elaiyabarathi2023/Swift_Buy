package com.swiftbuy.admin.controller.CustomerService;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerService.CustomerServiceSubCategory;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceCategoryService;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceSubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer-service-sub-categories")
public class CustomerServiceSubCategoryController {
    @Autowired
    private CustomerServiceSubCategoryService subCategoryService;
    @Autowired
    private CustomerServiceCategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSubCategories() {
        Map<String, Object> response = new HashMap<>();
    
            List<CustomerServiceSubCategory> subCategories = subCategoryService.getAllCustomerServiceSubCategories();
            response.put("status", true);
            response.put("message", "All subcategories retrieved successfully");
            response.put("subCategories", subCategories);
            return ResponseEntity.ok(response);
       
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSubCategoryById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        CustomerServiceSubCategory subCategory = subCategoryService.getCustomerServiceSubCategoryById(id);
        if (subCategory != null) {
            response.put("status", true);
            response.put("message", "Subcategory retrieved successfully");
            response.put("subCategory", subCategory);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", false);
            response.put("message", "Subcategory not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSubCategory(@RequestBody CustomerServiceSubCategory subCategory) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceSubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
            response.put("status", true);
            response.put("message", "Subcategory created successfully");
          
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Failed to create subcategory: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSubCategory(@PathVariable Long id,
                                                               @RequestBody CustomerServiceSubCategory updatedSubCategory) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceSubCategory updated = subCategoryService.updateCustomerServiceSubCategory(id, updatedSubCategory);
            response.put("status", true);
            response.put("message", "Subcategory updated successfully");
           
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Failed to update subcategory: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSubCategory(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            subCategoryService.deleteCustomerServiceSubCategory(id);
            response.put("status", true);
            response.put("message", "Subcategory deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("message", "Failed to delete subcategory: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}