package com.jitpay.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Location {

    @Id
    @Column(name = "location_id", nullable = false, unique = true)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID locationId = UUID.randomUUID();

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.ms")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NonNull
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NonNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NonNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
