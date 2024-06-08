package com.swiftbuy.admin.controller.CustomerService;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerService.CustomerServiceSubCategory;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceQuestionsAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/questions-answers")
public class CustomerServiceQuestionsAnswerController {
    @Autowired
    private CustomerServiceQuestionsAnswerService questionsAnswerService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllQuestionsAnswers() {
        Map<String, Object> response = new HashMap<>();
      
            List<CustomerServiceQuestionsAnswer> questionsAnswers = questionsAnswerService.getAllQuestionsAnswers();
            response.put("status", true);
            response.put("message", "All questions and answers retrieved successfully");
            response.put("questionsAnswers", questionsAnswers);
            return ResponseEntity.ok(response);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getQuestionAnswerById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<CustomerServiceQuestionsAnswer> questionAnswer = questionsAnswerService.getQuestionsAnswerById(id);
       
            response.put("status", true);
            response.put("message", "Question and answer retrieved successfully");
            response.put("questionAnswer", questionAnswer.get());
            return ResponseEntity.ok(response);
        
    }

    @PostMapping("/post")
    public ResponseEntity<Map<String, Object>> createQuestionAnswer(
            @RequestBody CustomerServiceQuestionsAnswer questionAnswer) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceQuestionsAnswer savedQuestionAnswer = questionsAnswerService.saveQuestionsAnswer(questionAnswer);
            response.put("status", true);
            response.put("message", "Question and answer created successfully");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", false);
            response.put("message", "Failed to create question and answer");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateQuestionAnswer(@PathVariable("id") Long id,
                                                                  @RequestBody CustomerServiceQuestionsAnswer updatedQuestionAnswer) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomerServiceQuestionsAnswer updatedAnswer = questionsAnswerService.updateQuestionsAnswer(id, updatedQuestionAnswer);
            response.put("status", true);
            response.put("message", "Question and answer updated successfully");
           
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            response.put("status", false);
            response.put("message", "Failed to update question and answer");
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteQuestionAnswer(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean deleted = questionsAnswerService.deleteQuestionsAnswer(id);
         
                response.put("status", true);
                response.put("message", "Question and answer deleted successfully");
                return ResponseEntity.ok(response);
            
        } catch (ResponseStatusException e) {
            response.put("status", false);
            response.put("message", "Failed to delete question and answer");
            return ResponseEntity.status(e.getStatusCode()).body(response);
        }
    }

    @GetMapping("/subcategory/{id}")
    public ResponseEntity<Map<String, Object>> getSubCategoryById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        CustomerServiceSubCategory subCategory = questionsAnswerService.getSubCategoryById(id);
      
            response.put("status", true);
            response.put("message", "Subcategory retrieved successfully");
            response.put("subCategory", subCategory);
            return ResponseEntity.ok(response);
        } 
    
}