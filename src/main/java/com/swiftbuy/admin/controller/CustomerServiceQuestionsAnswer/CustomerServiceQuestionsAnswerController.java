
package com.swiftbuy.admin.controller.CustomerServiceQuestionsAnswer;

//@RequestMapping("/api/customer-service-questions-answers")


import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer-service-questions-answers")
public class CustomerServiceQuestionsAnswerController {

    @Autowired
    private CustomerServiceQuestionsAnswerService customerServiceQuestionsAnswerService;

    @GetMapping
    public ResponseEntity<List<CustomerServiceQuestionsAnswer>> getAllQuestionsAnswers() {
        List<CustomerServiceQuestionsAnswer> questionsAnswers = customerServiceQuestionsAnswerService.getAllQuestionsAnswers();
        return ResponseEntity.ok(questionsAnswers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceQuestionsAnswer> getQuestionsAnswerById(@PathVariable Long id) {
        Optional<CustomerServiceQuestionsAnswer> questionsAnswer = customerServiceQuestionsAnswerService.getQuestionsAnswerById(id);
        return questionsAnswer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerServiceQuestionsAnswer> createQuestionsAnswer(@RequestBody CustomerServiceQuestionsAnswer questionsAnswer) {
        CustomerServiceQuestionsAnswer createdQuestionsAnswer = customerServiceQuestionsAnswerService.saveQuestionsAnswer(questionsAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionsAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceQuestionsAnswer> updateQuestionsAnswer(@PathVariable Long id, @RequestBody CustomerServiceQuestionsAnswer updatedQuestionsAnswer) {
        CustomerServiceQuestionsAnswer updatedAnswer = customerServiceQuestionsAnswerService.updateQuestionsAnswer(id, updatedQuestionsAnswer);
        return updatedAnswer != null ? ResponseEntity.ok(updatedAnswer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestionsAnswer(@PathVariable Long id) {
        customerServiceQuestionsAnswerService.deleteQuestionsAnswer(id);
        return ResponseEntity.noContent().build();
    }
}