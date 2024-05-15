package com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer;

 
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
 
import jakarta.persistence.*;
 

//Postman Query:
//	
//{
//    "question": "How can I trsjkkxkjacksjkjksjksuiijksdkjkjx my ksiurrwnbnmxkjxj skkd?",
//    "answer": "You can track your xkn,xm,mx osm,jxhjhjxmx rder xklkjxjknxnxm,by logging into your account and visiting the order trackidjkskjdjkwxkkxng section."
//}
@Entity

@Table(name = "customer_service_questions_answers_finalise")

public class CustomerServiceQuestionsAnswer {
 
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionsanswersid;
 
    

	@Column(nullable = false)

    private String question;
 
    @Column(nullable = false)

    private String answer;

	public Long getQuestionsanswersid() {
		return questionsanswersid;
	}

	public void setQuestionsanswersid(Long questionsanswersid) {
		this.questionsanswersid = questionsanswersid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
 

    
 


}