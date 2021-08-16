package com.petrov.service;


import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.BrandListParam;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Page<BrandDto> findWithFilter(BrandListParam brandListParam);

    Optional<BrandDto> findById(Long id);

    void save(BrandDto categoryDto);

    void deleteById(Long id);

    List<BrandDto> findAll();

}
