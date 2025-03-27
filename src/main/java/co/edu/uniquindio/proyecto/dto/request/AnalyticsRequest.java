package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para solicitud de reportes analíticos")
public class AnalyticsRequest {
    @Schema(description = "Métricas a calcular",
            required = true,
            example = "[\"categorias\", \"estados\", \"historial\", \"tendencias\"]",
            allowableValues = {"categorias", "estados", "historial", "tendencias"})
    private List<String> metricas;

    @Schema(description = "Fecha de inicio para filtrar (opcional)",
            example = "2023-01-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin para filtrar (opcional)",
            example = "2023-12-31")
    private LocalDate fechaFin;
}