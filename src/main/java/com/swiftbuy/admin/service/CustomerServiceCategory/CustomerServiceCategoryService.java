package com.swiftbuy.admin.service.CustomerServiceCategory;



import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerServiceCategory.CustomerServiceCategoryRepo;
import com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerRepo;
import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceCategoryService {

    @Autowired
    private CustomerServiceCategoryRepo customerServiceCategoryRepo;

    @Autowired
    private CustomerServiceQuestionsAnswerRepo customerServiceQuestionsAnswerRepo;
    
//    @Autowired
//    private CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer;
    
    @Autowired
    private CustomerServiceSubCategoryRepo customerServiceSubCategoryRepository;
    
    

    public List<CustomerServiceCategory> getAllCustomerServiceCategories() {
        return (List<CustomerServiceCategory>) customerServiceCategoryRepo.findAll();
    }

    public CustomerServiceCategory getCustomerServiceCategoryById(Long id) {
        Optional<CustomerServiceCategory> optionalCustomerServiceCategory = customerServiceCategoryRepo.findById(id);
        return optionalCustomerServiceCategory.orElse(null);
    }

    public CustomerServiceCategory createCustomerServiceCategory(CustomerServiceCategory customerServiceCategory) {
    	Long subCategoryId = customerServiceCategory.getCustomerservicesubcategory().getId();  	
    	CustomerServiceSubCategory subCategory = customerServiceSubCategoryRepository.findById(subCategoryId)
    			.orElseThrow(() -> new RuntimeException("Sub Category not found with id " + subCategoryId));
    	customerServiceCategory.setCustomerservicesubcategory(subCategory);
    	return customerServiceSubCategoryRepository.save(customerServiceCategory);
//    	Long subCategoryId = customerServiceQuestionsAnswer.getSubCategory().getId();
    }

    public CustomerServiceCategory updateCustomerServiceCategory(Long id, CustomerServiceCategory updatedCustomerServiceCategory) {
        Optional<CustomerServiceCategory> optionalCustomerServiceCategory = customerServiceCategoryRepo.findById(id);
        if (optionalCustomerServiceCategory.isPresent()) {
            CustomerServiceCategory existingCustomerServiceCategory = optionalCustomerServiceCategory.get();
            existingCustomerServiceCategory.setName(updatedCustomerServiceCategory.getName());
            existingCustomerServiceCategory.setDescription(updatedCustomerServiceCategory.getDescription());
            return customerServiceCategoryRepo.save(existingCustomerServiceCategory);
        }
        return null;
    }

    public void deleteCustomerServiceCategory(Long id) {
        Optional<CustomerServiceCategory> optionalCustomerServiceCategory = customerServiceCategoryRepo.findById(id);
        if (optionalCustomerServiceCategory.isPresent()) {
            CustomerServiceCategory customerServiceCategory = optionalCustomerServiceCategory.get();
            customerServiceCategoryRepo.delete(customerServiceCategory);
        }
    }

    
}