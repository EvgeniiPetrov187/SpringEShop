package com.petrov.service;


import com.petrov.controller.NotFoundException;
import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.dto.CategoryDto;
import com.petrov.controller.dto.ProductDto;
import com.petrov.controller.ProductListParam;
import com.petrov.persist.BrandRepository;
import com.petrov.persist.CategoryRepository;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Picture;
import com.petrov.persist.model.Product;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                              BrandRepository brandRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findAll(ProductListParam productListParam) {
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
                                    Optional.ofNullable(productListParam.getSize()).orElse(7),
                                    Optional.of(Optional.ofNullable(productListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                            Sort.by(productListParam.getSort()).descending() :
                                            Sort.by(productListParam.getSort()).ascending()).get()))
                    .map(product -> new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                            new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()),
                            getPictureId(product))
                    );
        } else {
            return productRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(productListParam.getSize()).orElse(7)))
                    .map(product -> new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            new CategoryDto(product.getCategory().getId(), product.getCategory().getTitle()),
                            new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()),
                            getPictureId(product))
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
                        new BrandDto(product.getCategory().getId(), product.getBrand().getTitle()),
                        getPictureId(product))
                );
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategoryDto().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(productDto.getBrandDto().getId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(productDto.getName());
        product.setCost(productDto.getCost());
        product.setCategory(category);
        product.setBrand(brand);

        if (productDto.getNewPictures() != null) {
            for (MultipartFile picture : productDto.getNewPictures()) {
                try {
                    product.getPictures().add(new Picture(null,
                            picture.getOriginalFilename(),
                            picture.getContentType(),
                            pictureService.createPicture(picture.getBytes()),
                            product));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Long> getPictureId(Product product){
        List<Long> picIds = new ArrayList<>();
        for(Picture picture : product.getPictures()){
            picIds.add(picture.getId());
        }
        return picIds;
    }

}


