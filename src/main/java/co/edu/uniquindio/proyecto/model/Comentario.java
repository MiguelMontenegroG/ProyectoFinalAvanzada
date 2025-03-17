package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "comentarios")

@Schema(description = "Comentario en un reporte ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {

    @Schema(description = "Identificador único del comentario", example = "550e8400-e29b-41d4-a716-446655440000")
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Schema(description = "Contenido del comentario", example = "Este problema lleva meses sin resolverse", maxLength = 500, required = true)
    private String texto;

    @Schema(description = "ID del autor del comentario", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String usuarioId;

    @Schema(description = "ID del reporte al que pertenece", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String reporteId;

    @Schema(description = "Fecha y hora de creación", example = "2023-03-15T11:30:00")
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Schema(description = "Número de likes recibidos", example = "15")
    @Builder.Default
    private Integer likes = 0;

    @Schema(description = "IDs de usuarios que dieron like")
    @Builder.Default
    private Set<String> usuariosLike = new HashSet<>();

    /**
     * Añade un like de un usuario si no lo ha dado antes.
     * @param usuarioId ID del usuario que da like
     * @return true si se añadió el like, false si ya lo había dado
     */
    public boolean darLike(String usuarioId) {
        boolean agregado = usuariosLike.add(usuarioId);
        if (agregado) {
            likes = usuariosLike.size();
        }
        return agregado;
    }

    /**
     * Quita un like de un usuario.
     * @param usuarioId ID del usuario que quita el like
     * @return true si se quitó el like, false si no lo había dado
     */
    public boolean quitarLike(String usuarioId) {
        boolean eliminado = usuariosLike.remove(usuarioId);
        if (eliminado) {
            likes = usuariosLike.size();
        }
        return eliminado;
    }
}
