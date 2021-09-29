package com.petrov.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.petrov.controller.dto.OrderDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.persist.OrderRepository;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.UserRepository;
import com.petrov.persist.model.Order;
import com.petrov.persist.model.Product;
import com.petrov.persist.model.User;
import com.petrov.service.dto.LineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream().map(order -> new OrderDto(
                order.getId(),
                order.getDateTime(),
                order.getTotalPrice(),
                order.getStatus())).collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findById(Long id) {
        return orderRepository.findById(id).map(order -> new OrderDto(
                order.getId(),
                order.getDateTime(),
                order.getTotalPrice(),
                order.getStatus()));
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        Order order = new Order(orderDto.getId(),
                new Date().toString(),
                orderDto.getTotalPrice(),
                orderDto.getStatus());
        orderRepository.save(order);
    }
}
