package com.ricard.nutriproapi.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ricard.nutriproapi.dto.request.UserRequest;
import com.ricard.nutriproapi.dto.response.UserResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.mapper.UserMapper;
import com.ricard.nutriproapi.model.User;
import com.ricard.nutriproapi.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .build();

        userRequest = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testFindAll() {
        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .build();
        List<User> users = Arrays.asList(testUser, user2);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponse(testUser)).thenReturn(userResponse);
        when(userMapper.toResponse(user2)).thenReturn(UserResponse.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .build());

        List<UserResponse> responses = userService.findAll();

        assertEquals(2, responses.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userMapper.toResponse(testUser)).thenReturn(userResponse);

        UserResponse response = userService.findById(1L);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.findById(999L);
        });

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    void testCreate() {
        User savedUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .build();

        when(userMapper.toEntity(userRequest)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toResponse(savedUser)).thenReturn(userResponse);

        UserResponse response = userService.create(userRequest);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(userResponse);

        UserResponse response = userService.update(1L, userRequest);

        assertNotNull(response);
        verify(userMapper, times(1)).updateEntity(testUser, userRequest);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.update(999L, userRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        userService.delete(1L);

        verify(userRepository, times(1)).delete(testUser);
    }

    @Test
    void testDeleteNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.delete(999L);
        });

        verify(userRepository, never()).delete(any(User.class));
    }
}
