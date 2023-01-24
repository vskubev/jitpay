package com.jitpay.service;

import com.jitpay.GenerateTestDataUtils;
import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.Location;
import com.jitpay.map.LocationMapper;
import com.jitpay.map.UserMapper;
import com.jitpay.repository.LocationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static com.jitpay.GenerateTestDataUtils.generateLocationRequest;
import static com.jitpay.GenerateTestDataUtils.generateUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LocationServiceImplTest {

    private final LocationRepository locationRepository = Mockito.mock(LocationRepository.class);
    private final UserService userService = Mockito.mock(UserService.class);
    private final LocationService locationService = new LocationServiceImpl(locationRepository, userService);

    @Test
    public void storeTest() {
        LocationRequest locationRequest = generateLocationRequest();
        UserResponse userResponse = UserMapper.toResponse(generateUser());
        when(userService.findById(any())).thenReturn(userResponse);
        locationService.store(locationRequest);
        verify(userService).findById(locationRequest.getUserId());
        verify(locationRepository).save(any(Location.class));
    }

    @Test(expected = ResponseStatusException.class)
    public void storeUserIsNotExistTest() {
        LocationRequest locationRequest = generateLocationRequest();
        UserResponse userResponse = UserMapper.toResponse(generateUser());
        when(userService.findById(any())).thenThrow(ResponseStatusException.class);
        locationService.store(locationRequest);

        verify(userService).findById(userResponse.getUserId());
        verify(locationRepository, times(0)).save(any(Location.class));
    }

    @Test
    public void findAllLocationsTest() {
        LocalDateTime from = LocalDateTime.of(1900, 1, 1, 12, 0, 0);
        LocalDateTime to = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
        UUID userId = UUID.randomUUID();
        Location location = GenerateTestDataUtils.generateLocation(userId);

        UserResponse userResponse = UserMapper.toResponse(generateUser());
        when(userService.findById(any())).thenReturn(userResponse);
        when(locationRepository.findAllByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThanOrderByCreatedAt(userId, from, to))
            .thenReturn(Collections.singletonList(location));

        LocationResponse response = locationService.findAllLocations(from, to, userId);
        verify(userService).findById(userId);
        verify(locationRepository)
            .findAllByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThanOrderByCreatedAt(userId, from, to);
        Assert.assertEquals(LocationMapper.toResponse(userId, Collections.singletonList(location)), response);
    }

    @Test(expected = ResponseStatusException.class)
    public void checkInputLocationRequestIsNullTest() {
        locationService.store(null);
    }

    @Test(expected = ResponseStatusException.class)
    public void checkInputLocationIsNullTest() {
        LocationRequest locationRequest = new LocationRequest(null, null, null);
        locationService.store(locationRequest);
    }

    @Test(expected = ResponseStatusException.class)
    public void checkInputLocationCoordinatesIsNullTest() {
        LocationRequest locationRequest = new LocationRequest(
            null,
            null,
            new LocationCoordinates(null, null)
        );
        locationService.store(locationRequest);
    }
}