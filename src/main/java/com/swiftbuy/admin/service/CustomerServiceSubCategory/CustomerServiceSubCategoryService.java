
package com.swiftbuy.admin.service.CustomerServiceSubCategory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;

import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerServiceCategory.CustomerServiceCategoryRepository;
import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service

public class CustomerServiceSubCategoryService {

	@Autowired

	private CustomerServiceSubCategoryRepo subCategoryRepo;

	@Autowired

	private CustomerServiceCategoryRepository categoryRepo;


	public List<CustomerServiceSubCategory> getAllCustomerServiceSubCategories() {

		return (List<CustomerServiceSubCategory>) subCategoryRepo.findAll();

	}

	public CustomerServiceSubCategory getCustomerServiceSubCategoryById(Long id) {

		Optional<CustomerServiceSubCategory> optionalSubCategory = subCategoryRepo.findById(id);

		return optionalSubCategory.orElse(null);

	}

	public CustomerServiceSubCategory createSubCategory(CustomerServiceSubCategory subCategory) {

		// Validate the category ID

		Long categoryId = subCategory.getCategory().getCscategoryid();

		CustomerServiceCategory category = categoryRepo.findById(categoryId)

				.orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));

		subCategory.setCategory(category);

		return subCategoryRepo.save(subCategory);

	}

	public CustomerServiceSubCategory updateCustomerServiceSubCategory(Long id,
			CustomerServiceSubCategory updatedSubCategory) {

		CustomerServiceSubCategory existingSubCategory = subCategoryRepo.findById(id)

				.orElseThrow(() -> new RuntimeException("Sub-category not found with id " + id));

		existingSubCategory.setName(updatedSubCategory.getName());

		existingSubCategory.setDescription(updatedSubCategory.getDescription());

		Long categoryId;

		if (updatedSubCategory.getCategory() != null && updatedSubCategory.getCategory().getCscategoryid() != null) {

			categoryId = updatedSubCategory.getCategory().getCscategoryid();

			CustomerServiceCategory category = categoryRepo.findById(categoryId)

					.orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));

			existingSubCategory.setCategory(category);

		}

		return subCategoryRepo.save(existingSubCategory);

	}

	public void deleteCustomerServiceSubCategory(Long id) {

		CustomerServiceSubCategory subCategory = subCategoryRepo.findById(id)

				.orElseThrow(() -> new RuntimeException("Sub-category not found with id " + id));

		subCategoryRepo.delete(subCategory);

	}

}
