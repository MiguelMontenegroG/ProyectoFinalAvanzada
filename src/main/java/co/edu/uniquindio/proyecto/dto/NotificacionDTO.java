package co.edu.uniquindio.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "DTO para representar una notificación enviada a un usuario")
public class NotificacionDTO {

    @Schema(description = "ID único de la notificación", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Tipo de notificación (ej. 'nuevo_comentario', 'reporte_aprobado')", example = "nuevo_comentario")
    private String tipo;

    @Schema(description = "Mensaje que será mostrado al usuario", example = "Han comentado en tu reporte")
    private String mensaje;

    @Schema(description = "ID del usuario que recibe la notificación", example = "123e4567-e89b-12d3-a456-426614174000")
    private String usuarioId;

    @Schema(description = "Fecha y hora de la notificación", example = "2025-04-19T17:42:00")
    private LocalDateTime fecha;

    @Schema(description = "Indica si la notificación ha sido leída por el usuario", example = "false")
    private boolean leida;

    @Schema(description = "Metadatos adicionales que dependen del tipo de notificación")
    private Object metadata;
}
