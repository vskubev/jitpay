package com.jitpay.map;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.entity.Location;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LocationMapper {

    public static Location toEntity(LocationRequest locationRequest) {
        return new Location(
            locationRequest.getCreatedOn(),
            locationRequest.getUserId(),
            locationRequest.getLocation().getLatitude(),
            locationRequest.getLocation().getLongitude()
        );
    }

    public static LocationResponse toResponse(UUID userId, List<Location> locations) {
        List<LocationResponse.Location> responseLocations = locations
            .stream()
            .map(location -> {
                LocationCoordinates coordinates = new LocationCoordinates(
                    location.getLatitude(),
                    location.getLongitude()
                );
                return new LocationResponse.Location(location.getCreatedAt(), coordinates);
            })
            .collect(Collectors.toList());
        return new LocationResponse(
            userId,
            responseLocations
        );
    }
}
