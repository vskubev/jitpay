package com.jitpay.controller;

import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.service.LocationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public ResponseEntity<?> saveLocation(@RequestBody LocationRequest locationRequest) {
        locationService.saveLocation(locationRequest);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    @RequestMapping(value = "/location/{userId}", method = RequestMethod.GET)
    public ResponseEntity<LocationResponse> getUserLocationsByTimeRange(
        @PathVariable("userId") UUID userId,
        @RequestParam("from")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
        @RequestParam("to")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(locationService.findAllLocations(from, to, userId));
    }
}