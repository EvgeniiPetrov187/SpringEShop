package com.petrov.controller.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.petrov.persist.model.Brand;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;

import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class BrandDto {

    private Long id;

    private String title;

    private List<Product> products;

    public BrandDto (Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public BrandDto (Long id) {
        this.id = id;
    }

    public BrandDto () {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        Brand brand = (Brand) o;
        return Objects.equals(getId(), brand.getId()) && Objects.equals(getTitle(), brand.getTitle()) && Objects.equals(getProducts(), brand.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}

