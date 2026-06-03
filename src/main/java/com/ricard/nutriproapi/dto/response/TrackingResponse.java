package com.ricard.nutriproapi.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de salida para un registro diario de seguimiento.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingResponse {

    private Long id;
    private LocalDate date;
    private Integer calories;
    private Integer proteinGrams;
    private String notes;
    private Long userId;
}
