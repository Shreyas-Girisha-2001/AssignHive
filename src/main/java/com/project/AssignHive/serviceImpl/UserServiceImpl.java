package com.project.AssignHive.serviceImpl;

import com.project.AssignHive.entity.User;
import com.project.AssignHive.exception.ResourceNotFoundException;
import com.project.AssignHive.repository.UserRepository;
import com.project.AssignHive.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException("Username already exists: " + user.getUsername());
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUserByUsername(String username, User updatedUser) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Update fields
        existingUser.setEmail(updatedUser.getEmail());

        // Hash the new password if it's updated
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        existingUser.setSubjects(updatedUser.getSubjects());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        userRepository.delete(existingUser);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}