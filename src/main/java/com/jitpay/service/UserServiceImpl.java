package com.jitpay.service;

import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.User;
import com.jitpay.map.UserMapper;
import com.jitpay.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse create(UserRequest userRequest) {
        checkInput(userRequest);
        checkUserIdUniqueness(userRequest.getUserId());

        User user = UserMapper.toEntity(userRequest);
        User createdUser = userRepository.save(user);
        return UserMapper.toResponseWithLastLocation(createdUser);
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        checkInput(userRequest);
        Optional<User> user = userRepository.findById(userRequest.getUserId());
        if (user.isPresent()) {
            return UserMapper.toResponse(userRepository.save(user.get()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
        }
    }

    @Override
    public UserResponse findByIdWithLatestLocation(UUID userId) {
        return userRepository.findByIdWithLatestLocation(userId)
            .map(UserMapper::toResponseWithLastLocation).orElseGet(() -> findById(userId));
    }

    @Override
    public UserResponse findById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return UserMapper.toResponse(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found");
        }
    }

    private void checkEmailUniqueness(UUID userId, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && !user.get().getUserId().equals(userId)) {
            String error = String.format("The user email %s already exists", email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private void checkUserIdUniqueness(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String error = String.format("The user id %s already exists", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
        }
    }

    private void checkInput(UserRequest userRequest) {
        if (userRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (userRequest.getUserId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is incorrect");
        }

        if (userRequest.getFirstName() == null
            || userRequest.getFirstName().isEmpty()
            || !userRequest.getFirstName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is incorrect");
        }

        if (userRequest.getSecondName() == null
            || userRequest.getSecondName().isEmpty()
            || !userRequest.getSecondName().matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Second name is incorrect");
        }

        if (userRequest.getEmail() == null
            || userRequest.getEmail().isEmpty()
            || !userRequest.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is incorrect");
        }
        checkEmailUniqueness(userRequest.getUserId(), userRequest.getEmail());
    }

}
