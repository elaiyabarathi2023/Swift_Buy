package com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer;

//public class CustomerServiceQuestionsAnswerService {
//
//}


import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceQuestionsAnswerService {

    @Autowired
    private CustomerServiceQuestionsAnswerRepo customerServiceQuestionsAnswerRepository;

    public List<CustomerServiceQuestionsAnswer> getAllCustomerServiceQuestionsAnswers() {
        return customerServiceQuestionsAnswerRepository.findAll();
    }

    public CustomerServiceQuestionsAnswer getCustomerServiceQuestionsAnswerById(Long id) {
        return customerServiceQuestionsAnswerRepository.findById(id)
                .orElseThrow();
    }

    public CustomerServiceQuestionsAnswer createCustomerServiceQuestionsAnswer(CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer) {
        return customerServiceQuestionsAnswerRepository.save(customerServiceQuestionsAnswer);
    }

    public CustomerServiceQuestionsAnswer updateCustomerServiceQuestionsAnswer(Long id, CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer) {
        CustomerServiceQuestionsAnswer existingCustomerServiceQuestionsAnswer = getCustomerServiceQuestionsAnswerById(id);
        existingCustomerServiceQuestionsAnswer.setQuestion(customerServiceQuestionsAnswer.getQuestion());
        existingCustomerServiceQuestionsAnswer.setAnswer(customerServiceQuestionsAnswer.getAnswer());
        existingCustomerServiceQuestionsAnswer.setSubCategory(customerServiceQuestionsAnswer.getSubCategory());
        return customerServiceQuestionsAnswerRepository.save(existingCustomerServiceQuestionsAnswer);
    }

    public void deleteCustomerServiceQuestionsAnswer(Long id) {
        CustomerServiceQuestionsAnswer customerServiceQuestionsAnswer = getCustomerServiceQuestionsAnswerById(id);
        customerServiceQuestionsAnswerRepository.delete(customerServiceQuestionsAnswer);
    }
}