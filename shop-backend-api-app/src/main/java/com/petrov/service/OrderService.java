package com.petrov.service;

import com.petrov.controller.dto.OrderDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.persist.model.Order;
import com.petrov.service.dto.LineItem;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> findAll();

    Optional<OrderDto> findById(Long id);

    void addOrder(OrderDto order);
}
