package com.petrov.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;


@JsonPropertyOrder({"id", "orderDate", "price", "status"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private Long id;

    private String orderDate;

    @JsonDeserialize(as = BigDecimal.class)
    private BigDecimal price;

    private String status;

    public OrderDto(Long id, String dateTime, BigDecimal totalPrice, String status) {
        this.id = id;
        this.orderDate = dateTime;
        this.price = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
