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
        order.setTotalDiscount(priceAndDiscount.get("totalDiscount"));

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

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getProductPrice() * cartItem.getQuantity());
            orderItem.setDiscountId(cartItem.getSelectedCouponId());
            orderItem.setOrder(order); // Set the order for the order item
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
    

}