package com.petrov.service;

import com.fasterxml.jackson.annotation.*;
import com.petrov.controller.dto.ProductDto;
import com.petrov.service.dto.LineItem;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartServiceImpl implements CartService {

    private final Map<LineItem, Integer> lineItems;

    public CartServiceImpl() {
        this.lineItems = new HashMap<>();
    }

    @JsonCreator
    public CartServiceImpl(@JsonProperty("lineItems") List<LineItem> lineItems) {
        this.lineItems = lineItems.stream().collect(Collectors.toMap(li -> li, LineItem::getQty));
    }

    @Override
    public void addProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    // поиск продукта в корзине
    @Override
    public LineItem findByProduct(LineItem lineItem) {
        for (LineItem item : getLineItems()) {
            if (item.getProductDto().getName().equals(lineItem.getProductDto().getName()) &&
                    item.getColor().equals(lineItem.getColor()) &&
                    item.getMaterial().equals(lineItem.getMaterial())) {
                return item;
            }
        }
        return null;
    }

    // изменение количества продуктов одного типа
    @Override
    public void removeProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        LineItem newLineItem = findByProduct(lineItem);
        lineItems.put(newLineItem, qty);
    }

    // удаление продукта из корзины
    @Override
    public void removeProduct(ProductDto productDto, String color, String material) {
        LineItem lineItem = new LineItem(productDto, color, material);
        LineItem aLineItem = findByProduct(lineItem);
        lineItems.remove(aLineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    @JsonIgnore
    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQty);
        return lineItems.keySet()
                .stream().map(LineItem::getItemTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // очистка корзины
    @Override
    public void clearCart() {
        lineItems.clear();
    }
}

