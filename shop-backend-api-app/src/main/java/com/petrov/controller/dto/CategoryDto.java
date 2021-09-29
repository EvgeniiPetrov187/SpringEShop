package com.petrov.controller.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.petrov.persist.model.Category;
import com.petrov.persist.model.Product;

import java.util.List;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class CategoryDto {

    private Long id;

    private String title;

    private List<Product> products;

    public CategoryDto (Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoryDto (Long id) {
        this.id = id;
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
