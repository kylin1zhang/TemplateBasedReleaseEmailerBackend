package com.emailer.service.impl;

import com.emailer.model.User;
import com.emailer.model.dto.UserDTO;
import com.emailer.repository.UserRepository;
import com.emailer.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return userRepository.save(user);
    }

    @Override
    public User getUserBySoeid(String soeid) {
        return userRepository.findById(soeid)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void deleteUser(String soeid) {
        userRepository.deleteById(soeid);
    }

    @Override
    @Transactional
    public User updateUser(String soeid, UserDTO userDTO) {
        User existingUser = getUserBySoeid(soeid);
        BeanUtils.copyProperties(userDTO, existingUser);
        return userRepository.save(existingUser);
    }
} 