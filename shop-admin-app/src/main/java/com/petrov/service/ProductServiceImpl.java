package com.petrov.service;



import com.petrov.controller.CategoryDto;
import com.petrov.controller.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.controller.RoleDto;
import com.petrov.persist.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findWithFilter(ProductListParam productListParam) {
        Specification<Product> spec = Specification.where(null);
        if (productListParam.getTitleFilter() != null && !productListParam.getTitleFilter().isEmpty()) {
            spec = spec.and(ProductSpecifications.productPrefix(productListParam.getTitleFilter()));
        }
        if (productListParam.getMinPriceFilter() != null) {
            spec = spec.and(ProductSpecifications.minPrice(productListParam.getMinPriceFilter()));
        }
        if (productListParam.getMaxPriceFilter() != null) {
            spec = spec.and(ProductSpecifications.maxPrice(productListParam.getMaxPriceFilter()));
        }


        if (productListParam.getSort() != null && !productListParam.getSort().isEmpty()) {
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productListParam.getSize()).orElse(3),
                            Optional.of(Optional.ofNullable(productListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                    Sort.by(productListParam.getSort()).descending() :
                                    Sort.by(productListParam.getSort()).ascending()).get()))
                    .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), product.getCategory()));
        } else {
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productListParam.getSize()).orElse(3)))
                    .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), product.getCategory()));
        }
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), product.getCategory()));
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getCost(),
                mapCategoryDto(productDto.getCategoryDto()));
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), product.getCategory()))
                .collect(Collectors.toList());
    }

    /// метод для сохранения категории продукта из categoryDto
    private static Category mapCategoryDto(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getTitle());
        return category;
    }
}

