package com.petrov.service;


import com.petrov.controller.dto.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.persist.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<ProductDto> findById(Long id);

    void save(ProductDto productDto);

    void deleteById(Long id);

    Page<ProductDto> findAll(ProductListParam productListParam);
}
