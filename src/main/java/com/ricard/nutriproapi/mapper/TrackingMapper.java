package com.ricard.nutriproapi.mapper;

import com.ricard.nutriproapi.dto.request.TrackingRequest;
import com.ricard.nutriproapi.dto.response.TrackingResponse;
import com.ricard.nutriproapi.model.TrackingEntry;
import com.ricard.nutriproapi.model.User;
import org.springframework.stereotype.Component;

/**
 * Convierte entre entidad TrackingEntry y DTOs de seguimiento.
 */
@Component
public class TrackingMapper {

    public TrackingEntry toEntity(TrackingRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }
        return TrackingEntry.builder()
                .date(request.getDate())
                .calories(request.getCalories())
                .proteinGrams(request.getProteinGrams())
                .notes(request.getNotes())
                .user(user)
                .build();
    }

    public TrackingResponse toResponse(TrackingEntry entry) {
        if (entry == null) {
            return null;
        }
        return TrackingResponse.builder()
                .id(entry.getId())
                .date(entry.getDate())
                .calories(entry.getCalories())
                .proteinGrams(entry.getProteinGrams())
                .notes(entry.getNotes())
                .userId(entry.getUser() != null ? entry.getUser().getId() : null)
                .build();
    }

    public void updateEntity(TrackingEntry existingEntry, TrackingRequest request) {
        if (existingEntry == null || request == null) {
            return;
        }
        existingEntry.setDate(request.getDate());
        existingEntry.setCalories(request.getCalories());
        existingEntry.setProteinGrams(request.getProteinGrams());
        existingEntry.setNotes(request.getNotes());
    }
}
