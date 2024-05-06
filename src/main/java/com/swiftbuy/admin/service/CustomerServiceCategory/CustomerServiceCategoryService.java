package com.swiftbuy.admin.service.CustomerServiceCategory;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.repository.CustomerServiceCategory.CustomerServiceCategoryRepo;

import java.util.List;

@Service
public class CustomerServiceCategoryService {
    private final CustomerServiceCategoryRepo customerServiceCategoryRepository;

    @Autowired
    public CustomerServiceCategoryService(CustomerServiceCategoryRepo customerServiceCategoryRepository) {
        this.customerServiceCategoryRepository = customerServiceCategoryRepository;
    }

    public List<CustomerServiceCategory> getAllCategories() {
        return customerServiceCategoryRepository.findAll();
    }

    public CustomerServiceCategory getCategoryById(Long id) {
        return customerServiceCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
    
    public class ResourceAlreadyExistsException extends RuntimeException {
        public ResourceAlreadyExistsException(String message) {
            super(message);
        }
    }

    public CustomerServiceCategory createCategory(CustomerServiceCategory category) {
        CustomerServiceCategory existingCategory = customerServiceCategoryRepository.findByName(category.getName());
        if (existingCategory != null) {
            throw new ResourceAlreadyExistsException("Category with name " + category.getName() + " already exists");
        }
        return customerServiceCategoryRepository.save(category);
    }

    public CustomerServiceCategory updateCategory(Long id, CustomerServiceCategory updatedCategory) {
        CustomerServiceCategory existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        return customerServiceCategoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        CustomerServiceCategory category = getCategoryById(id);
        customerServiceCategoryRepository.delete(category);
    }
}
