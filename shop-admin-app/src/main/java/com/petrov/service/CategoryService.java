package com.petrov.service;


import com.petrov.controller.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> findById(Long id);

    void save(CategoryDto categoryDto);

    void deleteById(Long id);

    List<CategoryDto> findAll();
//
}
