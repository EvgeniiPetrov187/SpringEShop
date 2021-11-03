package com.petrov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrov.controller.dto.AllCartDto;
import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;
import com.petrov.service.CartService;
import com.petrov.service.ProductService;
import com.petrov.service.dto.LineItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @Test
    public void testCartFunctionality() throws Exception {
        CategoryDto category = new CategoryDto(null, "Category");
        BrandDto brand = new BrandDto(null, "Brand");
        ProductDto product = new ProductDto(1L, "Product", new BigDecimal(200), "Description", category, brand, new ArrayList<>());

        cartService.addProductQty(product, "black", "metal", 2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cart/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].subTotal", is(cartService.getSubTotal())));
    // спросить на уроке
    
    }
}
