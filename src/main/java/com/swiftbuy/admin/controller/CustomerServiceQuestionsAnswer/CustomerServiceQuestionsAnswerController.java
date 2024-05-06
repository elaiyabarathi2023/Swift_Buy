package com.swiftbuy.admin.controller.CustomerServiceQuestionsAnswer;

//public class CustomerServiceQuestionsAnswerController {
//
//}


import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-service-questions-answers")
public class CustomerServiceQuestionsAnswerController {

    @Autowired
    private CustomerServiceQuestionsAnswerService customerServiceQuestionsAnswerService;

    @GetMapping
    public ResponseEntity<List<CustomerServiceQuestionsAnswer>> getAllCustomerServiceQuestionsAnswers() {
        List<CustomerServiceQuestionsAnswer> customerServiceQuestionsAnswers = customerServiceQuestionsAnswerService.getAllCustomerServiceQuestionsAnswers();
        return ResponseEntity.ok(customerServiceQuestionsAnswers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerServiceQuestionsAnswer> getCustomerServiceQuestionsAnswerById(@PathVariable Long id) {
        CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer = customerServiceQuestionsAnswerService.getCustomerServiceQuestionsAnswerById(id);
        return ResponseEntity.ok(customerServiceQuestionsAnswer);
    }

    @PostMapping
    public ResponseEntity<CustomerServiceQuestionsAnswer> createCustomerServiceQuestionsAnswer(@RequestBody CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer) {
        CustomerServiceQuestionsAnswer createdCustomerServiceQuestionsAnswer = customerServiceQuestionsAnswerService.createCustomerServiceQuestionsAnswer(customerServiceQuestionsAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerServiceQuestionsAnswer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerServiceQuestionsAnswer> updateCustomerServiceQuestionsAnswer(@PathVariable Long id, @RequestBody CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer) {
        CustomerServiceQuestionsAnswer updatedCustomerServiceQuestionsAnswer = customerServiceQuestionsAnswerService.updateCustomerServiceQuestionsAnswer(id, customerServiceQuestionsAnswer);
        return ResponseEntity.ok(updatedCustomerServiceQuestionsAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerServiceQuestionsAnswer(@PathVariable Long id) {
        customerServiceQuestionsAnswerService.deleteCustomerServiceQuestionsAnswer(id);
        return ResponseEntity.noContent().build();
    }
}
