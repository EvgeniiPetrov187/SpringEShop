package com.petrov.service;

import com.petrov.controller.dto.RoleDto;
import com.petrov.controller.dto.UserDto;
import com.petrov.controller.UserListParam;

import com.petrov.persist.RoleRepository;
import com.petrov.persist.model.User;
import com.petrov.persist.UserRepository;
import com.petrov.persist.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserDto> findWithFilter(UserListParam userListParam) {
        Specification<User> spec = Specification.where(null);
        if (userListParam.getTitleFilter() != null && !userListParam.getTitleFilter().isEmpty()) {
            spec = spec.and(UserSpecifications.userPrefix(userListParam.getTitleFilter()));
        }
        if (userListParam.getMinAgeFilter() != null) {
            spec = spec.and(UserSpecifications.minAge(userListParam.getMinAgeFilter()));
        }
        if (userListParam.getMaxAgeFilter() != null) {
            spec = spec.and(UserSpecifications.maxAge(userListParam.getMaxAgeFilter()));
        }


        if (userListParam.getSort() != null && !userListParam.getSort().isEmpty()) {
            return userRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(userListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(userListParam.getSize()).orElse(7),
                                    Optional.of(Optional.ofNullable(userListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                            Sort.by(userListParam.getSort()).descending() :
                                            Sort.by(userListParam.getSort()).ascending()).get()))
                    .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge()));
        } else {
            return userRepository.findAll(spec,
                            PageRequest.of(
                                    Optional.ofNullable(userListParam.getPage()).orElse(1) - 1,
                                    Optional.ofNullable(userListParam.getSize()).orElse(7)))
                    .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge()));
        }
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge(), mapRolesDto(user)));
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getAge(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRoles().stream()
                        .map(roleDto -> {
                            return roleRepository.getOne(roleDto.getId());
                        })
                        .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAge()))
                .collect(Collectors.toList());
    }

    private static Set<RoleDto> mapRolesDto(User user){
        return user.getRoles().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }
}



