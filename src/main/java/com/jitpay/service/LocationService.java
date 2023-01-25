package com.jitpay.service;

import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LocationService {

    void saveLocation(LocationRequest locationRequest);

    LocationResponse findAllLocations(LocalDateTime from, LocalDateTime to, UUID userId);

}
