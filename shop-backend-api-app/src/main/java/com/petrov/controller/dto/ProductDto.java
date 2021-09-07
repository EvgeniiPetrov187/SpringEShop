package com.petrov.controller.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal cost;

    private String description;

    private CategoryDto categoryDto;

    private BrandDto brandDto;

    private List<Long> pictures;

    public ProductDto(Long id, String name, BigDecimal cost, String description, CategoryDto categoryDto, BrandDto brandDto, List<Long> pictures) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.description = description;
        this.categoryDto = categoryDto;
        this.brandDto = brandDto;
        this.pictures = pictures;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }
}



