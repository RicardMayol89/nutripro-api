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
import com.ricard.nutriproapi.dto.request.TrackingRequest;
import com.ricard.nutriproapi.dto.response.TrackingResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.service.TrackingService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TrackingController.class)
class TrackingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrackingService trackingService;

    @Autowired
    private ObjectMapper objectMapper;

    private TrackingRequest trackingRequest;
    private TrackingResponse trackingResponse;

    @BeforeEach
    void setUp() {
        trackingRequest = TrackingRequest.builder()
                .date(LocalDate.now())
                .calories(2000)
                .proteinGrams(100)
                .notes("Good day")
                .userId(1L)
                .build();

        trackingResponse = TrackingResponse.builder()
                .id(1L)
                .date(LocalDate.now())
                .calories(2000)
                .proteinGrams(100)
                .notes("Good day")
                .userId(1L)
                .build();
    }

    @Test
    void testGetAllTracking() throws Exception {
        List<TrackingResponse> entries = Arrays.asList(trackingResponse);
        when(trackingService.findAll()).thenReturn(entries);

        mockMvc.perform(get("/api/tracking"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calories").value(2000));
    }

    @Test
    void testGetTrackingByIdSuccess() throws Exception {
        when(trackingService.findById(1L)).thenReturn(trackingResponse);

        mockMvc.perform(get("/api/tracking/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calories").value(2000))
                .andExpect(jsonPath("$.proteinGrams").value(100));
    }

    @Test
    void testGetTrackingByIdNotFound() throws Exception {
        when(trackingService.findById(999L)).thenThrow(new ResourceNotFoundException("Registro no encontrado con id: 999"));

        mockMvc.perform(get("/api/tracking/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Registro no encontrado con id: 999"));
    }

    @Test
    void testGetTrackingByUserId() throws Exception {
        List<TrackingResponse> entries = Arrays.asList(trackingResponse);
        when(trackingService.findAllByUserId(1L)).thenReturn(entries);

        mockMvc.perform(get("/api/tracking/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void testCreateTrackingSuccess() throws Exception {
        when(trackingService.create(any(TrackingRequest.class))).thenReturn(trackingResponse);

        mockMvc.perform(post("/api/tracking")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.calories").value(2000));
    }

    @Test
    void testCreateTrackingWithInvalidCalories() throws Exception {
        TrackingRequest invalidRequest = TrackingRequest.builder()
                .date(LocalDate.now())
                .calories(-100)
                .proteinGrams(100)
                .notes("Bad data")
                .userId(1L)
                .build();

        mockMvc.perform(post("/api/tracking")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTrackingUserNotFound() throws Exception {
        when(trackingService.create(any(TrackingRequest.class)))
                .thenThrow(new ResourceNotFoundException("Usuario no encontrado con id: 999"));

        mockMvc.perform(post("/api/tracking")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateTrackingSuccess() throws Exception {
        when(trackingService.update(eq(1L), any(TrackingRequest.class))).thenReturn(trackingResponse);

        mockMvc.perform(put("/api/tracking/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calories").value(2000));
    }

    @Test
    void testUpdateTrackingNotFound() throws Exception {
        when(trackingService.update(eq(999L), any(TrackingRequest.class)))
                .thenThrow(new ResourceNotFoundException("Registro no encontrado con id: 999"));

        mockMvc.perform(put("/api/tracking/999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(trackingRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTrackingSuccess() throws Exception {
        doNothing().when(trackingService).delete(1L);

        mockMvc.perform(delete("/api/tracking/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTrackingNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Registro no encontrado con id: 999"))
                .when(trackingService).delete(999L);

        mockMvc.perform(delete("/api/tracking/999"))
                .andExpect(status().isNotFound());
    }
}
