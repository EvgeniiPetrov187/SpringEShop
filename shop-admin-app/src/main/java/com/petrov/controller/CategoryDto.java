package com.petrov.controller;

import com.petrov.persist.Category;
import com.petrov.persist.Product;

import java.util.List;
import java.util.Objects;

/// категория
public class CategoryDto {

    private Long id;

    private String title;

    private List<Product> products;

    public CategoryDto (Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoryDto () {
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
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(getId(), category.getId()) && Objects.equals(getTitle(), category.getTitle()) && Objects.equals(getProducts(), category.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
