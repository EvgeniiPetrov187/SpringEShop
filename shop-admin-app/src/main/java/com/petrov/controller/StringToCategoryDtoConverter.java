package com.petrov.controller;

import com.petrov.controller.dto.CategoryDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class StringToCategoryDtoConverter implements Converter<String, CategoryDto> {

    @Override
    public CategoryDto convert(String id) {
        return new CategoryDto(Long.parseLong(id));
    }
}