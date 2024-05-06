package com.swiftbuy.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.Category;
import com.swiftbuy.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            category.setCategory_id(categoryId);
            return categoryRepository.save(category);
        }
        return null;
    }

    public boolean deleteCategory(Long categoryId) {
        Category existingCategory = getCategoryById(categoryId);
        if (existingCategory != null) {
            categoryRepository.delete(existingCategory);
            return true;
        }
        return false;
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
