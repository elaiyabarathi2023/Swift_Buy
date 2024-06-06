

 

package com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer;
 
//public class CustomerServiceQuestionsAnswerService {

//

//}
 

import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerRepository;
import com.swiftbuy.admin.repository.CustomerServiceSubCategory.CustomerServiceSubCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
 
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
 
@Service
public class CustomerServiceQuestionsAnswerService {
 
    @Autowired
    private CustomerServiceQuestionsAnswerRepository customerServiceQuestionsAnswerRepo;
 
    @Autowired
    private CustomerServiceSubCategoryRepo subCategoryRepository;
 
    public List<CustomerServiceQuestionsAnswer> getAllQuestionsAnswers() {
        return (List<CustomerServiceQuestionsAnswer>) customerServiceQuestionsAnswerRepo.findAll();
    }
 
    public Optional<CustomerServiceQuestionsAnswer> getQuestionsAnswerById(Long id) {
        return customerServiceQuestionsAnswerRepo.findById(id);
    }
 
    public CustomerServiceQuestionsAnswer saveQuestionsAnswer(CustomerServiceQuestionsAnswer questionsAnswer) {
        Long subCategoryId = questionsAnswer.getSubCategory().getId();
        Optional<CustomerServiceSubCategory> subCategoryOptional = subCategoryRepository.findById(subCategoryId);
        if (subCategoryOptional.isPresent()) {
            CustomerServiceSubCategory subCategory = subCategoryOptional.get();
            questionsAnswer.setSubCategory(subCategory);
            return customerServiceQuestionsAnswerRepo.save(questionsAnswer);
        } else {
            throw new RuntimeException("Subcategory not found with id " + subCategoryId);
        }
    }
 
 
    public boolean deleteQuestionsAnswer(Long id) {
        if (customerServiceQuestionsAnswerRepo.existsById(id)) {
            customerServiceQuestionsAnswerRepo.deleteById(id);
            return true;
        }else {
        	 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to delete the question");}
    }
    public CustomerServiceQuestionsAnswer updateQuestionsAnswer(Long id, CustomerServiceQuestionsAnswer updatedQuestionsAnswer) {
        Optional<CustomerServiceQuestionsAnswer> existingQuestionsAnswer = customerServiceQuestionsAnswerRepo.findById(id);
        if (existingQuestionsAnswer.isPresent()) {
            CustomerServiceQuestionsAnswer questionsAnswer = existingQuestionsAnswer.get();
            questionsAnswer.setQuestion(updatedQuestionsAnswer.getQuestion());
            questionsAnswer.setAnswer(updatedQuestionsAnswer.getAnswer());

            if (updatedQuestionsAnswer.getSubCategory() == null || updatedQuestionsAnswer.getSubCategory().getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SubCategory or SubCategory ID cannot be null");
            }

            Long subCategoryId = updatedQuestionsAnswer.getSubCategory().getId();
            CustomerServiceSubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubCategory Not Found"));
            questionsAnswer.setSubCategory(subCategory);

            return customerServiceQuestionsAnswerRepo.save(questionsAnswer);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Answer Not Found");
        }
    }

 
    
 
    // Method to fetch CustomerServiceSubCategory by id
    public CustomerServiceSubCategory getSubCategoryById(Long id) {
        Optional<CustomerServiceSubCategory> optionalSubCategory = subCategoryRepository.findById(id);
        return optionalSubCategory.orElse(null);
    }
}