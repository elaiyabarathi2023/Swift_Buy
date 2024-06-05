package com.swiftbuy.Testcases;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
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
import com.swiftbuy.user.model.Order.OrderStatus;
import com.swiftbuy.user.repository.OrderRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private AdminOrderService adminOrderService;

    @MockBean
    private OrderRepository adminOrderRepo;

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
        now1 = LocalDate.now();
        mockOrder = new Order();
        mockOrder.setOrderId(1L);
        when(adminOrderRepo.findById(1L)).thenReturn(Optional.of(mockOrder));
        mockOrder.setShippedDate(now);
        mockOrder.setDeliveredDate(now.plusDays(2));
        mockOrder.setCancelledDate(now1);
        mockOrder.setOrderStatus(Order.OrderStatus.PLACED);  // Use the enum here

    }

    @Test
    public void testMarkOrderAsShipped() throws Exception {
        mockOrder.setOrderStatus(Order.OrderStatus.SHIPPED);  // Update status for this test
        when(adminOrderService.markOrderAsShipped(1L)).thenReturn(mockOrder);

        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testMarkOrderAsShipped_OrderNotFound() throws Exception {
        when(adminOrderService.markOrderAsShipped(1L)).thenThrow(new ResourceNotFoundException("Order not found"));

        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
                
    }

    @Test
    public void testMarkOrderAsShipped_OrderCancelled() throws Exception {
        // Arrange: Set order status to CANCELLED
        mockOrder.setOrderStatus(Order.OrderStatus.CANCELLED);
        // Act and Assert: Expect an internal server error
        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testMarkOrderAsShipped_OrderNotPlaced() throws Exception {
        mockOrder.setOrderStatus(Order.OrderStatus.SHIPPED);  // Set status to SHIPPED for this test
        when(adminOrderService.markOrderAsShipped(1L)).thenThrow(new IllegalStateException("Cannot mark an order as shipped that is not in PLACED status"));

        mockMvc.perform(put("/orders/1/shipped")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
               
    }
    @Test
    public void testMarkOrderAsDelivered() throws Exception {
      
      
        mockOrder.setOrderStatus(Order.OrderStatus.DELIVERED);

        // Now mark the order as delivered
        when(adminOrderService.markOrderAsDelivered(1L)).thenReturn(mockOrder);
        mockMvc.perform(put("/orders/1/delivered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Add additional assertions or logging as needed
    }

    @Test
    public void testMarkOrderAsDelivered_OrderNotFound() throws Exception {
    	mockOrder.setOrderStatus(Order.OrderStatus.DELIVERED);  // Set status to SHIPPED for this test
        when(adminOrderService.markOrderAsDelivered(1L)).thenThrow(new IllegalStateException("Cannot mark an order as shipped that is not in PLACED status"));

        mockMvc.perform(put("/orders/1/delivered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
               
                
    }

    @Test
    public void testMarkOrderAsDelivered_OrderCancelled() throws Exception {
        mockOrder.setOrderStatus(Order.OrderStatus.CANCELLED);  // Set status to CANCELLED for this test

        Map<String, Object> expectedErrorResponse = new HashMap<>();
        expectedErrorResponse.put("status", false);
        expectedErrorResponse.put("error", "Cannot mark a cancelled order as delivered");

        mockMvc.perform(put("/orders/1/delivered")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedErrorResponse)));
    }


    @Test
	public void testGetAllShippedOrders() throws Exception {
		List<Order> shippedOrders = new ArrayList<>();
		shippedOrders.add(mockOrder);
		Page<Order> shippedOrdersPage = new PageImpl<>(shippedOrders);
		when(adminOrderRepo.findByOrderStatusAndShippedDateBetween(eq(OrderStatus.SHIPPED), any(LocalDateTime.class),
				any(LocalDateTime.class), any(Pageable.class))).thenReturn(shippedOrdersPage);

		mockMvc.perform(get("/orders/shipped")).andExpect(status().isOk());
	}

    @Test
	public void testGetAllDeliveredOrders() throws Exception {
		List<Order> deliveredOrders = new ArrayList<>();
		deliveredOrders.add(mockOrder);
		Page<Order> deliveredOrdersPage = new PageImpl<>(deliveredOrders);
		when(adminOrderRepo.findByOrderStatusAndDeliveredDateBetween(eq(OrderStatus.DELIVERED),
				any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
				.thenReturn(deliveredOrdersPage);

		mockMvc.perform(get("/orders/delivered")).andExpect(status().isOk());
	}

	@Test
	public void testGetAllCancelledOrders() throws Exception {
		List<Order> cancelledOrders = new ArrayList<>();
		cancelledOrders.add(mockOrder);
		Page<Order> cancelledOrdersPage = new PageImpl<>(cancelledOrders);
		when(adminOrderRepo.findByOrderStatusEquals(eq(OrderStatus.CANCELLED), any(Pageable.class)))
				.thenReturn(cancelledOrdersPage);

		mockMvc.perform(get("/orders/cancelled")).andExpect(status().isOk());
	}
    @Test
    public void testGetAllShippedOrders_NoOrdersFound() throws Exception {
    	List<Order> shippedOrders = new ArrayList<>();
		shippedOrders.add(null);
		Page<Order> shippedOrdersPage = new PageImpl<>(shippedOrders);
		when(adminOrderRepo.findByOrderStatusAndShippedDateBetween(eq(OrderStatus.SHIPPED), any(LocalDateTime.class),
				any(LocalDateTime.class), any(Pageable.class))).thenReturn(shippedOrdersPage);

		mockMvc.perform(get("/orders/shipped")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetAllDeliveredOrders_NoOrdersFound() throws Exception {
    	List<Order> deliveredOrders = new ArrayList<>();
		deliveredOrders.add(null);
		Page<Order> deliveredOrdersPage = new PageImpl<>(deliveredOrders);
		when(adminOrderRepo.findByOrderStatusAndDeliveredDateBetween(eq(OrderStatus.DELIVERED),
				any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
				.thenReturn(deliveredOrdersPage);

		mockMvc.perform(get("/orders/delivered")).andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetAllCancelledOrders_NoOrdersFound() throws Exception {
    	List<Order> cancelledOrders = new ArrayList<>();
		cancelledOrders.add(null);
		Page<Order> cancelledOrdersPage = new PageImpl<>(cancelledOrders);
		when(adminOrderRepo.findByOrderStatusEquals(eq(OrderStatus.CANCELLED), any(Pageable.class)))
				.thenReturn(cancelledOrdersPage);

		mockMvc.perform(get("/orders/cancelled")).andExpect(status().isInternalServerError());
    }
}