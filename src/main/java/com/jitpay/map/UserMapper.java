package com.jitpay.map;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.Location;
import com.jitpay.entity.User;

import java.util.Comparator;

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
        UserResponse response = new UserResponse(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getSecondName()
        );
        if (user.getLocations() != null) {
            user.getLocations()
                .stream()
                .max(Comparator.comparing(Location::getCreatedAt))
                .ifPresent(location -> response.setLocation(new LocationCoordinates(
                    location.getLatitude(),
                    location.getLongitude()
                )));
        }
        return response;
    }

    public static UserResponse toResponseWithoutLocations(User user) {
        return new UserResponse(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getSecondName()
        );
    }
}
