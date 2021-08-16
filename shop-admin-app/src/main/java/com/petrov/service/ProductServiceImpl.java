package com.petrov.service;



import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.persist.BrandRepository;
import com.petrov.persist.CategoryRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.ProductSpecifications;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
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
                    .map(product -> new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                            new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()))
                    );
        } else {
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productListParam.getSize()).orElse(3)))
                    .map(product -> new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                            new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()))
                    );
        }
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                        new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()))
                );
    }

    @Override
    public void save(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryDto().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(productDto.getBrandDto().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Product product = new Product(productDto.getId(),
                productDto.getName(),
                productDto.getCost(),
                category,
                brand);
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                        new BrandDto(product.getCategory().getId(), product.getBrand().getTitle())))
                .collect(Collectors.toList());
    }
}

