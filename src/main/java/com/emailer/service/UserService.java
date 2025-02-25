package com.emailer.service;

import com.emailer.model.User;
import com.emailer.model.dto.UserDTO;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(UserDTO userDTO);
    User getUserBySoeid(String soeid);
    void deleteUser(String soeid);
    User updateUser(String soeid, UserDTO userDTO);
} 