package com.petrov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrov.controller.dto.OrderDto;
import com.petrov.persist.OrderRepository;
import com.petrov.persist.UserRepository;
import com.petrov.persist.model.Order;
import com.petrov.persist.model.User;
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
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CreateOrderTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @WithMockUser(value = "admin", password = "admin", roles = {"GUEST"})
    @Test
    public void createOrderTest() throws Exception {

        User user = userRepository.save(new User(1L, "admin", 22, "admin", new HashSet<>()));

        OrderDto orderDto = new OrderDto(1L, LocalDateTime.now().toString(), BigDecimal.valueOf(100), "CREATED");

        orderRepository.save(new Order(
                orderDto.getId(),
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user));

        String jSon = new ObjectMapper().writeValueAsString(orderDto);

        mvc.perform(MockMvcRequestBuilders
                        .post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jSon)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
