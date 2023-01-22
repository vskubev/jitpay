package com.jitpay.repository;

import com.jitpay.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(@NonNull String name);

    @Query(nativeQuery = true,
        value = "select user from User user \n" +
                "           fetch join user.locations locs\n" +
                "           where user.user_id = :userId \n" +
                "           and\n" +
                "           locs.created_at = (SELECT MAX(locations.created_at) FROM locations WHERE user.user_id = locations.user_id);")
    Optional<User> findByIdWithLatestLocation(@Param("userId") @NonNull UUID userId);

    Optional<User> findTopByUserId(@NonNull UUID userId);

}
