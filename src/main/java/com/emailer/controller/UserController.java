package com.emailer.controller;

import com.emailer.model.User;
import com.emailer.model.dto.ApiResponse;
import com.emailer.model.dto.UserDTO;
import com.emailer.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody UserDTO userDTO) {
        return ApiResponse.success(userService.createUser(userDTO));
    }

    @GetMapping("/{soeid}")
    public ApiResponse<User> getUser(@PathVariable String soeid) {
        return ApiResponse.success(userService.getUserBySoeid(soeid));
    }

    @DeleteMapping("/{soeid}")
    public ApiResponse<Void> deleteUser(@PathVariable String soeid) {
        userService.deleteUser(soeid);
        return ApiResponse.success(null);
    }

    @PutMapping("/{soeid}")
    public ApiResponse<User> updateUser(@PathVariable String soeid, @RequestBody UserDTO userDTO) {
        return ApiResponse.success(userService.updateUser(soeid, userDTO));
    }
} 