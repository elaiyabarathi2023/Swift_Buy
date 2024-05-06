package com.swiftbuy.admin.service;

import com.swiftbuy.admin.model.Category;
import com.swiftbuy.admin.model.SubCategory;
import com.swiftbuy.repository.CategoryRepository;
import com.swiftbuy.subcrepository.SubCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        return subCategoryRepository.findById(id).orElseThrow();
    }

    public SubCategory createSubCategory(SubCategory subCategory) {
    	Long subid = subCategory.getCategory().getCategory_id();
    	
    	Category category =  categoryRepository.findById(subid)
    	        .orElseThrow(() -> new RuntimeException("Category not found with id " + subid));
    	subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }

    public SubCategory updateSubCategory(Long id, SubCategory subCategoryDetails) {
        SubCategory subCategory = getSubCategoryById(id);
        subCategory.setName(subCategoryDetails.getName());
        subCategory.setCategory(subCategoryDetails.getCategory());
        return subCategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(Long id) {
        SubCategory subCategory = getSubCategoryById(id);
        subCategoryRepository.delete(subCategory);
    }
}