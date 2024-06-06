
 
 
 
 
 
 
 
 
 
 
 
 
package com.swiftbuy.admin.controller.CustomerServiceSubCategory;
 
 
import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;

import com.swiftbuy.admin.service.CustomerServiceCategory.CustomerServiceCategoryService;

import com.swiftbuy.admin.service.CustomerServiceSubCategory.CustomerServiceSubCategoryService;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
 
import java.util.List;

import java.util.Optional;
 
@RestController

@RequestMapping("/api/customer-service-sub-categories")

public class CustomerServiceSubCategoryController {
 
    @Autowired

    private CustomerServiceSubCategoryService subCategoryService;
 
    @Autowired

    private CustomerServiceCategoryService categoryService;
 
    @GetMapping


    public ResponseEntity<List<CustomerServiceSubCategory>> getAllSubCategories() {
        List<CustomerServiceSubCategory> subCategories = subCategoryService.getAllCustomerServiceSubCategories();
        return ResponseEntity.ok(subCategories);
    }
 
    @GetMapping("/{id}")

    public ResponseEntity<CustomerServiceSubCategory> getSubCategoryById(@PathVariable Long id) {

        CustomerServiceSubCategory subCategory = subCategoryService.getCustomerServiceSubCategoryById(id);

        if (subCategory != null) {

            return ResponseEntity.ok(subCategory);

        } else {

            return ResponseEntity.notFound().build();

        }

    }
 
    @PostMapping

    public ResponseEntity<?> createSubCategory(@RequestBody CustomerServiceSubCategory subCategory) {

        try {

            CustomerServiceSubCategory createdSubCategory = subCategoryService.createSubCategory(subCategory);

            return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);

        } catch (RuntimeException e) {

            // Handle the specific runtime exception as per your application's needs

            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);

        } 

    }
 
    @PutMapping("/{id}")

    public ResponseEntity<CustomerServiceSubCategory> updateSubCategory(@PathVariable Long id, @RequestBody CustomerServiceSubCategory updatedSubCategory) {

        try {

            CustomerServiceSubCategory updated = subCategoryService.updateCustomerServiceSubCategory(id, updatedSubCategory);

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }
 
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {

        try {

            subCategoryService.deleteCustomerServiceSubCategory(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }}
