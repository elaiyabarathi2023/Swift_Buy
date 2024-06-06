package com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer;
 
//public class CustomerServiceQuestionsAnswerRepo {
//
//}
 
 
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import org.springframework.data.repository.CrudRepository;
 
public interface CustomerServiceQuestionsAnswerRepository extends CrudRepository<CustomerServiceQuestionsAnswer, Long> {
    // Add custom queries if needed
}