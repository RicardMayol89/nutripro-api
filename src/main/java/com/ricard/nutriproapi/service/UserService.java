package com.ricard.nutriproapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ricard.nutriproapi.dto.request.UserRequest;
import com.ricard.nutriproapi.dto.response.UserResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.mapper.UserMapper;
import com.ricard.nutriproapi.model.User;
import com.ricard.nutriproapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con los usuarios.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public UserResponse create(UserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User user = userMapper.toEntity(request);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    public UserResponse update(Long id, UserRequest request) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userMapper.updateEntity(existingUser, request);
        User updated = userRepository.save(existingUser);
        return userMapper.toResponse(updated);
    }

    public void delete(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userRepository.delete(existingUser);
    }
}
