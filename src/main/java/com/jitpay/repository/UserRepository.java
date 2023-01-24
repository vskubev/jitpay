package com.jitpay.repository;

import com.jitpay.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(@NonNull String name);

    @Query(nativeQuery = true,
        value = "update users " +
                "set email = :email, first_name = :firstName, second_name = :secondName " +
                "where user_id = :userId " +
                "returning user_id, email, first_name, second_name;")
    User update(
        @Param("userId") @NonNull String userId,
        @Param("firstName") @NonNull String firstName,
        @Param("secondName") @NonNull String secondName,
        @Param("email") @NonNull String email
    );

    @Query(
        "from User u join fetch u.locations l " +
        "where u.userId = :userId " +
        "and l.createdAt = (select MAX(l.createdAt) FROM l)"
    )
    Optional<User> findByIdWithLatestLocation(@Param("userId") @NonNull UUID userId);
}
