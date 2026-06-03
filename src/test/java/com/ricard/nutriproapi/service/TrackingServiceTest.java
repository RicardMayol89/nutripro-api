package com.ricard.nutriproapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ricard.nutriproapi.dto.request.TrackingRequest;
import com.ricard.nutriproapi.dto.response.TrackingResponse;
import com.ricard.nutriproapi.exception.ResourceNotFoundException;
import com.ricard.nutriproapi.mapper.TrackingMapper;
import com.ricard.nutriproapi.model.TrackingEntry;
import com.ricard.nutriproapi.model.User;
import com.ricard.nutriproapi.repository.TrackingRepository;
import com.ricard.nutriproapi.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {

    @Mock
    private TrackingRepository trackingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrackingMapper trackingMapper;

    @InjectMocks
    private TrackingService trackingService;

    private User testUser;
    private TrackingEntry testEntry;
    private TrackingRequest trackingRequest;
    private TrackingResponse trackingResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .build();

        testEntry = TrackingEntry.builder()
                .id(1L)
                .date(LocalDate.now())
                .calories(2000)
                .proteinGrams(100)
                .notes("Good day")
                .user(testUser)
                .build();

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
    void testFindAll() {
        TrackingEntry entry2 = TrackingEntry.builder()
                .id(2L)
                .date(LocalDate.now().minusDays(1))
                .calories(1800)
                .proteinGrams(90)
                .notes("Average day")
                .user(testUser)
                .build();
        List<TrackingEntry> entries = Arrays.asList(testEntry, entry2);

        when(trackingRepository.findAll()).thenReturn(entries);
        when(trackingMapper.toResponse(testEntry)).thenReturn(trackingResponse);
        when(trackingMapper.toResponse(entry2)).thenReturn(TrackingResponse.builder()
                .id(2L)
                .date(LocalDate.now().minusDays(1))
                .calories(1800)
                .proteinGrams(90)
                .notes("Average day")
                .userId(1L)
                .build());

        List<TrackingResponse> responses = trackingService.findAll();

        assertEquals(2, responses.size());
        verify(trackingRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        when(trackingRepository.findById(1L)).thenReturn(Optional.of(testEntry));
        when(trackingMapper.toResponse(testEntry)).thenReturn(trackingResponse);

        TrackingResponse response = trackingService.findById(1L);

        assertNotNull(response);
        assertEquals(2000, response.getCalories());
        verify(trackingRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(trackingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            trackingService.findById(999L);
        });

        verify(trackingRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAllByUserId() {
        List<TrackingEntry> entries = Arrays.asList(testEntry);

        when(trackingRepository.findAllByUserId(1L)).thenReturn(entries);
        when(trackingMapper.toResponse(testEntry)).thenReturn(trackingResponse);

        List<TrackingResponse> responses = trackingService.findAllByUserId(1L);

        assertEquals(1, responses.size());
        verify(trackingRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testCreateSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(trackingMapper.toEntity(trackingRequest, testUser)).thenReturn(testEntry);
        when(trackingRepository.save(any(TrackingEntry.class))).thenReturn(testEntry);
        when(trackingMapper.toResponse(testEntry)).thenReturn(trackingResponse);

        TrackingResponse response = trackingService.create(trackingRequest);

        assertNotNull(response);
        assertEquals(2000, response.getCalories());
        verify(userRepository, times(1)).findById(1L);
        verify(trackingRepository, times(1)).save(any(TrackingEntry.class));
    }

    @Test
    void testCreateUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        TrackingRequest invalidRequest = TrackingRequest.builder()
                .date(LocalDate.now())
                .calories(2000)
                .proteinGrams(100)
                .notes("Good day")
                .userId(999L)
                .build();

        assertThrows(ResourceNotFoundException.class, () -> {
            trackingService.create(invalidRequest);
        });

        verify(trackingRepository, never()).save(any(TrackingEntry.class));
    }

    @Test
    void testUpdateSuccess() {
        when(trackingRepository.findById(1L)).thenReturn(Optional.of(testEntry));
        when(trackingRepository.save(any(TrackingEntry.class))).thenReturn(testEntry);
        when(trackingMapper.toResponse(testEntry)).thenReturn(trackingResponse);

        TrackingResponse response = trackingService.update(1L, trackingRequest);

        assertNotNull(response);
        verify(trackingMapper, times(1)).updateEntity(testEntry, trackingRequest);
        verify(trackingRepository, times(1)).save(any(TrackingEntry.class));
    }

    @Test
    void testUpdateNotFound() {
        when(trackingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            trackingService.update(999L, trackingRequest);
        });

        verify(trackingRepository, never()).save(any(TrackingEntry.class));
    }

    @Test
    void testDeleteSuccess() {
        when(trackingRepository.findById(1L)).thenReturn(Optional.of(testEntry));

        trackingService.delete(1L);

        verify(trackingRepository, times(1)).delete(testEntry);
    }

    @Test
    void testDeleteNotFound() {
        when(trackingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            trackingService.delete(999L);
        });

        verify(trackingRepository, never()).delete(any(TrackingEntry.class));
    }
}
