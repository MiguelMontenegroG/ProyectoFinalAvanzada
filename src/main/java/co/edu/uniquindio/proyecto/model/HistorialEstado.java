package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reportes") // Indica que esta clase se almacena en la colección "reportes" en MongoDB


@Schema(description = "Registro de cambio de estado de un reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialEstado {

    @Schema(description = "Estado del reporte", example = "verificado", allowableValues = {"pendiente", "en_revision", "verificado", "rechazado", "resuelto"})
    private String estado;

    @Schema(description = "Fecha y hora del cambio de estado", example = "2023-03-15T14:30:00")
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Schema(description = "Motivo del cambio de estado", example = "Verificado por múltiples reportes similares")
    private String motivo;

    public HistorialEstado(String nuevoEstado, String motivo) {
    }
}
