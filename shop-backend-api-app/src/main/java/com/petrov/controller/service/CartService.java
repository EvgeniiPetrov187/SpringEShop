package com.petrov.controller.service;

import com.petrov.controller.dto.ProductDto;
import com.petrov.controller.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProduct(ProductDto productDto, String color, String material);

    void clearCart();

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    LineItem findByProduct(ProductDto productDto);
}
