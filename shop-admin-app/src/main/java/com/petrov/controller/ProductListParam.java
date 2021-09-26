package com.petrov.controller;



import java.math.BigDecimal;


public class ProductListParam {

    private String titleFilter;
    private BigDecimal minPriceFilter;
    private BigDecimal maxPriceFilter;
    private Integer page;
    private Integer size;
    private String sort;
    private String direction;
    private String reverse;
    private Long categoryId;


    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public BigDecimal getMinPriceFilter() {
        return minPriceFilter;
    }

    public void setMinPriceFilter(BigDecimal minPriceFilter) {
        this.minPriceFilter = minPriceFilter;
    }

    public BigDecimal getMaxPriceFilter() {
        return maxPriceFilter;
    }

    public void setMaxPriceFilter(BigDecimal maxPriceFilter) {
        this.maxPriceFilter = maxPriceFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getReverse() {
        try {
            return direction.equals("asc") ? ("desc") : ("asc");
        } catch (NullPointerException e) {
            return "asc";
        }
    }
    public void setReverse(String reverse) {
        this.reverse = reverse;
    }
}
