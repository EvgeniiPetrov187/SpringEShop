package com.petrov.controller.service;

import com.petrov.controller.dto.ProductDto;
import com.petrov.controller.service.dto.LineItem;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final Map<LineItem, Integer> lineItems = new HashMap<>();

    @Override
    public void addProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    // поиск продукта в корзине
    @Override
    public LineItem findByProduct(ProductDto productDto) {
        for (LineItem item : getLineItems()) {
            if (item.getProductDto().hashCode() == (productDto.hashCode())) {
                return item;
            }
        }
        return null;
    }

    // изменение количества продуктов одного типа
    @Override
    public void removeProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = findByProduct(productDto);
        lineItems.put(lineItem, qty);
    }

    // удаление продукта из корзины
    @Override
    public void removeProduct(ProductDto productDto, String color, String material) {
        LineItem lineItem = findByProduct(productDto);
        lineItems.remove(lineItem);
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

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

