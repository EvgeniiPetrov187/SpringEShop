package com.petrov.service;



import com.petrov.controller.*;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.persist.model.Category;
import com.petrov.persist.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<CategoryDto> findWithFilter(CategoryListParam categoryListParam) {
        Specification<Category> spec = Specification.where(null);

        if (categoryListParam.getSort() != null && !categoryListParam.getSort().isEmpty()) {
            return categoryRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(categoryListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(categoryListParam.getSize()).orElse(3),
                                    Optional.of(Optional.ofNullable(categoryListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                            Sort.by(categoryListParam.getSort()).descending() :
                                            Sort.by(categoryListParam.getSort()).ascending()).get()))
                    .map(category -> new CategoryDto(category.getId(), category.getTitle()));
        } else {
            return categoryRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(categoryListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(categoryListParam.getSize()).orElse(3)))
                    .map(category -> new CategoryDto(category.getId(), category.getTitle()));
        }
    }


    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(product -> new CategoryDto(product.getId(), product.getTitle()));
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category(
                categoryDto.getId(),
                categoryDto.getTitle());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getTitle()))
                .collect(Collectors.toList());
    }
}

