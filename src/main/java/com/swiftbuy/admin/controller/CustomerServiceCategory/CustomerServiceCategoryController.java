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

    private final CustomerServiceCategoryService categoryService;

    @Autowired
    public CustomerServiceCategoryController(CustomerServiceCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CustomerServiceCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceCategory> getCategoryById(@PathVariable Long id) {
        CustomerServiceCategory category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CustomerServiceCategory> createCategory(@RequestBody CustomerServiceCategory category) {
        CustomerServiceCategory createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceCategory> updateCategory(@PathVariable Long id, @RequestBody CustomerServiceCategory updatedCategory) {
        CustomerServiceCategory updatedCat = categoryService.updateCategory(id, updatedCategory);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}