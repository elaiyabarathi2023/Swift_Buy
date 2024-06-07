package com.swiftbuy.admin.controller.CustomerService;
 
import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
 
import java.util.List;
 
@RestController
@RequestMapping("/api/customer-service-categories_part")
public class CustomerServiceCategoryController {

    @Autowired
    private CustomerServiceCategoryService categoryService;

    @PostMapping
    public ResponseEntity<CustomerServiceCategory> createCategory(@RequestBody CustomerServiceCategory category) {
        CustomerServiceCategory createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/{cscategoryid}")
    public ResponseEntity<CustomerServiceCategory> getCategoryById(@PathVariable Long cscategoryid) {
        try {
            CustomerServiceCategory category = categoryService.getCategoryById(cscategoryid);
            return ResponseEntity.ok(category);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(null);
        }
    }

    @PutMapping("/{cscategoryid}")
    public ResponseEntity<CustomerServiceCategory> updateCategory(@PathVariable Long cscategoryid,
                                                                  @RequestBody CustomerServiceCategory categoryDetails) {
        try {
            CustomerServiceCategory updatedCategory = categoryService.updateCategory(cscategoryid, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(null);
        }
    }


    @DeleteMapping("/{cscategoryid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cscategoryid") Long cscategoryid) {
        try {
            boolean deleted = categoryService.deleteCategory(cscategoryid);
            return  ResponseEntity.noContent().build() ;
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
    @GetMapping
    public ResponseEntity<Iterable<CustomerServiceCategory>> getAllCategories() {
        Iterable<CustomerServiceCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}