package com.petrov.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.petrov.controller.dto.ProductDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

//@JsonPropertyOrder({"productId","productDto","qty","color","material"})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem implements Serializable {

    private Long productId;

    private ProductDto productDto;

    private Integer qty;

    private String color;

    private String material;

    public LineItem(ProductDto productDto, String color, String material) {
        this.productId = productDto.getId();
        this.productDto = productDto;
        this.color = color;
        this.material = material;
    }

    public LineItem() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getItemTotal() {
        return productDto.getCost().multiply(new BigDecimal(qty));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineItem)) return false;
        LineItem lineItem = (LineItem) o;
        return Objects.equals(getProductDto(), lineItem.getProductDto()) && Objects.equals(getColor(), lineItem.getColor()) && Objects.equals(getMaterial(), lineItem.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, color, material);
    }
}