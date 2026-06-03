package com.ricard.nutriproapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricard.nutriproapi.dto.request.TrackingRequest;
import com.ricard.nutriproapi.dto.response.TrackingResponse;
import com.ricard.nutriproapi.service.TrackingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para operaciones sobre registros de seguimiento diario.
 */
@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping
    public ResponseEntity<List<TrackingResponse>> findAll() {
        return ResponseEntity.ok(trackingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(trackingService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrackingResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(trackingService.findAllByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<TrackingResponse> create(@Valid @RequestBody TrackingRequest request) {
        TrackingResponse response = trackingService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackingResponse> update(@PathVariable Long id, @Valid @RequestBody TrackingRequest request) {
        return ResponseEntity.ok(trackingService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trackingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
