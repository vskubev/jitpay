package com.jitpay.service;

import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.entity.Location;
import com.jitpay.map.LocationMapper;
import com.jitpay.repository.LocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserService userService;

    public LocationServiceImpl(LocationRepository locationRepository, UserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    @Override
    public void store(LocationRequest locationRequest) {
        checkInput(locationRequest);
        checkIfUserExists(locationRequest.getUserId());
        Location location = LocationMapper.toEntity(locationRequest);
        locationRepository.save(location);
    }

    @Override
    public LocationResponse findAllLocations(LocalDateTime from, LocalDateTime to, UUID userId) {
        checkTimeFrame(from, to);
//        checkIfUserExists(userId);
        Optional<List<Location>> locations
            = locationRepository.findAllByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThanOrderByCreatedAt(
            userId,
            from,
            to
        );
        return locations.map(locationList -> LocationMapper.toResponse(userId, locationList))
            .orElseGet(() -> new LocationResponse(userId, Collections.emptyList()));
    }

    private void checkIfUserExists(UUID userId) {
        userService.findById(userId);
    }

    private void checkTimeFrame(LocalDateTime from, LocalDateTime to) {
        if (from.isAfter(to) || from.isEqual(to)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Incorrect time frame. Time FROM should be before time TO"
            );
        }
    }

    private void checkInput(LocationRequest locationRequest) {
        if (locationRequest == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (locationRequest.getLocation() == null
            || locationRequest.getLocation().getLatitude() == null
            || locationRequest.getLocation().getLongitude() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is incorrect");
        }
    }
}
