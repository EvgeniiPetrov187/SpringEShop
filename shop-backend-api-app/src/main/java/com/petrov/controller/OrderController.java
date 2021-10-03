package com.petrov.controller;

import com.petrov.controller.dto.OrderDto;
import com.petrov.persist.model.Order;
import com.petrov.service.CartService;
import com.petrov.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/all")
    public Authentication findAll(Authentication auth) {
        orderService.findAll();
        return auth;
    }

    @GetMapping("/{userId}/user")
    public OrderDto findOrder(@PathVariable("userId") Long id) {
        return orderService.findById(id).get();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public void saveOrder(@RequestBody OrderDto order) {
        OrderDto newOrder = new OrderDto(order.getId(),
                order.getDateTime(),
                cartService.getSubTotal(),
                order.getStatus());
        orderService.addOrder(newOrder);
    }
}