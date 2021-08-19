package com.petrov.service;

import com.petrov.controller.dto.UserDto;
import com.petrov.controller.UserListParam;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface UserService {
    Page<UserDto> findWithFilter(UserListParam userListParam);

    Optional<UserDto> findById(Long id);

    void save(UserDto userDto);

    void deleteById(Long id);

    List<UserDto> findAll();

}
