package com.petrov.persist.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


// заказ
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private String dateTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    private String status;

    public Order(Long id, String dateTime, BigDecimal totalPrice, String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order(String dateTime, BigDecimal totalPrice, String status) {
        this.dateTime = dateTime;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Order() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}



