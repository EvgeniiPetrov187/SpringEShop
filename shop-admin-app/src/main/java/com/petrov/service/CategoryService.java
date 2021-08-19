package com.petrov.service;


import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.CategoryListParam;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryDto> findWithFilter(CategoryListParam categoryListParam);

    Optional<CategoryDto> findById(Long id);

    void save(CategoryDto categoryDto);

    void deleteById(Long id);

    List<CategoryDto> findAll();
}
