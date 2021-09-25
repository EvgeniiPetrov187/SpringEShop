package com.petrov.controller.service;

import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.ProductSpecifications;
import com.petrov.persist.model.Picture;
import com.petrov.persist.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId,
                                    Optional<String> namePattern,
                                    Optional<BigDecimal> minPrice,
                                    Optional<BigDecimal> maxPrice,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecifications.byCategory(categoryId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecifications.productPrefix(namePattern.get()));
        }
        //добавлена фильтрация по цене
        if (minPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.minPrice(minPrice.get()));
        }
        if (maxPrice.isPresent()) {
            spec = spec.and(ProductSpecifications.maxPrice(maxPrice.get()));
        }

        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        product.getDescription(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                        new BrandDto(product.getBrand().getId(), product.getBrand().getTitle()),
                        product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        product.getDescription(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                        new BrandDto(product.getBrand().getId(), product.getBrand().getTitle()),
                        product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}


