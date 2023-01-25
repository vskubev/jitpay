package com.jitpay;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.UserRequest;
import com.jitpay.entity.Location;
import com.jitpay.entity.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class GenerateTestDataUtils {

    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "secondName";
    private static final String EMAIL = "test@email.com";

    public static UserRequest generateUserRequest() {
        return new UserRequest(
            UUID.randomUUID(),
            EMAIL,
            FIRST_NAME,
            SECOND_NAME
        );
    }

    public static User generateUser() {
        return new User(
            UUID.randomUUID(),
            EMAIL,
            FIRST_NAME,
            SECOND_NAME
        );
    }

    public static User generateUserWithLocation() {
        User user = generateUser();
        user.setLocations(Collections.singletonList(generateLocation(user.getUserId())));
        return user;
    }

    public static LocationRequest generateLocationRequest(UUID userId) {
        return new LocationRequest(
            userId,
            LocalDateTime.now(),
            generateLocationCoordinates()
        );
    }

    public static LocationCoordinates generateLocationCoordinates() {
        return new LocationCoordinates(
            Math.random(),
            Math.random()
        );
    }

    public static Location generateLocation(UUID userId) {
        return new Location(
            LocalDateTime.now(),
            userId,
            Math.random(),
            Math.random()
        );
    }
}
