package com.jitpay.map;

import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.Location;
import com.jitpay.entity.User;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.UUID;

import static com.jitpay.GenerateTestDataUtils.generateLocation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
public class UserMapperTest {

    private static final UUID UUID = java.util.UUID.randomUUID();
    private static final String FIRST_NAME = "firstName";
    private static final String SECOND_NAME = "secondName";
    private static final String EMAIL = "test@email.com";

    @Test
    public void userToEntityTest() {
        UserRequest request = new UserRequest(UUID, EMAIL, FIRST_NAME, SECOND_NAME);
        User user = UserMapper.toEntity(request);

        assertEquals(request.getUserId(), user.getUserId());
        assertEquals(request.getFirstName(), user.getFirstName());
        assertEquals(request.getSecondName(), user.getSecondName());
        assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    public void userEntityToResponse() {
        User user = new User(UUID, EMAIL, FIRST_NAME, SECOND_NAME);
        UserResponse response = UserMapper.toResponse(user);

        assertEquals(user.getUserId(), response.getUserId());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getSecondName(), response.getSecondName());
        assertEquals(user.getEmail(), response.getEmail());
        assertNull(response.getLocation());
    }

    @Test
    public void userEntityToResponseWithLocation() {
        User user = new User(UUID, EMAIL, FIRST_NAME, SECOND_NAME);
        Location location = generateLocation(UUID);
        user.setLocations(Collections.singletonList(location));
        UserResponse response = UserMapper.toResponse(user);

        assertEquals(user.getUserId(), response.getUserId());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getSecondName(), response.getSecondName());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getLocations().get(0).getLatitude(), response.getLocation().getLatitude());
        assertEquals(user.getLocations().get(0).getLongitude(), response.getLocation().getLongitude());
    }
}
