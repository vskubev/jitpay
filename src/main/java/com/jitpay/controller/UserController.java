package com.jitpay.controller;

import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.create(userRequest));
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.update(userRequest));
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.findByIdWithLatestLocation(userId));
    }
}
