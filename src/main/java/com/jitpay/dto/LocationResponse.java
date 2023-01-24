package com.jitpay.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class LocationResponse {
    private final UUID userId;
    private final List<Location> locations;

    @Data
    public static class Location {
        private final LocalDateTime createdOn;
        private final LocationCoordinates location;
    }
}
