package com.swiftbuy.admin.repository.CustomerService;
 
import org.springframework.data.repository.CrudRepository;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceQuestionsAnswer;
 
public interface CustomerServiceQuestionsAnswerRepository extends CrudRepository<CustomerServiceQuestionsAnswer, Long> {
    // Add custom queries if needed
}