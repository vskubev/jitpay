package com.jitpay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
//@SecondaryTable(name = "locations",
//    pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id"))
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

    @OneToMany
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    @Where(clause = "created_at = (SELECT MAX(locations.created_at) FROM locations WHERE locations.user_id = locations.user_id)")
//    @Where(clause = "latitude = 52.25742342295784")
    private List<Location> locations;

//    @Column(name = "latitude", table = "locations")
//    private Double latitude;
//
//    @Column(name = "longitude", table = "locations")
//    private Double longitude;
}
