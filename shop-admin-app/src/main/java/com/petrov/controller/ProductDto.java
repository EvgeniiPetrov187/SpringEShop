package com.petrov.controller;

import com.petrov.persist.Category;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @Max(value = 1000)
    private BigDecimal cost;

    private CategoryDto categoryDto;

    private Category category;

    public ProductDto(Long id, String name, BigDecimal cost, CategoryDto categoryDto) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.categoryDto = categoryDto;
    }

    public ProductDto(Long id, String name, BigDecimal cost, Category category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // метод для отображения названия категории
    public String showCategory() {
        try {
            return category.getTitle();
        } catch (NullPointerException e){
            return "No category";
        }
    }
//
}

