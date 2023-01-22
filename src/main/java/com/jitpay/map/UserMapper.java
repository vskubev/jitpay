package com.jitpay.map;

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
        UserResponse response = new UserResponse(
            user.getUserId(),
            user.getEmail(),
            user.getFirstName(),
            user.getSecondName()
        );
//        if (user.getLatitude() != null && user.getLongitude() != null) {
//            response.setLocation(new LocationCoordinates(user.getLatitude(), user.getLongitude()));
//        }
        return response;
    }
}
