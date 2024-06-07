package com.swiftbuy.admin.service;
 
import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.admin.repository.CategoryRepository;
import com.swiftbuy.admin.repository.SubCategoryRepository;

import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
 
@Service
public class SubCategoryService {
 
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;
 
    public Iterable<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }
 
 
    
    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "SubCategory not found with ID " + id));
    }
 
    public SubCategory createSubCategory(SubCategory subCategory) {
        Long categoryId = subCategory.getCategory().getCategory_id();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
 
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }
 
 
    public SubCategory updateSubCategory(Long id, SubCategory subCategoryDetails) {
        SubCategory subCategory = getSubCategoryById(id);
 
        if (subCategory != null) {
            subCategory.setName(subCategoryDetails.getName());
 
            // Fetch the full Category object from the database
            Category category = categoryRepository.findById(subCategoryDetails.getCategory().getCategory_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
 
            subCategory.setCategory(category);
            return subCategoryRepository.save(subCategory);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SubCategory not found");
        }
    }
 
    public void deleteSubCategory(Long id) {
        SubCategory subCategory = getSubCategoryById(id);
        subCategoryRepository.delete(subCategory);
    }
}