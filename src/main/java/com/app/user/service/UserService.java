package com.app.user.service;

import com.app.user.dto.UserRegistrationRequest;
import com.app.user.dto.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
}