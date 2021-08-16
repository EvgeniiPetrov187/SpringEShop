package com.petrov.controller;

import com.petrov.controller.dto.BrandDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBrandDtoConverter implements Converter<String, BrandDto> {

    @Override
    public BrandDto convert(String id) {
        return new BrandDto(Long.parseLong(id));
    }
}
