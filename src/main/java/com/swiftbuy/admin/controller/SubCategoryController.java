package com.swiftbuy.admin.controller;

import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSubCategories() {
        Map<String, Object> response = new HashMap<>();
      
            Iterable<SubCategory> subCategories = subCategoryService.getAllSubCategories();
            response.put("status", true);
            response.put("message", "All subcategories retrieved successfully");
            response.put("subcategories", subCategories);
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSubCategoryById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
      
            SubCategory subCategory = subCategoryService.getSubCategoryById(id);
            response.put("status", true);
            response.put("message", "Subcategory retrieved successfully");
            response.put("subcategory", subCategory);
            return ResponseEntity.ok(response);
       
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSubCategory(@RequestBody SubCategory subCategory) {
        Map<String, Object> response = new HashMap<>();
        if (subCategory.getCategory() == null || subCategory.getCategory().getCategory_id() == null) {
            response.put("status", false);
            response.put("error", "Required parameter 'category_id' is not present in the request body.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            SubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);
            response.put("status", true);
            response.put("message", "Subcategory created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            response.put("status", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategory) {
        Map<String, Object> response = new HashMap<>();
        try {
            SubCategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategory);
            response.put("status", true);
            response.put("message", "Subcategory updated successfully");
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            response.put("status", false);
            response.put("error", "Category not found");
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSubCategory(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            subCategoryService.deleteSubCategory(id);
            response.put("status", true);
            response.put("message", "Subcategory deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", false);
            response.put("error", "Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}