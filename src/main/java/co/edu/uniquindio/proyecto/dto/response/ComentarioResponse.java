package co.edu.uniquindio.proyecto.dto.response;

import co.edu.uniquindio.proyecto.model.Comentario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Schema(description = "Respuesta con la información de un comentario")
@Data
@Getter
public class ComentarioResponse {

    @Schema(description = "ID del comentario", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String id;

    @Schema(description = "Contenido del comentario", example = "Este problema sigue sin resolverse", required = true)
    private String texto;

    @Schema(description = "ID del reporte al que pertenece el comentario", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String reporteId;

    @Schema(description = "ID del usuario que hizo el comentario", example = "user-123", required = true)
    private String usuarioId;

    @Schema(description = "Fecha de creación del comentario", example = "2025-04-18T14:30:00", required = true)
    private String fechaCreacion;

    @Schema(description = "Cantidad likes", example = "31", required = true)
    private final Integer likes;

    public ComentarioResponse(Comentario comentario) {
        this.id = comentario.getId();
        this.texto = comentario.getTexto();
        this.usuarioId = comentario.getUsuarioId();
        this.reporteId = comentario.getReporteId();
        this.fechaCreacion = comentario.getFechaCreacion().toString();
        this.likes = comentario.getLikes();
    }
}
