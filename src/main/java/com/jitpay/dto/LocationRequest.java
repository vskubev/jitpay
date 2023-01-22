package com.jitpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationRequest {
    private final UUID userId;
    private final LocalDateTime createdOn;
    private final LocationCoordinates location;
}
