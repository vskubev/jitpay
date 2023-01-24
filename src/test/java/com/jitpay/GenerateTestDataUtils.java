package com.jitpay;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.entity.Location;

import java.time.LocalDateTime;
import java.util.UUID;

public class GenerateTestDataUtils {
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
