package com.swiftbuy.admin.controller;

import java.time.LocalDateTime;
import java.util.List;

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
	    public ResponseEntity<String> markOrderAsShipped(@PathVariable Long orderId) {
	        try {
	            Order order = adminorderService.markOrderAsShipped(orderId);
	            LocalDateTime shippedDate = order.getShippedDate();
	            String message = "Order shipped successfully on date: " + shippedDate.toLocalDate() + " at time: " + shippedDate.toLocalTime();
	            return ResponseEntity.ok(message);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
	        } catch (IllegalStateException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	        }
	    }

	    @PutMapping("/{orderId}/delivered")
	    public ResponseEntity<String> markOrderAsDelivered(@PathVariable Long orderId) {
	        try {
	            Order order = adminorderService.markOrderAsDelivered(orderId);
	            LocalDateTime deliveredDate = order.getDeliveredDate();
	           
	            String message = "Order delivered successfully on date: "+deliveredDate.toLocalDate() ;
	            return ResponseEntity.ok(message);
	        } catch (ResourceNotFoundException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
	        } catch (IllegalStateException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured");
	        }
	    }
	    @GetMapping("/shipped")
	    public ResponseEntity<Page<ShippingDTO>> getAllShippedOrders(@PageableDefault(size = 10) Pageable pageable) {
	        Page<ShippingDTO> shippedOrders = adminorderService.getAllShippedOrders(pageable);
	        return ResponseEntity.ok(shippedOrders);
	    }

	    @GetMapping("/delivered")
	    public ResponseEntity<Page<DeliveredDTO>> getAllDeliveredOrders(@PageableDefault(size = 10) Pageable pageable) {
	        Page<DeliveredDTO> deliveredOrders = adminorderService.getAllDeliveredOrders(pageable);
	        return ResponseEntity.ok(deliveredOrders);
	    }

	    @GetMapping("/cancelled")
	    public ResponseEntity<Page<CancellationDTO>> getAllCancelledOrders(@PageableDefault(size = 10) Pageable pageable) {
	        Page<CancellationDTO> cancelledOrders = adminorderService.getAllCancelledOrders(pageable);
	        return ResponseEntity.ok(cancelledOrders);
	    }
}
