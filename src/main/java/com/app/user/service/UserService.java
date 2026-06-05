package com.app.user.service;

import com.app.user.dto.UserRegistrationRequest;
import com.app.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    List<UserResponse> getAllUsers(); // New method added
}