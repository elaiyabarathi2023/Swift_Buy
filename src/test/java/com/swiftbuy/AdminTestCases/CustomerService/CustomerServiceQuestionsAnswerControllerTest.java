package com.swiftbuy.AdminTestCases.CustomerService;

import com.swiftbuy.admin.model.CustomerService.CustomerServiceQuestionsAnswer;
import com.swiftbuy.admin.model.CustomerService.CustomerServiceSubCategory;
import com.swiftbuy.admin.repository.CustomerService.CustomerServiceQuestionsAnswerRepository;
import com.swiftbuy.admin.repository.CustomerService.CustomerServiceSubCategoryRepo;
import com.swiftbuy.admin.service.CustomerService.CustomerServiceQuestionsAnswerService;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceQuestionsAnswerControllerTest {

    @MockBean
    private CustomerServiceQuestionsAnswerRepository customerServiceQuestionsAnswerRepo;

    @MockBean
    private CustomerServiceSubCategoryRepo subCategoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerServiceQuestionsAnswerService customerServiceQuestionsAnswerService;

    @BeforeEach
    public void setUpMocks() {
        CustomerServiceQuestionsAnswer questionsAnswer = new CustomerServiceQuestionsAnswer();
        questionsAnswer.setQuestionsanswersid(1L);
        questionsAnswer.setQuestion("How do I reset my password?");
        questionsAnswer.setAnswer("Go to the login page and click on 'Forgot Password'.");

        CustomerServiceSubCategory subCategory = new CustomerServiceSubCategory();
        subCategory.setId(1L);
        subCategory.setName("Account Issues");
        questionsAnswer.setSubCategory(subCategory);

        when(customerServiceQuestionsAnswerRepo.findById(1L)).thenReturn(Optional.of(questionsAnswer));
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(subCategory));
    }

    @Test
    public void testGetAllQuestionsAnswers() throws Exception {
        CustomerServiceQuestionsAnswer qa1 = new CustomerServiceQuestionsAnswer();
        qa1.setQuestionsanswersid(1L);
        CustomerServiceQuestionsAnswer qa2 = new CustomerServiceQuestionsAnswer();
        qa2.setQuestionsanswersid(2L);

        when(customerServiceQuestionsAnswerRepo.findAll()).thenReturn(Arrays.asList(qa1, qa2));

        mockMvc.perform(get("/api/questions-answers"))
               .andExpect(status().isOk());
    }

    @Test
    public void testGetQuestionsAnswerById() throws Exception {
        mockMvc.perform(get("/api/questions-answers/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void testSaveQuestionsAnswer() throws Exception {
        JSONObject questionsAnswerJson = new JSONObject();
        questionsAnswerJson.put("question", "How do I change my email?");
        questionsAnswerJson.put("answer", "Go to Account Settings and select 'Change Email'.");
        
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 1L);
        questionsAnswerJson.put("subCategory", subCategoryJson);

        mockMvc.perform(post("/api/questions-answers/post")
               .content(questionsAnswerJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateQuestionsAnswer() throws Exception {
        JSONObject updatedQaJson = new JSONObject();
        updatedQaJson.put("question", "How do I reset my password? (Updated)");
        updatedQaJson.put("answer", "Click 'Forgot Password' on the login page.");
        
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 1L);
        updatedQaJson.put("subCategory", subCategoryJson);

        mockMvc.perform(put("/api/questions-answers/1")
               .content(updatedQaJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void testDeleteQuestionsAnswer() throws Exception {
        when(customerServiceQuestionsAnswerRepo.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/questions-answers/1"))
               .andExpect(status().isOk());

        verify(customerServiceQuestionsAnswerRepo, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteQuestionsAnswer_NotFound() throws Exception {
        when(customerServiceQuestionsAnswerRepo.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/questions-answers/999"))
               .andExpect(status().isNotFound());

        verify(customerServiceQuestionsAnswerRepo, never()).deleteById(999L);
    }

    @Test
    public void testGetSubCategoryById() throws Exception {
        mockMvc.perform(get("/api/questions-answers/subcategory/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void testSaveQuestionsAnswer_SubCategoryNotFound() throws Exception {
        JSONObject questionsAnswerJson = new JSONObject();
        questionsAnswerJson.put("question", "How do I change my email?");
        questionsAnswerJson.put("answer", "Go to Account Settings and select 'Change Email'.");
        
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 999L);
        questionsAnswerJson.put("subCategory", subCategoryJson);

        when(subCategoryRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/questions-answers/post")
               .content(questionsAnswerJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isInternalServerError());
    }

    @Test
    public void testSaveQuestionsAnswer_NullSubCategory() throws Exception {
        JSONObject questionsAnswerJson = new JSONObject();
        questionsAnswerJson.put("question", "How do I change my email?");
        questionsAnswerJson.put("answer", "Go to Account Settings and select 'Change Email'.");
        questionsAnswerJson.put("subCategory", JSONObject.NULL);

        mockMvc.perform(post("/api/questions-answers/post")
               .content(questionsAnswerJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdateQuestionsAnswer_NotFound() throws Exception {
        JSONObject updatedQaJson = new JSONObject();
        updatedQaJson.put("question", "Updated Question");
        updatedQaJson.put("answer", "Updated Answer");
        
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 1L);
        updatedQaJson.put("subCategory", subCategoryJson);

        when(customerServiceQuestionsAnswerRepo.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/questions-answers/999")
               .content(updatedQaJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateQuestionsAnswer_SubCategoryNotFound() throws Exception {
        CustomerServiceQuestionsAnswer existingQa = new CustomerServiceQuestionsAnswer();
        existingQa.setQuestionsanswersid(1L);
        when(customerServiceQuestionsAnswerRepo.findById(1L)).thenReturn(Optional.of(existingQa));

        JSONObject updatedQaJson = new JSONObject();
        updatedQaJson.put("question", "Updated Question");
        updatedQaJson.put("answer", "Updated Answer");
        
        JSONObject subCategoryJson = new JSONObject();
        subCategoryJson.put("id", 999L);
        updatedQaJson.put("subCategory", subCategoryJson);

        when(subCategoryRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/questions-answers/1")
               .content(updatedQaJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateQuestionsAnswer_NullSubCategory() throws Exception {
        CustomerServiceQuestionsAnswer existingQa = new CustomerServiceQuestionsAnswer();
        existingQa.setQuestionsanswersid(1L);
        when(customerServiceQuestionsAnswerRepo.findById(1L)).thenReturn(Optional.of(existingQa));

        JSONObject updatedQaJson = new JSONObject();
        updatedQaJson.put("question", "Updated Question");
        updatedQaJson.put("answer", "Updated Answer");
        updatedQaJson.put("subCategory", JSONObject.NULL);

        mockMvc.perform(put("/api/questions-answers/1")
               .content(updatedQaJson.toString())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetSubCategoryById_NotFound() throws Exception {
        when(subCategoryRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/questions-answers/subcategory/999"))
               .andExpect(status().isOk());
    }
}
