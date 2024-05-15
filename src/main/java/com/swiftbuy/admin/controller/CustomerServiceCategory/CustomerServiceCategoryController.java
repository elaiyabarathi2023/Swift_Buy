package com.swiftbuy.admin.controller.CustomerServiceCategory;




import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.service.CustomerServiceCategory.CustomerServiceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CustomerServiceCategoryController {

    @Autowired
    private CustomerServiceCategoryService customerServiceCategoryService;

    @GetMapping
    public ResponseEntity<List<CustomerServiceCategory>> getAllCategories() {
        List<CustomerServiceCategory> categories = customerServiceCategoryService.getAllCustomerServiceCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceCategory> getCategoryById(@PathVariable Long id) {
        CustomerServiceCategory category = customerServiceCategoryService.getCustomerServiceCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerServiceCategory> createCategory(@RequestBody CustomerServiceCategory category) {
        CustomerServiceCategory createdCategory = customerServiceCategoryService.createCustomerServiceCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceCategory> updateCategory(@PathVariable Long id, @RequestBody CustomerServiceCategory updatedCategory) {
        CustomerServiceCategory updatedCategoryEntity = customerServiceCategoryService.updateCustomerServiceCategory(id, updatedCategory);
        return updatedCategoryEntity != null ? ResponseEntity.ok(updatedCategoryEntity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        customerServiceCategoryService.deleteCustomerServiceCategory(id);
        return ResponseEntity.noContent().build();
    }
}