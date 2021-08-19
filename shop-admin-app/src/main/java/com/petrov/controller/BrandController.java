package com.petrov.controller;


import com.petrov.controller.dto.BrandDto;
import com.petrov.persist.BrandRepository;
import com.petrov.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    private final BrandService brandService;
    private final BrandRepository brandRepository;

    @Autowired
    public BrandController(BrandRepository brandRepository, BrandService brandService) {
        this.brandRepository = brandRepository;
        this.brandService =  brandService;
    }


    @GetMapping
    public String listPage(Model model, BrandListParam brandListParam) {
        logger.info("Brand list page requested");
        model.addAttribute("brands", brandService.findWithFilter(brandListParam));
        return "brands";
    }

    @GetMapping("/new")
    public String newBrandForm(Model model) {
        logger.info("New brand page requested");
        model.addAttribute("brandDto", new BrandDto());
        return "new_brand_form";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        logger.info("Edit brand page requested");

        model.addAttribute("brandDto", brandService.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found")));
        model.addAttribute("brands", brandRepository.findAll().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getTitle()))
                .collect(Collectors.toList()));
        return "new_brand_form";
    }

    @PostMapping
    public String update(@Valid BrandDto brandDto, BindingResult result) {
        logger.info("Saving category");

        if (result.hasErrors()) {
            return "new_brand_form";
        }

        brandService.save(brandDto);
        return "redirect:/brands";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting category");
        model.addAttribute("category", brandService.findById(id));
        brandService.deleteById(id);
        return "redirect:/brands";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}


