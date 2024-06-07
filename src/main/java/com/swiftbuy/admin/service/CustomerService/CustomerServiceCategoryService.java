package com.swiftbuy.admin.service.CustomerService;
 
import com.swiftbuy.admin.model.CustomerService.CustomerServiceCategory;
import com.swiftbuy.admin.repository.CustomerService.CustomerServiceCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import java.util.Optional;
 
@Service
public class CustomerServiceCategoryService {
 
    @Autowired
    private CustomerServiceCategoryRepository repository;
 
    public CustomerServiceCategory createCategory(CustomerServiceCategory category) {
        return repository.save(category);
    }
 
    public CustomerServiceCategory getCategoryById(Long cscategoryid) {
        Optional<CustomerServiceCategory> categoryOptional = repository.findById(cscategoryid);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
    public CustomerServiceCategory updateCategory(Long cscategoryid, CustomerServiceCategory categoryDetails) {
        return repository.findById(cscategoryid)
                         .map(existingCategory -> {
                             existingCategory.setName(categoryDetails.getName());
                             existingCategory.setDescription(categoryDetails.getDescription());
                             return repository.save(existingCategory);
                         })
                         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
 
    public boolean deleteCategory(Long cscategoryid) {
        CustomerServiceCategory existingCategory = getCategoryById(cscategoryid);
        repository.delete(existingCategory);
        return true;
    }

    
    public Iterable<CustomerServiceCategory> getAllCategories() {
        return repository.findAll();
    }
}