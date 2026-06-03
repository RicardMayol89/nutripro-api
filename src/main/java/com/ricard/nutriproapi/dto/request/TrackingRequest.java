package com.ricard.nutriproapi.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de entrada para crear un registro diario de seguimiento.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackingRequest {

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @NotNull(message = "Las calorías son obligatorias")
    @Min(value = 0, message = "Las calorías no pueden ser negativas")
    private Integer calories;

    @NotNull(message = "Los gramos de proteína son obligatorios")
    @Min(value = 0, message = "Los gramos de proteína no pueden ser negativos")
    private Integer proteinGrams;

    @Size(max = 512, message = "Las notas no pueden superar 512 caracteres")
    private String notes;

    @NotNull(message = "El id de usuario es obligatorio")
    private Long userId;
}
