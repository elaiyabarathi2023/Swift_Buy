package com.swiftbuy.admin.model.CustomerServiceSubCategory;

import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;

import jakarta.persistence.*;


import com.swiftbuy.admin.model.CustomerServiceCategory.CustomerServiceCategory;
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import jakarta.persistence.*;


//Postman Query:
//	
//{
//    "name": "New SubCategorymnd,md,mksal Name",
//    "description": "Descriptionkdjkkdd of the new SubCategory",
//    "questionanswer": {
//        "questionsanswersid": 2
//    }
//}
@Entity
@Table(name = "customer_service_sub_categories_final")
public class CustomerServiceSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "questions_answers_id", nullable = true)
    private CustomerServiceQuestionsAnswer questionanswer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CustomerServiceQuestionsAnswer getQuestionanswer() {
		return questionanswer;
	}

	public void setQuestionanswer(CustomerServiceQuestionsAnswer questionanswer) {
		this.questionanswer = questionanswer;
	}

    
}