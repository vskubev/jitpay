package com.jitpay.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@RequiredArgsConstructor
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @NonNull
    private final UUID userId;
    @NonNull
    private final String email;
    @NonNull
    private final String firstName;
    @NonNull
    private final String secondName;
    @Nullable
    private LocationCoordinates location = null;
}
