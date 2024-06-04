package com.swiftbuy.Testcases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.Order.OrderStatus;
import com.swiftbuy.user.repository.OrderRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
	@MockBean
	private OrderRepository orderRepository;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private Order order;

	@BeforeEach
	public void setUpMocks() {
		order.setOrderId(1L);
		order.setUserId(1L);
		order.setOrderStatus(OrderStatus.DELIVERED);
		order.setShippedDate(LocalDateTime.now());
		order.setDeliveredDate(LocalDateTime.now().plusDays(2));
	}

	@Test
	public void testMarkOrderAsShipped() throws Exception {
		when(orderRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		mockMvc.perform(put("/orders/{orderId}/shipped", 1L)).andExpect(status().isNotFound())
				.andExpect(content().json("{\"error\":\"Order not found\",\"status\":false}"));
	}

	@Test
	public void testMarkOrderAsShipped_OrderNotFound() throws Exception {
		when(orderRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		mockMvc.perform(put("/orders/{orderId}/shipped", 1L)).andExpect(status().isNotFound())
				.andExpect(content().json("{\"error\":\"Order not found\",\"status\":false}"));
	}

	@Test
	public void testMarkOrderAsDelivered_OrderNotFound() throws Exception {
		when(orderRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		mockMvc.perform(put("/orders/{orderId}/delivered", 1L)).andExpect(status().isNotFound())
				.andExpect(content().json("{\"error\":\"Order not found\",\"status\":false}"));
	}

	@Test
	public void testGetAllShippedOrders() throws Exception {
		List<Order> shippedOrders = new ArrayList<>();
		shippedOrders.add(order);
		Page<Order> shippedOrdersPage = new PageImpl<>(shippedOrders);
		when(orderRepository.findByOrderStatusAndShippedDateBetween(eq(OrderStatus.SHIPPED), any(LocalDateTime.class),
				any(LocalDateTime.class), any(Pageable.class))).thenReturn(shippedOrdersPage);

		mockMvc.perform(get("/orders/shipped")).andExpect(status().isOk());
	}
	@Test
	public void testMarkCancelledOrderAsShipped() throws Exception {
	    when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));
	    order.setOrderStatus(OrderStatus.CANCELLED);

	    mockMvc.perform(put("/orders/{orderId}/shipped", 1L))
	            .andExpect(status().isInternalServerError()) // The expected status should match the behavior of your application
	            .andExpect(content().json("{\"error\":\"Cannot mark a cancelled order as shipped\",\"status\":false}"));
	}

	@Test
	public void testGetAllDeliveredOrders() throws Exception {
		List<Order> deliveredOrders = new ArrayList<>();
		deliveredOrders.add(order);
		Page<Order> deliveredOrdersPage = new PageImpl<>(deliveredOrders);
		when(orderRepository.findByOrderStatusAndDeliveredDateBetween(eq(OrderStatus.DELIVERED),
				any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
				.thenReturn(deliveredOrdersPage);

		mockMvc.perform(get("/orders/delivered")).andExpect(status().isOk());
	}

	@Test
	public void testGetAllCancelledOrders() throws Exception {
		List<Order> cancelledOrders = new ArrayList<>();
		cancelledOrders.add(order);
		Page<Order> cancelledOrdersPage = new PageImpl<>(cancelledOrders);
		when(orderRepository.findByOrderStatusEquals(eq(OrderStatus.CANCELLED), any(Pageable.class)))
				.thenReturn(cancelledOrdersPage);

		mockMvc.perform(get("/orders/cancelled")).andExpect(status().isOk());
	}
}