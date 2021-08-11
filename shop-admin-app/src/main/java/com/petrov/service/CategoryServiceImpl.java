package com.petrov.service;



import com.petrov.controller.CategoryDto;
import com.petrov.controller.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.controller.UserDto;
import com.petrov.persist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// класс будет доработан по фильтрам
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(product -> new CategoryDto(product.getId(), product.getTitle()));
    }

    @Override
    public void save(CategoryDto productDto) {
        Category category = new Category(
                productDto.getId(),
                productDto.getTitle());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(product -> new CategoryDto(product.getId(), product.getTitle()))
                .collect(Collectors.toList());
    }
}

