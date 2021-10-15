package com.petrov.service;

import com.petrov.controller.dto.OrderDto;
import com.petrov.persist.OrderRepository;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.UserRepository;
import com.petrov.persist.model.Order;
import com.petrov.persist.model.OrderLineItem;
import com.petrov.persist.model.Product;
import com.petrov.persist.model.User;
import com.petrov.service.dto.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final RabbitTemplate rabbitTemplate;

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            UserRepository userRepository,
                            ProductRepository productRepository,
                            RabbitTemplate rabbitTemplate,
                            SimpMessagingTemplate template) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.template = template;
    }

    public BigDecimal getOrderSum(Order order) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (OrderLineItem lineItem : order.getOrderLineItems()) {
            sum = sum.add(lineItem.getPrice());
        }
        return sum;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        List<OrderDto> orders = new ArrayList<>();
        for (Order order : orderRepository.findAllByUsername(username)) {
            orders.add(new OrderDto(
                    order.getId(),
                    order.getOrderDate().toString(),
                    getOrderSum(order),
                    order.getStatus().toString())
            );
        }
        return orders;
    }

    @Transactional
    public void createOrder(String username) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                .stream()
                .map(li -> new OrderLineItem(
                        null,
                        order,
                        findProductById(li.getProductId()),
                        li.getProductDto().getCost(),
                        li.getQty(),
                        li.getColor(),
                        li.getMaterial()
                ))
                .collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
        rabbitTemplate.convertAndSend(
                "order.exchange", "new_order",
                new OrderMessage(order.getId(), order.getStatus().name()));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id"));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @RabbitListener(queues = "processed.order.queue")
    public void receive(OrderMessage order) {
        logger.info("Order with id '{}' state change to '{}'", order.getId(), order.getState());
        template.convertAndSend("/order_out/order", order);
    }
}
