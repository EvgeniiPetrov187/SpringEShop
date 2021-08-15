package com.petrov.controller;

import com.petrov.persist.RoleRepository;

import com.petrov.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Controller
public class NewUserController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public NewUserController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/new_user")
    public String newUser(Model model) {
        logger.info("New user page requested");
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("roles", roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList()));
        return "new_user";
    }

    @PostMapping("/new_user")
    public String update(@Valid UserDto userDto, BindingResult result, Model model) {
        logger.info("Saving user");

        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toList()));
            return "new_user";
        }

        if (!userDto.getPassword().equals(userDto.getPasswordRpt())) {
            model.addAttribute("roles", roleRepository.findAll().stream()
                    .map(role -> new RoleDto(role.getId(), role.getName()))
                    .collect(Collectors.toList()));
            result.rejectValue("passwordRpt", "", "Password is wrong");
            return "new_user";
        }

        for (UserDto user : userService.findAll()) {
            if (user.getUsername().equals(userDto.getUsername())) {
                model.addAttribute("roles", roleRepository.findAll().stream()
                        .map(role -> new RoleDto(role.getId(), role.getName()))
                        .collect(Collectors.toList()));
                result.rejectValue("username", "", "Username is already exist");
                return "new_user";
            }
        }
        userService.save(userDto);
        return "redirect:/login";
    }
}

