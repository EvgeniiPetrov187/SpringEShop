package com.petrov.controller;

public class UserListParam {

    private String titleFilter;
    private Integer minAgeFilter;
    private Integer maxAgeFilter;
    private Integer page;
    private Integer size;
    private String sort;
    private String direction;
    private String reverse;


    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public Integer getMinAgeFilter() {
        return minAgeFilter;
    }

    public void setMinAgeFilter(Integer minAgeFilter) {
        this.minAgeFilter = minAgeFilter;
    }

    public Integer getMaxAgeFilter() {
        return maxAgeFilter;
    }

    public void setMaxAgeFilter(Integer maxAgeFilter) {
        this.maxAgeFilter = maxAgeFilter;
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
