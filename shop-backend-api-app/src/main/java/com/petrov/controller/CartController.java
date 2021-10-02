package com.petrov.controller;

import com.petrov.controller.dto.AddLineItemDto;
import com.petrov.controller.dto.AllCartDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.service.CartService;
import com.petrov.service.ProductService;
import com.petrov.service.dto.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public List<LineItem> addToCart(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("New LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                .orElseThrow(RuntimeException::new);
        cartService.addProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(), addLineItemDto.getQty());
        return cartService.getLineItems();
    }

    @GetMapping("/all")
    public AllCartDto findAll() {
        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }


    @DeleteMapping(consumes = "application/json")
    public void deleteLineItem(@RequestBody LineItem lineItem) {
        cartService.removeProduct(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial());
    }

    @PostMapping(path = "/{qty}",produces = "application/json", consumes = "application/json")
    public void deleteLineItemQty(@RequestBody LineItem lineItem, @PathVariable Integer qty) {
        cartService.removeProductQty(lineItem.getProductDto(), lineItem.getColor(), lineItem.getMaterial(), qty);
    }

    @DeleteMapping("/clear_cart")
    public void clearCart(){
        cartService.clearCart();
    }
}
