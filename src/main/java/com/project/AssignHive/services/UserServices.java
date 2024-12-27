package com.project.AssignHive.services;

import com.project.AssignHive.entity.User;

import java.util.List;

public interface UserServices {
    User createUser(User user);

    User updateUserByUsername(String username, User user);

    void deleteUserByUsername(String username);

    User getUserByUsername(String username);

    List<User> getAllUsers();
}
