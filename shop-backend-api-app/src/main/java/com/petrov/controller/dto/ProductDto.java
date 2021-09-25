package com.petrov.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal cost;

    private CategoryDto category;

    private BrandDto brand;

    private List<Long> pictures;

    private Long mainPictureId;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal price, String description,
                      CategoryDto category, BrandDto brand, List<Long> pictures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = price;
        this.category = category;
        this.brand = brand;
        this.pictures = pictures;
        if (pictures != null && !pictures.isEmpty()) {
            this.mainPictureId = pictures.get(0);
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }

    public Long getMainPictureId() {
        return mainPictureId;
    }

    public void setMainPictureId(Long mainPictureId) {
        this.mainPictureId = mainPictureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getCost(), that.getCost()) && Objects.equals(getCategory(), that.getCategory()) && Objects.equals(getBrand(), that.getBrand());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getCost(), getCategory(), getBrand());
    }
}


