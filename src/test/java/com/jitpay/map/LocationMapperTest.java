package com.jitpay.map;

import com.jitpay.dto.LocationCoordinates;
import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.entity.Location;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static com.jitpay.GenerateTestDataUtils.generateLocation;
import static com.jitpay.GenerateTestDataUtils.generateLocationCoordinates;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class LocationMapperTest {
    private static final java.util.UUID UUID = java.util.UUID.randomUUID();

    @Test
    public void requestToEntityTest() {
        LocationCoordinates coordinates = generateLocationCoordinates();
        LocationRequest request = new LocationRequest(UUID, LocalDateTime.now(), coordinates);
        Location location = LocationMapper.toEntity(request);

        assertEquals(request.getUserId(), location.getUserId());
        assertEquals(request.getLocation().getLatitude(), location.getLatitude());
        assertEquals(request.getLocation().getLongitude(), location.getLongitude());
        assertEquals(request.getCreatedOn(), location.getCreatedAt());
    }

    @Test
    public void entityToResponseTest() {
        Location location = generateLocation(UUID);
        LocationResponse response = LocationMapper.toResponse(UUID, singletonList(location));
        LocationResponse.Location responseLocation = response.getLocations().get(0);

        Assert.assertEquals(location.getUserId(), response.getUserId());
        Assert.assertEquals(location.getCreatedAt(), responseLocation.getCreatedOn());
        Assert.assertEquals(location.getLatitude(), responseLocation.getLocation().getLatitude());
        Assert.assertEquals(location.getLongitude(), responseLocation.getLocation().getLongitude());
    }
}
