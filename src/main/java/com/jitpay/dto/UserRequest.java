package com.jitpay.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private final UUID userId;
    private final String email;
    private final String firstName;
    private final String secondName;
}
