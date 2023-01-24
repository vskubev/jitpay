package com.jitpay.map;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.User;

public class UserMapper {

    public static User toEntity(UserRequest userRequest) {
        return new User(
            userRequest.getUserId(),
            userRequest.getEmail(),
            userRequest.getFirstName(),
            userRequest.getSecondName()
        );
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getSecondName()
        );
    }

    public static UserResponse toResponseWithLastLocation(User user) {
        UserResponse response = new UserResponse(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getSecondName()
        );
        if (user.getLocations() != null && !user.getLocations().isEmpty()) {
            user.getLocations().stream()
                .findFirst()
                .ifPresent(location -> response.setLocation(new LocationCoordinates(
                    location.getLatitude(),
                    location.getLongitude()
                )));
        }
        return response;
    }
}
