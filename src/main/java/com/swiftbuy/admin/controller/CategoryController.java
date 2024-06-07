package com.swiftbuy.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.service.CategoryService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        
            Category createdCategory = categoryService.createCategory(category);
            response.put("status", true);
            response.put("message", "Category created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }

    // Read Category
    @GetMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long categoryId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Category category = categoryService.getCategoryById(categoryId);
            response.put("status", true);
            response.put("message", "Category retrieved successfully");
            response.put("category", category);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", false);
            response.put("error", "Category not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        Map<String, Object> response = new HashMap<>();
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            response.put("status", true);
            response.put("message", "Category updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", false);
            response.put("error", "Category not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Delete Category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long categoryId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = categoryService.deleteCategory(categoryId);
            
                response.put("status", true);
                response.put("message", "Category deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
            
        } catch (Exception e) {
            response.put("status", false);
            response.put("error", "Internal Server Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Read All Categories
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> response = new HashMap<>();
        
            Iterable<Category> categories = categoryService.getAllCategories();
            response.put("status", true);
            response.put("message", "All categories retrieved successfully");
            response.put("categories", categories);
            return new ResponseEntity<>(response, HttpStatus.OK);
        
    }
}