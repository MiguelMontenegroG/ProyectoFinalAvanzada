package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@Schema(description = "Registro de cambio de estado de un reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEstado {

    @Schema(description = "Estado del reporte",
            allowableValues = {"PENDIENTE", "EN_REVISION", "VERIFICADO", "RECHAZADO", "RESUELTO"},
            example = "VERIFICADO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private EstadoReporte estado;

    @Schema(description = "Fecha y hora del cambio de estado", example = "2023-03-15T14:30:00")
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Schema(description = "Motivo del cambio de estado", example = "Verificado por múltiples reportes similares")
    private String motivo;

    /**
     * Constructor alternativo para creación directa
     * @param estado Estado del reporte
     * @param motivo Motivo del cambio
     */
    public HistorialEstado(EstadoReporte estado, String motivo) {
        this.estado = estado;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
    }

    /**
     * Representación legible del historial
     * @return String con formato [fecha] estado: motivo
     */
    @Override
    public String toString() {
        return String.format("[%s] %s: %s",
                fecha.toString(),
                estado != null ? estado.name() : "null",
                motivo != null ? motivo : "");
    }
}