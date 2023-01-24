package com.jitpay.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @NonNull
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @NonNull
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NonNull
    @Column(name = "second_name", nullable = false)
    private String secondName;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private List<Location> locations;
}
