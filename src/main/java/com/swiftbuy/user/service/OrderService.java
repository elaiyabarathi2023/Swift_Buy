package com.swiftbuy.user.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.UserRepository;

@Service
public class OrderService {
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    OrderUtil orderutil;

    public Order createOrder(Long userId) throws Exception {
        // Fetch the cart details
        List<ShoppingCart> cartItems = cartService.getCartUserId(userId);

        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("Cart is empty");
        }

        Map<String, Double> priceAndDiscount = cartService.calculateTotalPrice(cartItems);

        // Create a new Order object
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(priceAndDiscount.get("totalPrice"));
        order.setTotalCouponDiscount(priceAndDiscount.get("totalCouponDiscount"));
        order.setOrderedDate(LocalDate.now());

        // Add each cart item to the order
        for (ShoppingCart cartItem : cartItems) {
            ProductDetails product = cartItem.getProduct();

            // Check if product is out of stock
            if (product.getProductQuantity() == 0) {
                throw new Exception("Product " + product.getProductId() + " is out of stock");
            }

            // Check if product quantity is less than the quantity in cart
            if (product.getProductQuantity() < cartItem.getQuantity()) {
                throw new Exception(
                        "Product " + product.getProductId() + " quantity is less than the quantity you provided");
            }

            // Subtract product quantity based on the quantity in order
            product.setProductQuantity(product.getProductQuantity() - cartItem.getQuantity());
            AddressDetails addressId = cartService.getAddressForUserByUserId(userId);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getProductPrice() * cartItem.getQuantity());
            orderItem.setCoupondiscountId(cartItem.getSelectedCouponId());
            order.setTotalOfferDiscount(priceAndDiscount.get("totalOfferDiscount"));
            orderItem.setAddress(addressId);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart items for the user
        cartService.clearCart(userId);

        return savedOrder;
    }

    public Page<Order> getAllOrdersByUser(Long userId, Pageable pageable) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findByUserId(userId, pageable);
    }
   
    public Order getOrderById(Long orderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void cancelOrder(Long orderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Update the order status to CANCELLED
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        order.setCancelledDate(LocalDate.now());
        // Restore the product quantities
        for (OrderItem orderItem : order.getOrderItems()) {
            ProductDetails product = orderItem.getProduct();
            product.setProductQuantity(product.getProductQuantity() + orderItem.getQuantity());
        }

        // Save the updated order and product quantities
        orderRepository.save(order);
    }

    public Page<CancellationDTO> getAllCancelledOrders(Long userId, Pageable pageable) {
    	   // Check if the user exists
    	   userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    	   // Get a Page of cancelled Orders for the given userId and pageable
    	   Page<Order> cancelledOrders = orderRepository.findByUserIdAndOrderStatusEquals(userId, Order.OrderStatus.CANCELLED, pageable);

    	   // Convert each Order object to a CancellationDTO object using the orderutil.convertOrderToCancellationDTO method
    	   return cancelledOrders.map(order -> orderutil.convertOrderToCancellationDTO(order));
    	}
    public CancellationDTO getCancelledOrder(Long orderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = orderRepository.findByOrderIdAndUserIdAndOrderStatusEquals(orderId, userId, Order.OrderStatus.CANCELLED);
        return orderutil.convertOrderToCancellationDTO(order);
    }

    public Page<DeliveredDTO> getAllDeliveredOrders(Long userId, Pageable pageable) {
        // Check if the user exists
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Get a Page of delivered Orders for the given userId and pageable
        Page<Order> deliveredOrders = orderRepository.findByUserIdAndOrderStatus(userId, Order.OrderStatus.DELIVERED, pageable);

        // Convert each Order object to a DeliveredDTO object using the orderutil.convertOrderToDeliveredDTO method
        return deliveredOrders.map(order -> orderutil.convertOrderToDeliveredDTO(order));
    }

    public DeliveredDTO getDeliveredOrder(Long orderId, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = orderRepository.findByOrderIdAndUserIdAndOrderStatus(orderId, userId, Order.OrderStatus.DELIVERED);
        return orderutil.convertOrderToDeliveredDTO(order);
    }
}