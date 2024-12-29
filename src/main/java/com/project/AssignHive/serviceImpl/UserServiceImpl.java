package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.User;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.UserRepository;
import com.project.AssignHive.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (user.getUsername() == null) {
            throw new ResourceNotFoundException("Username cannot be null");
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ResourceNotFoundException("User With this username already exists !");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User updateUserByUsername(String username, User updatedUser) {
        if (username == null) {
            throw new ResourceNotFoundException("Username cannot be null");
        }

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setSubjects(updatedUser.getSubjects());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            existingUser.setPassword(encoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        userRepository.delete(existingUser);
    }

    @Override
    public User getUserByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

