package com.swiftbuy.admin.controller.CustomerServiceSubCategory;

//public class CustomerServiceSubCategoryController {
//
//}


import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.service.CustomerServiceSubCategory.CustomerServiceSubCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-service-subcategories")
public class CustomerServiceSubCategoryController {

    @Autowired
    private CustomerServiceSubCategoryService customerServiceSubCategoryService;

    @GetMapping
    public ResponseEntity<List<CustomerServiceSubCategory>> getAllCustomerServiceSubCategories() {
        List<CustomerServiceSubCategory> customerServiceSubCategories = customerServiceSubCategoryService.getAllCustomerServiceSubCategories();
        return ResponseEntity.ok(customerServiceSubCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceSubCategory> getCustomerServiceSubCategoryById(@PathVariable Long id) {
        CustomerServiceSubCategory customerServiceSubCategory = customerServiceSubCategoryService.getCustomerServiceSubCategoryById(id);
        return ResponseEntity.ok(customerServiceSubCategory);
    }

    @PostMapping
    public ResponseEntity<CustomerServiceSubCategory> createCustomerServiceSubCategory(@RequestBody CustomerServiceSubCategory customerServiceSubCategory) {
        CustomerServiceSubCategory createdCustomerServiceSubCategory = customerServiceSubCategoryService.createCustomerServiceSubCategory(customerServiceSubCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerServiceSubCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceSubCategory> updateCustomerServiceSubCategory(@PathVariable Long id, @RequestBody CustomerServiceSubCategory customerServiceSubCategory) {
        CustomerServiceSubCategory updatedCustomerServiceSubCategory = customerServiceSubCategoryService.updateCustomerServiceSubCategory(id, customerServiceSubCategory);
        return ResponseEntity.ok(updatedCustomerServiceSubCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerServiceSubCategory(@PathVariable Long id) {
        customerServiceSubCategoryService.deleteCustomerServiceSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}
