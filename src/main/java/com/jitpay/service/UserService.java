package com.jitpay.service;

import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    UserResponse update(UserRequest userRequest);

    UserResponse findByIdWithLatestLocation(UUID userId);

    UserResponse findById(UUID userId);
}
