package com.swiftbuy.admin.service;
 
import java.util.Optional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.repository.CategoryRepository;
 
@Service
public class CategoryService {
 
    @Autowired
    private CategoryRepository categoryRepository;
 
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }
 
    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        return categoryOptional.get();
    }
 
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            category.setCategory_id(categoryId);
        } 
        return categoryRepository.save(category);
    }
 
    public boolean deleteCategory(Long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            categoryRepository.delete(existingCategory);
        } 
        return true;
    }
 
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}