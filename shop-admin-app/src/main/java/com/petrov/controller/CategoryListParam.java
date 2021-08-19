package com.petrov.controller;


public class CategoryListParam {

    private Integer page;
    private Integer size;
    private String sort;
    private String direction;
    private String reverse;


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

