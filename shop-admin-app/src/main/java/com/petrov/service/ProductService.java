package com.petrov.service;


import com.petrov.controller.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.persist.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductDto> findWithFilter(ProductListParam productListParam);

    Optional<ProductDto> findById(Long id);

    void save(ProductDto productDto);

    void deleteById(Long id);

    List<ProductDto> findAll();

}
