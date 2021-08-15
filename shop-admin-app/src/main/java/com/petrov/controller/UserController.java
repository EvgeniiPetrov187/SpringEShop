package com.petrov.controller;

import com.petrov.persist.RoleRepository;
import com.petrov.service.UserService;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping
    public String listPage(Model model,
                           UserListParam userListParam) {
        logger.info("User list page requested");
        model.addAttribute("users", userService.findWithFilter(userListParam));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("New user page requested");
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "new_user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Edit user page requested");

        model.addAttribute("userDto", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "new_user_form";
    }

    @PostMapping
    public String update(@Valid UserDto userDto, BindingResult result, Model model) {
        logger.info("Saving user");

        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toList()));
            return "new_user_form";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordRpt())) {
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toList()));
            result.rejectValue("passwordRpt", "", "Password is wrong");
            return "new_user_form";
        }

        userService.save(userDto);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting user");
        model.addAttribute("user", userService.findById(id));
        userService.deleteById(id);
        return "redirect:/users";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}

