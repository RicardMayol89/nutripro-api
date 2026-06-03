package com.ricard.nutriproapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricard.nutriproapi.dto.request.UserRequest;
import com.ricard.nutriproapi.dto.response.UserResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.service.UserService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
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
    void testGetAllUsers() throws Exception {
        List<UserResponse> users = Arrays.asList(userResponse);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void testGetUserByIdSuccess() throws Exception {
        when(userService.findById(1L)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(userService.findById(999L)).thenThrow(new ResourceNotFoundException("Usuario no encontrado con id: 999"));

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Usuario no encontrado con id: 999"));
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        when(userService.create(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testCreateUserWithInvalidEmail() throws Exception {
        UserRequest invalidRequest = UserRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validación fallida"));
    }

    @Test
    void testCreateUserWithBlankFirstName() throws Exception {
        UserRequest invalidRequest = UserRequest.builder()
                .firstName("")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        when(userService.update(eq(1L), any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        when(userService.update(eq(999L), any(UserRequest.class)))
                .thenThrow(new ResourceNotFoundException("Usuario no encontrado con id: 999"));

        mockMvc.perform(put("/api/users/999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Usuario no encontrado con id: 999"))
                .when(userService).delete(999L);

        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound());
    }
}
