package com.petrov.service;


import com.petrov.controller.dto.BrandDto;
import com.petrov.controller.BrandListParam;
import com.petrov.persist.BrandRepository;
import com.petrov.persist.model.Brand;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Page<BrandDto> findWithFilter(BrandListParam brandListParam) {
        Specification<Brand> spec = Specification.where(null);

        if (brandListParam.getSort() != null && !brandListParam.getSort().isEmpty()) {
            return brandRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(brandListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(brandListParam.getSize()).orElse(3),
                                    Optional.of(Optional.ofNullable(brandListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                            Sort.by(brandListParam.getSort()).descending() :
                                            Sort.by(brandListParam.getSort()).ascending()).get()))
                    .map(brand -> new BrandDto(brand.getId(), brand.getTitle()));
        } else {
            return brandRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(brandListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(brandListParam.getSize()).orElse(3)))
                    .map(brand -> new BrandDto(brand.getId(), brand.getTitle()));
        }
    }

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public Optional<BrandDto> findById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> new BrandDto(brand.getId(), brand.getTitle()));
    }

    @Override
    public void save(BrandDto brandDto) {
        Brand brand = new Brand(
                brandDto.getId(),
                brandDto.getTitle());
        brandRepository.save(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getTitle()))
                .collect(Collectors.toList());
    }
}

