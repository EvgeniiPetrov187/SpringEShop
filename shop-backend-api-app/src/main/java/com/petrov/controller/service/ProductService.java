package com.petrov.controller.service;


import com.petrov.controller.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId,
                             Optional<String> namePattern,
                             Optional<BigDecimal> minPrice,
                             Optional<BigDecimal> maxPrice,
                             Integer page, Integer size, String sortField);

    Optional<ProductDto> findById(Long id);

    void deleteById(Long id);
}
