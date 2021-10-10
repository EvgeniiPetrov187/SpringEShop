package com.petrov.controller;

import com.petrov.controller.dto.OrderDto;
import com.petrov.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<OrderDto> findAll(Authentication auth) {
        return orderService.findOrdersByUsername(auth.getName());
    }

    @PostMapping
    public void createOrder(Authentication auth) {
        orderService.createOrder(auth.getName());
    }

    @DeleteMapping(consumes = "application/json")
    public void deleteOrder(@RequestBody OrderDto order) {
        orderService.deleteOrder(order.getId());
    }
}