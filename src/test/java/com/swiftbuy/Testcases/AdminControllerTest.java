package com.swiftbuy.Testcases;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.JsonExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ShippingDTO;
import com.swiftbuy.admin.service.AdminOrderService;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @MockBean
    private AdminOrderService adminOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Order mockOrder;
    private LocalDateTime now;
    private LocalDate now1;

    @BeforeEach
    public void setUp() {
        now = LocalDateTime.now();
        now1=LocalDate.now();
        mockOrder = new Order();
        mockOrder.setOrderId(1L);
        mockOrder.setShippedDate(now);
        mockOrder.setDeliveredDate(now.plusDays(2));
        mockOrder.setCancelledDate(now1);
    }

    @Test
    public void testMarkOrderAsShipped() throws Exception {
        when(adminOrderService.markOrderAsShipped(1L)).thenReturn(mockOrder);

        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": true, \"message\": \"Order shipped successfully on date: " + now.toLocalDate() + " at time: " + now.toLocalTime() + "\"}"));
    }

    @Test
    public void testMarkOrderAsShipped_OrderNotFound() throws Exception {
        when(adminOrderService.markOrderAsShipped(1L)).thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\": false, \"error\": \"Order not found\"}"));
    }

    @Test
    public void testMarkOrderAsDelivered() throws Exception {
        when(adminOrderService.markOrderAsDelivered(1L)).thenReturn(mockOrder);

        mockMvc.perform(put("/orders/1/delivered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": true, \"message\": \"Order delivered successfully on date: " + now.plusDays(2).toLocalDate() + "\"}"));
    }

    @Test
    public void testMarkOrderAsDelivered_OrderNotFound() throws Exception {
        when(adminOrderService.markOrderAsDelivered(1L)).thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(put("/orders/1/delivered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\": false, \"error\": \"Order not found\"}"));
    }

    @Test
    public void testGetAllShippedOrders() throws Exception {
        List<ShippingDTO> shippedOrders = new ArrayList<>();
        ShippingDTO shippedDTO1 = new ShippingDTO();
        shippedDTO1.setOrderId(1L);
        shippedDTO1.setShippedDate(now);
        // Set other properties of deliveredDTO1 if needed

        ShippingDTO shippedDTO2 = new ShippingDTO();
        shippedDTO2.setOrderId(2L);
        shippedDTO2.setShippedDate(now.plusDays(1));
        // Set other properties of deliveredDTO2 if needed

        shippedOrders.add(shippedDTO1);
        shippedOrders.add(shippedDTO2);

        PageImpl<ShippingDTO> page = new PageImpl<>(shippedOrders, PageRequest.of(0, 10), shippedOrders.size());

        when(adminOrderService.getAllShippedOrders(any(PageRequest.class))).thenReturn(page);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("status", true);
        expectedResponse.put("data", page);

        MvcResult cartResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/shipped"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }


    @Test
    public void testGetAllDeliveredOrders() throws Exception {
        List<DeliveredDTO> deliveredOrders = new ArrayList<>();
        DeliveredDTO deliveredDTO1 = new DeliveredDTO();
        deliveredDTO1.setOrderId(1L);
        deliveredDTO1.setDeliveredDate(now);
        // Set other properties of deliveredDTO1 if needed

        DeliveredDTO deliveredDTO2 = new DeliveredDTO();
        deliveredDTO2.setOrderId(2L);
        deliveredDTO2.setDeliveredDate(now.plusDays(1));
        // Set other properties of deliveredDTO2 if needed

        deliveredOrders.add(deliveredDTO1);
        deliveredOrders.add(deliveredDTO2);

        PageImpl<DeliveredDTO> page = new PageImpl<>(deliveredOrders, PageRequest.of(0, 10), deliveredOrders.size());

        when(adminOrderService.getAllDeliveredOrders(any(PageRequest.class))).thenReturn(page);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("status", true);
        expectedResponse.put("data", page);

        MvcResult cartResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/delivered"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }



    @Test
    public void testGetAllCancelledOrders() throws Exception {
        List<CancellationDTO> cancelledOrders = new ArrayList<>();
        CancellationDTO cancelDTO1 = new CancellationDTO();
        cancelDTO1.setOrderId(1L);
        cancelDTO1.setCancelledDate(now1);
        // Set other properties of deliveredDTO1 if needed

        CancellationDTO cancelDTO2 = new CancellationDTO();
        cancelDTO2.setOrderId(2L);
        cancelDTO2.setCancelledDate(now1.plusDays(1));
        // Set other properties of deliveredDTO2 if needed

        cancelledOrders.add(cancelDTO1);
        cancelledOrders.add(cancelDTO2);

        PageImpl<CancellationDTO> page = new PageImpl<>(cancelledOrders, PageRequest.of(0, 10), cancelledOrders.size());

        when(adminOrderService.getAllCancelledOrders(any(PageRequest.class))).thenReturn(page);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("status", true);
        expectedResponse.put("data", page);

        MvcResult cartResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/cancelled"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
    @Test
    public void testGetAllShippedOrders_NoOrdersFound() throws Exception {
    	List<ShippingDTO> shippedOrders = new ArrayList<>();
        ShippingDTO shippedDTO1 = new ShippingDTO();
       
        shippedDTO1.setShippedDate(now);
        // Set other properties of deliveredDTO1 if needed

        ShippingDTO shippedDTO2 = new ShippingDTO();
      
        shippedDTO2.setShippedDate(now.plusDays(1));
        // Set other properties of deliveredDTO2 if needed

       

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("status", true);
      

        MvcResult cartResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/orders/shipped"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
    }

    @Test
    public void testGetAllDeliveredOrders_NoOrdersFound() throws Exception {
        PageImpl<DeliveredDTO> emptyPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 10), 0);
        when(adminOrderService.getAllDeliveredOrders(any(PageRequest.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/orders/delivered"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": true, \"data\": []}"));
    }
    @Test
    public void testGetAllCancelledOrders_NoOrdersFound() throws Exception {
        PageImpl<CancellationDTO> emptyPage = new PageImpl<>(new ArrayList<>(), PageRequest.of(0, 10), 0);
        when(adminOrderService.getAllCancelledOrders(any(PageRequest.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/orders/cancelled"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\": true, \"data\": []}"));
    }
}