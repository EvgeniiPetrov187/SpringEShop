package com.petrov.controller.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;


@JsonPropertyOrder({"id","dateTime","totalPrice","status"})
public class OrderDto {

    private Long id;

    private String dateTime;

    @JsonDeserialize(as = BigDecimal.class)
    private BigDecimal totalPrice;

    private String status;

    public OrderDto(Long id, String dateTime, BigDecimal totalPrice, String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
