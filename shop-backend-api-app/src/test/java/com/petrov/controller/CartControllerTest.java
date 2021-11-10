package com.petrov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrov.controller.dto.*;
import com.petrov.persist.BrandRepository;
import com.petrov.persist.CategoryRepository;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;
import com.petrov.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpMessagingTemplate template;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCartFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cart/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void addToCartTest() throws Exception {

        Category category = categoryRepository.save(new Category(null, "Category"));
        Brand brand = brandRepository.save(new Brand(null, "Brand"));
        Product product = productRepository.save(new Product(1L, "Product", new BigDecimal(1234), "Description", category, brand));

        AddLineItemDto addLineItemDto = new AddLineItemDto();
        addLineItemDto.setProductId(1L);
        addLineItemDto.setColor("black");
        addLineItemDto.setMaterial("metal");
        addLineItemDto.setQty(2);

        String jSon = new ObjectMapper().writeValueAsString(addLineItemDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jSon)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
