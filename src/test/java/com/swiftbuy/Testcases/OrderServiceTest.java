package com.swiftbuy.Testcases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import com.swiftbuy.admin.model.DeliveredDTO;
import com.swiftbuy.admin.model.ProductDetails;
import com.swiftbuy.user.model.CancellationDTO;
import com.swiftbuy.user.model.Order;
import com.swiftbuy.user.model.OrderItem;
import com.swiftbuy.user.model.ShoppingCart;
import com.swiftbuy.user.model.UserDetails;
import com.swiftbuy.user.model.AccountManangement.AddressDetails;
import com.swiftbuy.user.repository.OrderRepository;
import com.swiftbuy.user.repository.UserRepository;
import com.swiftbuy.user.service.OrderService;
import com.swiftbuy.user.service.OrderUtil;
import com.swiftbuy.user.service.ShoppingCartService;
@SpringBootTest
class OrderServiceTest {

    @Mock
    private ShoppingCartService cartService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderUtil orderUtil;

    @InjectMocks
    private OrderService orderService;

    private Long userId = 1L;
    private Long orderId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() throws Exception {
        // Arrange
        List<ShoppingCart> cartItems = new ArrayList<>();
        ShoppingCart cartItem = new ShoppingCart();
        ProductDetails product = new ProductDetails();
        product.setProductQuantity(10);
        product.setProductPrice(10.0); // Set the product price to a non-null value
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItems.add(cartItem);

        when(cartService.calculateTotalPrice(cartItems)).thenReturn(
                Map.of("totalPrice", 100.0, "totalCouponDiscount", 10.0, "totalOfferDiscount", 5.0));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setOrderId(orderId);
            return order;
        });

        // Act
        Order savedOrder = orderService.createOrder(userId, cartItems);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(orderId, savedOrder.getOrderId());
        assertEquals(userId, savedOrder.getUserId());
        assertEquals(100.0, savedOrder.getTotalPrice());
        assertEquals(10.0, savedOrder.getTotalCouponDiscount());
        assertEquals(5.0, savedOrder.getTotalOfferDiscount());
        assertEquals(LocalDate.now(), savedOrder.getOrderedDate());
        assertEquals(1, savedOrder.getOrderItems().size());
        verify(cartService, times(1)).clearCart(userId);
    }
    @Test
    void testGetAllOrdersByUser() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        Page<Order> orderPage = new PageImpl<>(orders, pageable, orders.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findByUserId(userId, pageable)).thenReturn(orderPage);

        // Act
        Page<Order> result = orderService.getAllOrdersByUser(userId, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetOrderById() {
        // Arrange
        Order order = new Order();
        order.setOrderId(orderId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        Order result = orderService.getOrderById(orderId, userId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
    }

    @Test
    void testCancelOrder() {
        // Arrange
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(Order.OrderStatus.PLACED);

        OrderItem orderItem = new OrderItem();
        ProductDetails product = new ProductDetails();
        product.setProductQuantity(5);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);

        order.getOrderItems().add(orderItem);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Act
        orderService.cancelOrder(orderId, userId);

        // Assert
        assertEquals(Order.OrderStatus.CANCELLED, order.getOrderStatus());
        assertNotNull(order.getCancelledDate());
        assertEquals(7, orderItem.getProduct().getProductQuantity());
    }

    @Test
    void testGetAllCancelledOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> cancelledOrders = new ArrayList<>();
        Order order = new Order();
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        cancelledOrders.add(order);

        Page<Order> orderPage = new PageImpl<>(cancelledOrders, pageable, cancelledOrders.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findByUserIdAndOrderStatusEquals(userId, Order.OrderStatus.CANCELLED, pageable)).thenReturn(orderPage);
        when(orderUtil.convertOrderToCancellationDTO(order)).thenReturn(new CancellationDTO());

        // Act
        Page<CancellationDTO> result = orderService.getAllCancelledOrders(userId, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetCancelledOrder() {
        // Arrange
        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(Order.OrderStatus.CANCELLED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findByOrderIdAndUserIdAndOrderStatusEquals(orderId, userId, Order.OrderStatus.CANCELLED)).thenReturn(order);
        when(orderUtil.convertOrderToCancellationDTO(order)).thenReturn(new CancellationDTO());

        // Act
        CancellationDTO result = orderService.getCancelledOrder(orderId, userId);

        // Assert
        assertNotNull(result);
    }
    
    @Test
    public void testCancelOrder_OrderFound() {
       // Arrange
       Long orderId = 1L;
       Long userId = 1L;

       Order order = new Order();
       order.setOrderId(orderId);
       order.setOrderStatus(Order.OrderStatus.PLACED);

       OrderItem orderItem = new OrderItem();
       ProductDetails product = new ProductDetails();
       product.setProductQuantity(5);
       orderItem.setProduct(product);
       orderItem.setQuantity(2);
       order.getOrderItems().add(orderItem);

       when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
       when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

       // Act
       orderService.cancelOrder(orderId, userId);

       // Assert
       assertEquals(Order.OrderStatus.CANCELLED, order.getOrderStatus());
       assertNotNull(order.getCancelledDate());
       assertEquals(7, orderItem.getProduct().getProductQuantity());
    }
    @Test
    void testGetOrderFromOrderItem() {
        // Arrange
        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);

        // Act
        Order result = orderItem.getOrder();

        // Assert
        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        assertEquals(order.getUserId(), result.getUserId());
    }
    @Test
    void testGetAllDeliveredOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> deliveredOrders = new ArrayList<>();
        Order order = new Order();
        order.setOrderStatus(Order.OrderStatus.DELIVERED);
        deliveredOrders.add(order);

        Page<Order> orderPage = new PageImpl<>(deliveredOrders, pageable, deliveredOrders.size());

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findByUserIdAndOrderStatus(userId, Order.OrderStatus.DELIVERED, pageable)).thenReturn(orderPage);
        when(orderUtil.convertOrderToDeliveredDTO(order)).thenReturn(new DeliveredDTO());

        // Act
        Page<DeliveredDTO> result = orderService.getAllDeliveredOrders(userId, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
    @Test
    void testGetDeliveredOrder() {
        // Arrange
        Long orderId = 1L;
        Long userId = 1L;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(Order.OrderStatus.DELIVERED);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserDetails()));
        when(orderRepository.findByOrderIdAndUserIdAndOrderStatus(orderId, userId, Order.OrderStatus.DELIVERED)).thenReturn(order);
        when(orderUtil.convertOrderToDeliveredDTO(order)).thenReturn(new DeliveredDTO());

        // Act
        DeliveredDTO result = orderService.getDeliveredOrder(orderId, userId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testGetAddressFromOrderItem() {
        // Arrange
        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setId(1L);;
        addressDetails.setCity("New York");

        OrderItem orderItem = new OrderItem();
        orderItem.setAddress(addressDetails);

        // Act
        AddressDetails result = orderItem.getAddress();

        // Assert
        assertNotNull(result);
        assertEquals(addressDetails.getId(), result.getId());
        assertEquals(addressDetails.getCity(), result.getCity());
    }
    @Test
    void testGetOrderItemId() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        Long orderItemId = 123L;
        orderItem.setOrderItemId(orderItemId);

        // Act
        Long result = orderItem.getOrderItemId();

        // Assert
        assertNotNull(result);
        assertEquals(orderItemId, result);
    }
    @Test
    void testSetOrderItemId() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        Long orderItemId =3L;

        // Act
        orderItem.setOrderItemId(orderItemId);

        // Assert
        assertEquals(orderItemId, orderItem.getOrderItemId());
    }
    @Test
    void testGetPriceFromOrderItem() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        double price = 99.99;
        orderItem.setPrice(price);

        // Act
        double result = orderItem.getPrice();

        // Assert
        assertEquals(price, result);
    }

    @Test
    void testGetCoupondiscountIdFromOrderItem() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        Long coupondiscountId = 123L;
        orderItem.setCoupondiscountId(coupondiscountId);

        // Act
        Long result = orderItem.getCoupondiscountId();

        // Assert
        assertNotNull(result);
        assertEquals(coupondiscountId, result);
    }
    }
   
   

