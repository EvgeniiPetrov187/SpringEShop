package com.petrov.controller;


import com.petrov.controller.dto.CategoryDto;
import com.petrov.persist.CategoryRepository;
import com.petrov.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);


    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService =  categoryService;
    }


    @GetMapping
    public String listPage(Model model, CategoryListParam categoryListParam) {
        logger.info("Category list page requested");
        model.addAttribute("categories", categoryService.findWithFilter(categoryListParam));
        return "categories";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        logger.info("New category page requested");
        model.addAttribute("categoryDto", new CategoryDto());
        return "new_category_form";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long id, Model model) {
        logger.info("Edit category page requested");

        model.addAttribute("categoryDto", categoryService.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found")));
        model.addAttribute("categories", categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getTitle()))
                .collect(Collectors.toList()));
        return "new_category_form";
    }

    @PostMapping
    public String update(@Valid CategoryDto categoryDto, BindingResult result) {
        logger.info("Saving category");

        if (result.hasErrors()) {
            return "new_category_form";
        }

        categoryService.save(categoryDto);
        return "redirect:/categories";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting category");
        model.addAttribute("category", categoryService.findById(id));
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}

