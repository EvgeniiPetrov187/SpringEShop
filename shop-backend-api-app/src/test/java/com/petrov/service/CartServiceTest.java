package com.petrov.service;

import com.petrov.controller.dto.ProductDto;
import com.petrov.service.dto.LineItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImpl();
    }

    @Test
    public void testIfNewCartIsEmpty() {
        assertNotNull(cartService.getLineItems());
        assertEquals(0, cartService.getLineItems().size());
        assertEquals(BigDecimal.ZERO, cartService.getSubTotal());
    }

    @Test
    public void testAddProduct() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(123));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);

        List<LineItem> lineItems = cartService.getLineItems();
        assertNotNull(lineItems);
        assertEquals(1, lineItems.size());

        LineItem lineItem = lineItems.get(0);
        assertEquals("color", lineItem.getColor());
        assertEquals("material", lineItem.getMaterial());
        assertEquals(2, lineItem.getQty());

        assertEquals(expectedProduct.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductDto());
        assertEquals(expectedProduct.getName(), lineItem.getProductDto().getName());
    }

    //тест удаления  продукта
    @Test
    public void testRemoveProduct(){
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(100));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 3);
        List<LineItem> lineItems = cartService.getLineItems();
        LineItem lineItem = lineItems.get(0);

        cartService.removeProduct(expectedProduct, lineItem.getColor(), lineItem.getMaterial());

        assertEquals(0,cartService.getLineItems().size());
        assertTrue(cartService.getLineItems().isEmpty());
    }

    //тест изменение количества продуктов одного типа
    @Test
    public void testRemoveProductQty(){
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(100));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "color", "material", 2);
        List<LineItem> lineItems = cartService.getLineItems();
        LineItem lineItem = lineItems.get(0);

        cartService.removeProductQty(expectedProduct, lineItem.getColor(), lineItem.getMaterial(), 1);

        assertEquals(1,cartService.getLineItems().get(0).getQty());
        assertEquals(1, cartService.getLineItems().size());
    }

    // тест очисики корзины
    @Test
    public void testClearCart() {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setId(1L);
        expectedProduct.setCost(new BigDecimal(100));
        expectedProduct.setName("Product name");

        cartService.addProductQty(expectedProduct, "red", "silk", 2);
        cartService.addProductQty(expectedProduct, "blue", "steel", 1);
        cartService.addProductQty(expectedProduct, "green", "plastic", 5);

        cartService.clearCart();

        assertEquals(0, cartService.getLineItems().size());
        assertTrue(cartService.getLineItems().isEmpty());
    }
}
