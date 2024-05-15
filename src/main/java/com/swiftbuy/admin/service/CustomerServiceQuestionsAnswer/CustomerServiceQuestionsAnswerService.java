

 

package com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer;
 
//public class CustomerServiceQuestionsAnswerService {

//

//}
 

import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceQuestionsAnswerService {

    @Autowired
    private CustomerServiceQuestionsAnswerRepo customerServiceQuestionsAnswerRepo;

    public List<CustomerServiceQuestionsAnswer> getAllQuestionsAnswers() {
        return (List<CustomerServiceQuestionsAnswer>) customerServiceQuestionsAnswerRepo.findAll();
    }

    public Optional<CustomerServiceQuestionsAnswer> getQuestionsAnswerById(Long id) {
        return customerServiceQuestionsAnswerRepo.findById(id);
    }

    public CustomerServiceQuestionsAnswer saveQuestionsAnswer(CustomerServiceQuestionsAnswer questionsAnswer) {
        return customerServiceQuestionsAnswerRepo.save(questionsAnswer);
    }

    public void deleteQuestionsAnswer(Long id) {
        customerServiceQuestionsAnswerRepo.deleteById(id);
    }

    public CustomerServiceQuestionsAnswer updateQuestionsAnswer(Long id, CustomerServiceQuestionsAnswer updatedQuestionsAnswer) {
        Optional<CustomerServiceQuestionsAnswer> existingQuestionsAnswer = customerServiceQuestionsAnswerRepo.findById(id);
        if (existingQuestionsAnswer.isPresent()) {
            CustomerServiceQuestionsAnswer questionsAnswer = existingQuestionsAnswer.get();
            questionsAnswer.setQuestion(updatedQuestionsAnswer.getQuestion());
            questionsAnswer.setAnswer(updatedQuestionsAnswer.getAnswer());
            return customerServiceQuestionsAnswerRepo.save(questionsAnswer);
        } else {
            return null;
        }
    }
}