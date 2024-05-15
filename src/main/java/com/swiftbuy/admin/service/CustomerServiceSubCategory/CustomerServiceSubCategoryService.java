package com.swiftbuy.admin.service.CustomerServiceSubCategory;


//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
//import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
//import com.swiftbuy.admin.repository.CustomerServiceCategory.CustomerServiceCategoryRepo;
//import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;
//
//import java.util.List;
//
//@Service
//public class CustomerServiceSubCategoryService {
//
//    @Autowired
//    private CustomerServiceSubCategoryRepo customerServiceSubCategoryRepository;
//
//    @Autowired
//    private CustomerServiceCategoryRepo customerServiceCategoryRepository;
//
//    public List<CustomerServiceSubCategory> getAllCustomerServiceSubCategories() {
//        return (List<CustomerServiceSubCategory>) customerServiceSubCategoryRepository.findAll();
//    }
//
//    public CustomerServiceSubCategory getCustomerServiceSubCategoryById(Long id) {
//        return customerServiceSubCategoryRepository.findById(id)
//                .orElseThrow();
//    }
//
//    public CustomerServiceSubCategory createCustomerServiceSubCategory(CustomerServiceSubCategory customerServiceSubCategory) {
//        Long categoryId = customerServiceSubCategory.getCategory().getId();
//        CustomerServiceCategory category = customerServiceCategoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
//
//        customerServiceSubCategory.setCategory(category);
//        return customerServiceSubCategoryRepository.save(customerServiceSubCategory);
//    }
//
//    public CustomerServiceSubCategory updateCustomerServiceSubCategory(Long id, CustomerServiceSubCategory customerServiceSubCategory) {
//        CustomerServiceSubCategory existingCustomerServiceSubCategory = getCustomerServiceSubCategoryById(id);
//
//        existingCustomerServiceSubCategory.setName(customerServiceSubCategory.getName());
//        existingCustomerServiceSubCategory.setDescription(customerServiceSubCategory.getDescription());
//
//        Long categoryId = customerServiceSubCategory.getCategory().getId();
//        CustomerServiceCategory category = customerServiceCategoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
//
//        existingCustomerServiceSubCategory.setCategory(category);
//
//        return customerServiceSubCategoryRepository.save(existingCustomerServiceSubCategory);
//    }
//
//    public void deleteCustomerServiceSubCategory(Long id) {
//        CustomerServiceSubCategory customerServiceSubCategory = getCustomerServiceSubCategoryById(id);
//        customerServiceSubCategoryRepository.delete(customerServiceSubCategory);
//    }
//}






import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerRepo;
import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceSubCategoryService {

    @Autowired
    private CustomerServiceSubCategoryRepo customerServiceSubCategoryRepo;

    @Autowired
    private CustomerServiceQuestionsAnswerRepo customerServiceQuestionsAnswerRepo;

    public List<CustomerServiceSubCategory> getAllCustomerServiceSubCategories() {
        return (List<CustomerServiceSubCategory>) customerServiceSubCategoryRepo.findAll();
    }

    public CustomerServiceSubCategory getCustomerServiceSubCategoryById(Long id) {
        Optional<CustomerServiceSubCategory> optionalCustomerServiceSubCategory = customerServiceSubCategoryRepo.findById(id);
        return optionalCustomerServiceSubCategory.orElse(null);
    }

    public CustomerServiceSubCategory createCustomerServiceSubCategory(CustomerServiceSubCategory customerServiceSubCategory) {
        Long questionsAnswerId = customerServiceSubCategory.getQuestionanswer().getQuestionsanswersid();
        CustomerServiceQuestionsAnswer questionsAnswer = getQuestionsAnswerById(questionsAnswerId)
                .orElseThrow(() -> new RuntimeException("Questions Answer not found with id " + questionsAnswerId));

        customerServiceSubCategory.setQuestionanswer(questionsAnswer);
        return customerServiceSubCategoryRepo.save(customerServiceSubCategory);
    }

    public CustomerServiceSubCategory updateCustomerServiceSubCategory(Long id, CustomerServiceSubCategory updatedCustomerServiceSubCategory) {
        Optional<CustomerServiceSubCategory> optionalCustomerServiceSubCategory = customerServiceSubCategoryRepo.findById(id);
        if (optionalCustomerServiceSubCategory.isPresent()) {
            CustomerServiceSubCategory existingCustomerServiceSubCategory = optionalCustomerServiceSubCategory.get();
            existingCustomerServiceSubCategory.setName(updatedCustomerServiceSubCategory.getName());
            existingCustomerServiceSubCategory.setDescription(updatedCustomerServiceSubCategory.getDescription());
            return customerServiceSubCategoryRepo.save(existingCustomerServiceSubCategory);
        }
        return null;
    }

    public void deleteCustomerServiceSubCategory(Long id) {
        Optional<CustomerServiceSubCategory> optionalCustomerServiceSubCategory = customerServiceSubCategoryRepo.findById(id);
        if (optionalCustomerServiceSubCategory.isPresent()) {
            CustomerServiceSubCategory customerServiceSubCategory = optionalCustomerServiceSubCategory.get();
            customerServiceSubCategoryRepo.delete(customerServiceSubCategory);
        }
    }

    public void addQuestionsAnswer(Long subCategoryId, Long questionsAnswerId) {
        Optional<CustomerServiceSubCategory> optionalCustomerServiceSubCategory = customerServiceSubCategoryRepo.findById(subCategoryId);
        if (optionalCustomerServiceSubCategory.isPresent()) {
            CustomerServiceSubCategory customerServiceSubCategory = optionalCustomerServiceSubCategory.get();
            Optional<CustomerServiceQuestionsAnswer> optionalQuestionsAnswer = getQuestionsAnswerById(questionsAnswerId);
            if (optionalQuestionsAnswer.isPresent()) {
                CustomerServiceQuestionsAnswer questionsAnswer = optionalQuestionsAnswer.get();
                customerServiceSubCategory.setQuestionanswer(questionsAnswer);
                customerServiceSubCategoryRepo.save(customerServiceSubCategory);
            }
        }
    }

    private Optional<CustomerServiceQuestionsAnswer> getQuestionsAnswerById(Long questionsAnswerId) {
        return customerServiceQuestionsAnswerRepo.findById(questionsAnswerId);
    }
}