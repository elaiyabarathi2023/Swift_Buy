
 
 
 
 
 
 
 
 
 


 
 
 
 
package com.swiftbuy.admin.controller.CustomerServiceQuestionsAnswer;
 
 
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/questions-answers")
public class CustomerServiceQuestionsAnswerController {
 
    @Autowired
    private CustomerServiceQuestionsAnswerService questionsAnswerService;
 
    @GetMapping
    public ResponseEntity<List<CustomerServiceQuestionsAnswer>> getAllQuestionsAnswers() {
        
            List<CustomerServiceQuestionsAnswer> questionsAnswers = questionsAnswerService.getAllQuestionsAnswers();
            return ResponseEntity.ok(questionsAnswers);
        }
    
 
    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerServiceQuestionsAnswer>> getQuestionAnswerById(@PathVariable("id") Long id) {
            Optional<CustomerServiceQuestionsAnswer> questionAnswer = questionsAnswerService.getQuestionsAnswerById(id);
         return ResponseEntity.ok(questionAnswer);
    }
 
    @PostMapping("/post")
    public ResponseEntity<CustomerServiceQuestionsAnswer> createQuestionAnswer(@RequestBody CustomerServiceQuestionsAnswer questionAnswer) {
        try {
            CustomerServiceQuestionsAnswer savedQuestionAnswer = questionsAnswerService.saveQuestionsAnswer(questionAnswer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestionAnswer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceQuestionsAnswer> updateQuestionAnswer(@PathVariable("id") Long id, @RequestBody CustomerServiceQuestionsAnswer updatedQuestionAnswer) {
        try {
            CustomerServiceQuestionsAnswer updatedAnswer = questionsAnswerService.updateQuestionsAnswer(id, updatedQuestionAnswer);
            return ResponseEntity.ok(updatedAnswer);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

 
 
    
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionAnswer(@PathVariable("id") Long id) {
        try {
            boolean deleted = questionsAnswerService.deleteQuestionsAnswer(id);
            return  ResponseEntity.noContent().build() ;
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
 
    // Additional endpoint to fetch subcategory by id
    @GetMapping("/subcategory/{id}")
    public ResponseEntity<CustomerServiceSubCategory> getSubCategoryById(@PathVariable("id") Long id) {
      
            CustomerServiceSubCategory subCategory = questionsAnswerService.getSubCategoryById(id);
            return subCategory != null ? ResponseEntity.ok(subCategory) : ResponseEntity.notFound().build();
        
        
    }
}