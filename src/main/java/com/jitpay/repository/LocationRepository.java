package com.jitpay.repository;

import com.jitpay.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

    Optional<List<Location>> findAllByUserIdAndCreatedAtGreaterThanAndCreatedAtLessThanOrderByCreatedAt(
        UUID userId,
        LocalDateTime from,
        LocalDateTime to
    );
}
