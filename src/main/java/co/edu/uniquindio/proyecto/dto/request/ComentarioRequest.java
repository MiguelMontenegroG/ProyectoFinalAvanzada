package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Petición para agregar un comentario en un reporte")
@Data
public class ComentarioRequest {

    @Schema(description = "Contenido del comentario", example = "Este problema sigue sin resolverse", required = true)
    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    private String texto;

    @Schema(description = "ID del reporte al que pertenece el comentario", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    @NotBlank(message = "El ID del reporte es obligatorio")
    private String reporteId;
}
