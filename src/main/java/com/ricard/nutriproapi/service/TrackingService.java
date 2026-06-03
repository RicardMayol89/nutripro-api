package com.ricard.nutriproapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricard.nutriproapi.dto.request.TrackingRequest;
import com.ricard.nutriproapi.dto.response.TrackingResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.mapper.TrackingMapper;
import com.ricard.nutriproapi.model.TrackingEntry;
import com.ricard.nutriproapi.model.User;
import com.ricard.nutriproapi.repository.TrackingRepository;
import com.ricard.nutriproapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio encargado de la lógica de negocio relacionada con los registros diarios.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final UserRepository userRepository;
    private final TrackingMapper trackingMapper;

    public List<TrackingResponse> findAll() {
        return trackingRepository.findAll().stream()
                .map(trackingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TrackingResponse findById(Long id) {
        return trackingRepository.findById(id)
                .map(trackingMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con id: " + id));
    }

    public List<TrackingResponse> findAllByUserId(Long userId) {
        return trackingRepository.findAllByUserId(userId).stream()
                .map(trackingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TrackingResponse create(TrackingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getUserId()));
        TrackingEntry entry = trackingMapper.toEntity(request, user);
        TrackingEntry saved = trackingRepository.save(entry);
        return trackingMapper.toResponse(saved);
    }

    public TrackingResponse update(Long id, TrackingRequest request) {
        TrackingEntry existingEntry = trackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con id: " + id));
        trackingMapper.updateEntity(existingEntry, request);
        TrackingEntry updated = trackingRepository.save(existingEntry);
        return trackingMapper.toResponse(updated);
    }

    public void delete(Long id) {
        TrackingEntry existingEntry = trackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con id: " + id));
        trackingRepository.delete(existingEntry);
    }
}
