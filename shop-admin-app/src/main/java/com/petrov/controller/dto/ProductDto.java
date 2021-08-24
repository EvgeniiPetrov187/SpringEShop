package com.petrov.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @Max(value = 1000)
    private BigDecimal cost;

    private CategoryDto categoryDto;

    private BrandDto brandDto;

    private MultipartFile[] newPictures;

    private List<Long> pictures;

    public ProductDto(Long id, String name, BigDecimal cost, CategoryDto categoryDto, BrandDto brandDto, List<Long> pictures) {
        this.id = id;
        this.name = name;
        this.cost = cost;
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

    public MultipartFile[] getNewPictures() {
        return newPictures;
    }

    public void setNewPictures(MultipartFile[] newPictures) {
        this.newPictures = newPictures;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
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



