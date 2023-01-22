package com.jitpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationCoordinates {
    private final Double latitude;
    private final Double longitude;
}
