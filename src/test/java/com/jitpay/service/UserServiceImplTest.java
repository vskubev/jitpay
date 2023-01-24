package com.jitpay.service;

import com.jitpay.GenerateTestDataUtils;
import com.jitpay.dto.UserRequest;
import com.jitpay.dto.UserResponse;
import com.jitpay.entity.User;
import com.jitpay.map.UserMapper;
import com.jitpay.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void createTest() {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        User user = UserMapper.toEntity(userRequest);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserResponse response = userService.create(userRequest);

        Mockito.verify(userRepository).save(user);
        Assert.assertEquals(UserMapper.toResponseWithLastLocation(user), response);
    }

    @Test
    public void updateTest() {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        User user = UserMapper.toEntity(userRequest);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserResponse response = userService.update(userRequest);

        Mockito.verify(userRepository).save(user);
        Mockito.verify(userRepository).findByEmail(user.getEmail());
        Mockito.verify(userRepository).findById(user.getUserId());
        Assert.assertEquals(UserMapper.toResponse(user), response);
    }

    @Test(expected = ResponseStatusException.class)
    public void updateUserIsNotExistTest() {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        User user = UserMapper.toEntity(userRequest);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        userService.update(userRequest);

        Mockito.verify(userRepository).findByEmail(user.getEmail());
        Mockito.verify(userRepository).findById(user.getUserId());
    }

    @Test(expected = ResponseStatusException.class)
    public void updateEmailIsNotUniqueTest() {
        UserRequest userRequest = GenerateTestDataUtils.generateUserRequest();
        User user = UserMapper.toEntity(userRequest);
        User anotherUser = new User(UUID.randomUUID(), "a@a.com", "first", "second");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(anotherUser));

        userService.update(userRequest);
        Mockito.verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void findByIdTest() {
        User user = GenerateTestDataUtils.generateUser();
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        UserResponse response = userService.findById(user.getUserId());

        Mockito.verify(userRepository).findById(user.getUserId());
        Assert.assertEquals(response, UserMapper.toResponse(user));
    }

    @Test(expected = ResponseStatusException.class)
    public void findByIdWithLatestLocationNotFoundTest() {
        Mockito.when(userRepository.findByIdWithLatestLocation(Mockito.any())).thenReturn(Optional.empty());

        userService.findByIdWithLatestLocation(UUID.randomUUID());
    }

    @Test
    public void findByIdWithLatestLocationTest() {
        User user = GenerateTestDataUtils.generateUserWithLocation();
        Mockito.when(userRepository.findByIdWithLatestLocation(user.getUserId())).thenReturn(Optional.of(user));

        UserResponse response = userService.findByIdWithLatestLocation(user.getUserId());

        Mockito.verify(userRepository).findByIdWithLatestLocation(user.getUserId());
        Assert.assertEquals(response, UserMapper.toResponseWithLastLocation(user));
    }

    @Test(expected = ResponseStatusException.class)
    public void findByIdNotFoundTest() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        userService.findByIdWithLatestLocation(UUID.randomUUID());
    }

    @Test(expected = ResponseStatusException.class)
    public void createUserRequestIsNullTest() {
        userService.create(null);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createUserIdIsNullTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            null,
            template.getEmail(),
            template.getFirstName(),
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createFirstNameIsNullTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            null,
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createFirstNameIsEmptyTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            "",
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createFirstNameIsIncorrectTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            "incorrect123",
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createSecondNameIsNullTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            template.getFirstName(),
            null
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createSecondNameIsEmptyTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            template.getFirstName(),
            ""
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createSecondNameIsIncorrectTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            template.getEmail(),
            template.getFirstName(),
            "incorrect123"
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createEmailIsNullTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            null,
            template.getFirstName(),
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createEmailIsEmptyTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            "incorrectEmail",
            template.getFirstName(),
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = ResponseStatusException.class)
    public void createEmailIsIncorrectTest() {
        UserRequest template = GenerateTestDataUtils.generateUserRequest();
        UserRequest newRequest = new UserRequest(
            template.getUserId(),
            "incorrectEmail",
            template.getFirstName(),
            template.getSecondName()
        );
        userService.create(newRequest);
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any());
    }
}