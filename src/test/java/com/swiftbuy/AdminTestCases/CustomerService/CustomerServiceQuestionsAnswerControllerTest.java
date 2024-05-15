package com.swiftbuy.AdminTestCases.CustomerService;

//public class CustomerServiceQuestionsAnswerTest {
//
//}


import com.swiftbuy.admin.controller.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerController;
import com.swiftbuy.admin.model.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.service.CustomerServiceQuestionsAnswer.CustomerServiceQuestionsAnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.json.JSONObject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerServiceQuestionsAnswerController.class)
public class CustomerServiceQuestionsAnswerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceQuestionsAnswerService questionAnswerService;

    @MockBean
    private CustomerServiceQuestionsAnswerController questionAnswerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(questionAnswerController).build();
    }

    @Test
    public void testGetAllCustomerServiceQuestionsAnswers() throws Exception {
        mockMvc.perform(get("/api/customer-service-questions-answers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerServiceQuestionsAnswerById() throws Exception {
        Long questionAnswerId = 1L;
        mockMvc.perform(get("/api/customer-service-questions-answers/{id}", questionAnswerId))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCustomerServiceQuestionsAnswer() throws Exception {
        JSONObject questionAnswerJson = new JSONObject();
        questionAnswerJson.put("question", "New Question");
        questionAnswerJson.put("answer", "New Answer");
        questionAnswerJson.put("subCategory", null); // Replace null with the appropriate subcategory JSON object if needed

       

        mockMvc.perform(post("/api/customer-service-questions-answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionAnswerJson.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCustomerServiceQuestionsAnswer() throws Exception {
        JSONObject questionAnswerJson = new JSONObject();
        questionAnswerJson.put("question", "Updated Question");
        questionAnswerJson.put("answer", "Updated Answer");
        questionAnswerJson.put("subCategory", null); // Replace null with the appropriate subcategory JSON object if needed

        Long questionAnswerId = 1L;

       
        mockMvc.perform(put("/api/customer-service-questions-answers/{id}", questionAnswerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionAnswerJson.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCustomerServiceQuestionsAnswer() throws Exception {
        Long questionAnswerId = 1L;
        mockMvc.perform(delete("/api/customer-service-questions-answers/{id}", questionAnswerId))
                .andExpect(status().isOk());
    }
}
