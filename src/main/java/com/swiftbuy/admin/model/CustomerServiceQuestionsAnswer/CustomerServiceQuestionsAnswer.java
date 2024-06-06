package com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer;
 
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
import jakarta.persistence.*;
 
@Entity
@Table(name = "customer_service_questions_answers_part")
public class CustomerServiceQuestionsAnswer {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionsanswersid;
 
    @Column(nullable = false)
    private String question;
 
    @Column(nullable = false)
    private String answer;
 
    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false)
    private CustomerServiceSubCategory subCategory;
 
    // Getters and setters
 
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
 
    public CustomerServiceSubCategory getSubCategory() {
        return subCategory;
    }
 
    public void setSubCategory(CustomerServiceSubCategory subCategory) {
        this.subCategory = subCategory;
    }
}