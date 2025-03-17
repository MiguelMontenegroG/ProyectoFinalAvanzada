package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Document("notificaciones") // Asegura que se guarde en la colección "notificaciones"


@Schema(description = "Notificación del sistema para usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Schema(description = "Identificador único de la notificación", example = "550e8400-e29b-41d4-a716-446655440000")
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Schema(description = "Tipo de notificación", example = "nuevo_comentario",
            allowableValues = {"nuevo_reporte", "cambio_estado", "nuevo_comentario", "alerta_zona"})
    private String tipo;

    @Schema(description = "Contenido de la notificación",
            example = "Han comentado en tu reporte 'Bache peligroso en la vía'")
    private String mensaje;

    @Schema(description = "ID del usuario destinatario", example = "550e8400-e29b-41d4-a716-446655440000")
    private String usuarioId;

    @Schema(description = "Fecha y hora de generación", example = "2023-03-15T14:30:00")
    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @Schema(description = "Estado de lectura", example = "false")
    @Builder.Default
    private boolean leida = false;

    @Schema(description = "Datos adicionales relacionados")
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * Agrega un dato adicional a la metadata.
     * @param key Clave del dato
     * @param value Valor del dato
     */
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    @Override
    public String toString() {
        return "Notificacion{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", fecha=" + fecha +
                ", leida=" + leida +
                ", metadata=" + metadata +
                '}';
    }
}
