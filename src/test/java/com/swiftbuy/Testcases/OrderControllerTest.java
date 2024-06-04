package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.user.controller.OrderController;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.service.OrderService;
import com.swiftbuy.user.service.ShoppingCartService;

import io.jsonwebtoken.Claims;

import jakarta.servlet.http.HttpServletRequest;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private ShoppingCartService cartService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPlaceOrder_Success() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(cartService.getCartUserId(1L)).thenReturn(new ArrayList<>());
        when(orderService.createOrder(anyLong(), anyList())).thenReturn(new Order());

        ResponseEntity<Map<String, Object>> response = orderController.placeOrder(request);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetAllOrdersByUser_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        Page<Order> orders = new PageImpl<>(new ArrayList<>());
        when(orderService.getAllOrdersByUser(anyLong(), any())).thenReturn(orders);

        ResponseEntity<Map<String, Object>> response = orderController.getAllOrdersByUser(PageRequest.of(0, 10), request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testCancelOrder_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        doNothing().when(orderService).cancelOrder(anyLong(), anyLong());

        ResponseEntity<Map<String, Object>> response = orderController.cancelOrder(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetOrderById_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        Order order = new Order();
        when(orderService.getOrderById(anyLong(), anyLong())).thenReturn(order);

        ResponseEntity<Map<String, Object>> response = orderController.getOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetCancelledOrderById_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        CancellationDTO cancellation = new CancellationDTO();
        when(orderService.getCancelledOrder(anyLong(), anyLong())).thenReturn(cancellation);

        ResponseEntity<Map<String, Object>> response = orderController.getCancelledOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetDeliveredOrderById_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        DeliveredDTO delivered = new DeliveredDTO();
        when(orderService.getDeliveredOrder(anyLong(), anyLong())).thenReturn(delivered);

        ResponseEntity<Map<String, Object>> response = orderController.getDeliveredOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetCancelledOrders_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        Page<CancellationDTO> cancelledOrders = new PageImpl<>(new ArrayList<>());
        when(orderService.getAllCancelledOrders(anyLong(), any())).thenReturn(cancelledOrders);

        ResponseEntity<Map<String, Object>> response = orderController.getCancelledOrders(PageRequest.of(0, 10), request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetDeliveredOrders_Success() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        Page<DeliveredDTO> deliveredOrders = new PageImpl<>(new ArrayList<>());
        when(orderService.getAllDeliveredOrders(anyLong(), any())).thenReturn(deliveredOrders);

        ResponseEntity<Map<String, Object>> response = orderController.getDeliveredOrders(PageRequest.of(0, 10), request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((Boolean) response.getBody().get("status"));
    }

    // Negative Test Cases

    @Test
    void testPlaceOrder_Failure() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(cartService.getCartUserId(1L)).thenReturn(new ArrayList<>());
        when(orderService.createOrder(anyLong(), anyList())).thenThrow(new RuntimeException("Order creation failed"));

        ResponseEntity<Map<String, Object>> response = orderController.placeOrder(request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }



    @Test
    void testCancelOrder_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        doThrow(new RuntimeException("Cancellation failed")).when(orderService).cancelOrder(anyLong(), anyLong());

        ResponseEntity<Map<String, Object>> response = orderController.cancelOrder(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetOrderById_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(orderService.getOrderById(anyLong(), anyLong())).thenThrow(new RuntimeException("Order not found"));

        ResponseEntity<Map<String, Object>> response = orderController.getOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetCancelledOrderById_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(orderService.getCancelledOrder(anyLong(), anyLong())).thenThrow(new RuntimeException("Cancelled order not found"));

        ResponseEntity<Map<String, Object>> response = orderController.getCancelledOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetDeliveredOrderById_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(orderService.getDeliveredOrder(anyLong(), anyLong())).thenThrow(new RuntimeException("Delivered order not found"));

        ResponseEntity<Map<String, Object>> response = orderController.getDeliveredOrderById(1L, request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetCancelledOrders_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(orderService.getAllCancelledOrders(anyLong(), any())).thenThrow(new RuntimeException("Failed to fetch cancelled orders"));

        ResponseEntity<Map<String, Object>> response = orderController.getCancelledOrders(PageRequest.of(0, 10), request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }

    @Test
    void testGetDeliveredOrders_Failure() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        Claims claims = mock(Claims.class);
        when(request.getAttribute("claims")).thenReturn(claims);
        when(claims.get("userId", String.class)).thenReturn("1");

        when(orderService.getAllDeliveredOrders(anyLong(), any())).thenThrow(new RuntimeException("Failed to fetch delivered orders"));

        ResponseEntity<Map<String, Object>> response = orderController.getDeliveredOrders(PageRequest.of(0, 10), request);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse((Boolean) response.getBody().get("status"));
    }
}
