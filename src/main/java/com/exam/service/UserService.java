package com.exam.service;

import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {
    //creating User
    public User createUser(User user, Set<UserRole> userRoles);

    //get User By username
    public User getUser(String username);
    public void deleteUser(Long userId);
}
