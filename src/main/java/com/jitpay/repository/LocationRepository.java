package com.jitpay.repository;

import com.jitpay.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    List<Location> findAllByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThanOrderByCreatedAt(
        UUID userId,
        LocalDateTime from,
        LocalDateTime to
    );
}
