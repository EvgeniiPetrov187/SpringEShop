package com.petrov.service;

import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void testFindById() {
        Category expectedCategory = new Category();
        Brand expectedBrand = new Brand();
        expectedCategory.setId(1L);
        expectedCategory.setTitle("Category name");
        expectedBrand.setTitle("New Brand");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setName("Product name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setCost(new BigDecimal(12345));

        when(productRepository.findById(eq(expectedProduct.getId())))
                .thenReturn(Optional.of(expectedProduct));

        Optional<ProductDto> opt = productService.findById(expectedProduct.getId());

        assertTrue(opt.isPresent());
        assertEquals(expectedProduct.getId(), opt.get().getId());
        assertEquals(expectedProduct.getName(), opt.get().getName());
    }

    @Test
    public void testFindAll() {
        Category expectedCategory = new Category();
        Brand expectedBrand = new Brand();
        expectedCategory.setId(1L);
        expectedCategory.setTitle("Category name");
        expectedBrand.setTitle("New Brand");

        Product expectedProduct1 = new Product();
        Product expectedProduct2 = new Product();

        expectedProduct1.setId(1L);
        expectedProduct1.setName("Product name");
        expectedProduct1.setCategory(expectedCategory);
        expectedProduct1.setBrand(expectedBrand);
        expectedProduct1.setPictures(new ArrayList<>());
        expectedProduct1.setCost(new BigDecimal(100));

        expectedProduct2.setId(2L);
        expectedProduct2.setName("Product name");
        expectedProduct2.setCategory(expectedCategory);
        expectedProduct2.setBrand(expectedBrand);
        expectedProduct2.setPictures(new ArrayList<>());
        expectedProduct2.setCost(new BigDecimal(200));

        List<Product> productList = new ArrayList<>();
        productList.add(expectedProduct1);
        productList.add(expectedProduct2);
        Page<Product> productPage = new PageImpl<>(productList);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);

        Page<ProductDto> pageProductDtos = productService.findAll(
                Optional.of(expectedCategory.getId()),
                Optional.of("Product"),
                Optional.of(expectedProduct1.getCost()),
                Optional.of(expectedProduct2.getCost()),
                0,
                productPage.getContent().size(),
                "id");

        assertEquals(pageProductDtos.getContent().size(), productList.size());
        assertNotNull(pageProductDtos.getContent());
        assertEquals(pageProductDtos.getContent().get(0).getName(), productList.get(0).getName());
        assertEquals(pageProductDtos.getContent().get(1).getName(), productList.get(1).getName());
        assertEquals(pageProductDtos.getContent().get(0).getDescription(), productList.get(0).getDescription());
        assertEquals(pageProductDtos.getContent().get(1).getDescription(), productList.get(1).getDescription());
        assertEquals(pageProductDtos.getContent().get(0).getCost(), productList.get(0).getCost());
        assertEquals(pageProductDtos.getContent().get(1).getCost(), productList.get(1).getCost());
    }
}
