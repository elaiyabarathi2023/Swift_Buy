package com.swiftbuy.admin.repository.CustomerServiceQuestionsAnswer;

//public class CustomerServiceQuestionsAnswerRepo {
//
//}


import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerServiceQuestionsAnswerRepo extends CrudRepository<CustomerServiceQuestionsAnswer, Long> {
    // You can add custom queries here if needed
}
