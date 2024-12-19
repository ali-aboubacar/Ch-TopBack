package com.chaTop.Backend.controller;

import com.chaTop.Backend.dtos.UserDto;
import com.chaTop.Backend.mapper.UserMapper;
import com.chaTop.Backend.model.User;
import com.chaTop.Backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getOneUser(@PathVariable("id") long id){
        try {
            Optional<User> user = userService.getUserById(id);
            return user.map(value -> new ResponseEntity(userMapper.toUserDto(value), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
