package com.jitpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitpay.dto.LocationRequest;
import com.jitpay.dto.LocationResponse;
import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.Location;
import com.jitpay.entity.User;
import com.jitpay.map.LocationMapper;
import com.jitpay.map.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = JitpayApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-integrationtest.yml"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserTest() throws Exception {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        User user = UserMapper.toEntity(userRequest);
        UserResponse response = UserMapper.toResponse(user);
        createUser(userRequest)
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void getUserWithLastLocationTest() throws Exception {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        LocationRequest locationRequest = GenerateTestDataUtils.generateLocationRequest(userRequest.getUserId());
        User user = UserMapper.toEntity(userRequest);
        user.setLocations(
            Collections.singletonList(
                new Location(
                    locationRequest.getCreatedOn(),
                    locationRequest.getUserId(),
                    locationRequest.getLocation().getLatitude(),
                    locationRequest.getLocation().getLongitude()
                )
            )
        );
        UserResponse response = UserMapper.toResponseWithLastLocation(user);

        createUser(userRequest);
        addLocation(locationRequest)
            .andExpect(status().isOk());

        getUser(userRequest)
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void getAllUserLocations() throws Exception {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        LocationRequest locationRequestFirst = GenerateTestDataUtils.generateLocationRequest(userRequest.getUserId());
        LocationRequest locationRequestSecond = GenerateTestDataUtils.generateLocationRequest(userRequest.getUserId());

        createUser(userRequest);

        LocalDateTime from = LocalDateTime.of(2000,1,1,12,0);
        Thread.sleep(100);
        addLocation(locationRequestFirst);
        addLocation(locationRequestSecond);
        Thread.sleep(100);
        LocalDateTime to = LocalDateTime.now();

        Location locationFirst = LocationMapper.toEntity(locationRequestFirst);
        Location locationSecond = LocationMapper.toEntity(locationRequestSecond);
        LocationResponse expectedResponse = LocationMapper.toResponse(
            userRequest.getUserId(), Arrays.asList(locationFirst, locationSecond)
        );
        getAllUserLocations(userRequest, from, to)
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private ResultActions createUser(UserRequest request) throws Exception {
        return mvc.perform(post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions getUser(UserRequest request) throws Exception {
        return mvc.perform(get("/user/" + request.getUserId())
            .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions addLocation(LocationRequest request) throws Exception {
        return mvc.perform(post("/location")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions getAllUserLocations(UserRequest request, LocalDateTime from, LocalDateTime to) throws Exception {
        return mvc.perform(
            get("/location/" + request.getUserId())
                .queryParam("from", from.toString())
                .queryParam("to", to.toString())
            .accept(MediaType.APPLICATION_JSON)
        );
    }
}
