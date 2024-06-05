package com.swiftbuy.admin.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ShippingDTO;
import com.swiftbuy.admin.service.AdminOrderService;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;

@RestController
@RequestMapping("/orders")
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminorderService;

    @PutMapping("/{orderId}/shipped")
    public ResponseEntity<Map<String, Object>> markOrderAsShipped(@PathVariable Long orderId) {
        try {
            Order order = adminorderService.markOrderAsShipped(orderId);
            LocalDateTime shippedDate = order.getShippedDate();
            String message = "Order shipped successfully on date: " + shippedDate.toLocalDate() + " at time: "
                    + shippedDate.toLocalTime();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", true);
            response.put("message", message);

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("Cannot mark a cancelled order as shipped")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", false);
                errorResponse.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            } 
            
            else {
            
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<Map<String, Object>> markOrderAsDelivered(@PathVariable Long orderId) {
        try {
            Order order = adminorderService.markOrderAsDelivered(orderId);
            LocalDateTime deliveredDate = order.getDeliveredDate();
            String message = "Order delivered successfully on date: " + deliveredDate.toLocalDate();

            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("message", message);

            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            if (e.getMessage().equals("Cannot mark a cancelled order as delivered")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", false);
                errorResponse.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            } else {
                // Handle other exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @GetMapping("/shipped")
    public ResponseEntity<Map<String, Object>> getAllShippedOrders(@PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<ShippingDTO> shippedOrders = adminorderService.getAllShippedOrders(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("data", shippedOrders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "An error occurred while fetching shipped orders");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/delivered")
    public ResponseEntity<Map<String, Object>> getAllDeliveredOrders(@PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<DeliveredDTO> deliveredOrders = adminorderService.getAllDeliveredOrders(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("data", deliveredOrders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "An error occurred while fetching delivered orders");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/cancelled")
    public ResponseEntity<Map<String, Object>> getAllCancelledOrders(@PageableDefault(size = 10) Pageable pageable) {
        try {
            Page<CancellationDTO> cancelledOrders = adminorderService.getAllCancelledOrders(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("data", cancelledOrders);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("error", "An error occurred while fetching cancelled orders");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}