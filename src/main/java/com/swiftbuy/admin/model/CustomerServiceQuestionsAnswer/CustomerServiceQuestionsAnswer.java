package com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer;

import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_service_questions_answers")
public class CustomerServiceQuestionsAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private CustomerServiceSubCategory subCategory;

    // Constructors

    public CustomerServiceQuestionsAnswer() {
    }

    public CustomerServiceQuestionsAnswer(String question, String answer, CustomerServiceSubCategory subCategory) {
        this.question = question;
        this.answer = answer;
        this.subCategory = subCategory;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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