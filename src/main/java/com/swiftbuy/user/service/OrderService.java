package com.swiftbuy.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.OrderItemRepository;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.ShoppingCartRepository;
import com.swiftbuy.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    
    public Order createOrder(Long userId) throws Exception {
        // Fetch the cart details
        List<ShoppingCart> cartItems = cartService.getCartUserId(userId);

        UserDetails user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("Cart is empty");
        }

        Map<String, Double> priceAndDiscount = cartService.calculateTotalPrice(cartItems);

        // Create a new Order object
        Order order = new Order();
        order.setUser(user); // Set the user in the Order object
        order.setTotalPrice(priceAndDiscount.get("totalPrice"));
        order.setTotalCouponDiscount(priceAndDiscount.get("totalCouponDiscount"));

        // Add each cart item to the order
        for (ShoppingCart cartItem : cartItems) {
            ProductDetails product = cartItem.getProduct();

            // Check if product is out of stock
            if (product.getProductQuantity() == 0) {
                throw new Exception("Product " + product.getProductId() + " is out of stock");
            }

            // Check if product quantity is less than the quantity in cart
            if (product.getProductQuantity() < cartItem.getQuantity()) {
                throw new Exception("Product " + product.getProductId() + " quantity is less than the quantity you provided");
            }

            // Subtract product quantity based on the quantity in order
            product.setProductQuantity(product.getProductQuantity() - cartItem.getQuantity());
            AddressDetails addressDetails = cartService.getAddressById(userId);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getProductPrice() * cartItem.getQuantity());
            orderItem.setCoupondiscountId(cartItem.getSelectedCouponId());
            order.setTotalOfferDiscount(priceAndDiscount.get("totalOfferDiscount"));
            orderItem.setAddress(addressDetails);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            
        }

        // Save the order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart items for the user
        cartService.clearCart(userId);

        return savedOrder;
    }

    public List<Order> getAllOrdersByUser(Long userId) {
        UserDetails user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findByUserUserId(userId);
    }
    public Order getOrderById(Long orderId,Long userId) {
    	  UserDetails user = userRepository.findById(userId)
                  .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public void cancelOrder(Long orderId,Long userId) {
    	 UserDetails user = userRepository.findById(userId)
                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getOrderStatus() != Order.OrderStatus.PLACED) {
            throw new IllegalStateException("Cannot cancel an order that is not in PLACED status");
        }

        // Update the order status to CANCELLED
        order.setOrderStatus(Order.OrderStatus.CANCELLED);

        // Restore the product quantities
        for (OrderItem orderItem : order.getOrderItems()) {
            ProductDetails product = orderItem.getProduct();
            product.setProductQuantity(product.getProductQuantity() + orderItem.getQuantity());
        }

        // Save the updated order and product quantities
        orderRepository.save(order);
    }
    public Order markOrderAsShipped(Long orderId,Long userId) {
    	  UserDetails user = userRepository.findById(userId)
                  .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark a cancelled order as shipped");
        }

        if (order.getOrderStatus() != Order.OrderStatus.PLACED) {
            throw new IllegalStateException("Cannot mark an order as shipped that is not in PLACED status");
        }

        order.setOrderStatus(Order.OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    public Order markOrderAsDelivered(Long orderId,Long userId) {
    	  UserDetails user = userRepository.findById(userId)
                  .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark a cancelled order as delivered");
        }

        if (order.getOrderStatus() != Order.OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot mark an order as delivered that is not in SHIPPED status");
        }

        order.setOrderStatus(Order.OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }
}



