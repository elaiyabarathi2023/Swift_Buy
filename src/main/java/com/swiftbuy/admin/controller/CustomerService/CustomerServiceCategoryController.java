package com.swiftbuy.admin.controller.CustomerService;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer-service-categories_part")
public class CustomerServiceCategoryController {
    @Autowired
    private CustomerServiceCategoryService categoryService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCategory(@RequestBody CustomerServiceCategory category) {
        Map<String, Object> response = new HashMap<>();
     
            CustomerServiceCategory createdCategory = categoryService.createCategory(category);
            response.put("status", true);
            response.put("message", "Customer service category created successfully");
          
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
      
    }

    @GetMapping("/{cscategoryid}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long cscategoryid) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceCategory category = categoryService.getCategoryById(cscategoryid);
            response.put("status", true);
            response.put("message", "Customer service category retrieved successfully");
            response.put("category", category);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            response.put("status", false);
            response.put("message", "Failed to retrieve customer service category");
            return ResponseEntity.status(ex.getStatusCode()).body(response);
        }
    }

    @PutMapping("/{cscategoryid}")
    public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long cscategoryid,
                                                            @RequestBody CustomerServiceCategory categoryDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceCategory updatedCategory = categoryService.updateCategory(cscategoryid, categoryDetails);
            response.put("status", true);
            response.put("message", "Customer service category updated successfully");
          
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            response.put("status", false);
            response.put("message", "Failed to update customer service category");
            return ResponseEntity.status(ex.getStatusCode()).body(response);
        }
    }

    @DeleteMapping("/{cscategoryid}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable("cscategoryid") Long cscategoryid) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = categoryService.deleteCategory(cscategoryid);
           
                response.put("status", true);
                response.put("message", "Customer service category deleted successfully");
                return ResponseEntity.ok(response);
          
        } catch (ResponseStatusException e) {
            response.put("status", false);
            response.put("message", "Failed to delete customer service category");
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        Map<String, Object> response = new HashMap<>();
      
            Iterable<CustomerServiceCategory> categories = categoryService.getAllCategories();
            response.put("status", true);
            response.put("message", "All customer service categories retrieved successfully");
            response.put("categories", categories);
            return ResponseEntity.ok(response);
        
    }
}