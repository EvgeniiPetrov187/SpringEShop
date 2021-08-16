package com.petrov.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @Max(value = 1000)
    private BigDecimal cost;

    private CategoryDto categoryDto;

    private BrandDto brandDto;

    public ProductDto(Long id, String name, BigDecimal cost, CategoryDto categoryDto, BrandDto brandDto) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.categoryDto = categoryDto;
        this.brandDto = brandDto;
    }

    public ProductDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public BrandDto getBrandDto() {
        return brandDto;
    }

    public void setBrandDto(BrandDto brandDto) {
        this.brandDto = brandDto;
    }

    public String showCategory() {
        try {
            return categoryDto.getTitle();
        } catch (NullPointerException e) {
            return "No category";
        }
    }

    public String showBrand() {
        try {
            return brandDto.getTitle();
        } catch (NullPointerException e) {
            return "No brand";
        }
    }
}



