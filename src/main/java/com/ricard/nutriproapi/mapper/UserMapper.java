package com.ricard.nutriproapi.mapper;

import org.springframework.stereotype.Component;

import com.ricard.nutriproapi.dto.request.UserRequest;
import com.ricard.nutriproapi.dto.response.UserResponse;
import com.ricard.nutriproapi.model.User;

/**
 * Convierte entre entidad User y DTOs de usuario.
 */
@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public void updateEntity(User existingUser, UserRequest request) {
        if (existingUser == null || request == null) {
            return;
        }
        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setEmail(request.getEmail());
    }
}
