package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "El ID del reporte debe ser un UUID válido")
    private String reporteId;

    // Si quieres incluir el ID del usuario que realiza el comentario, lo puedes agregar aquí.
    @Schema(description = "ID del usuario que hace el comentario", example = "user-123", required = true)
    @NotBlank(message = "El ID del usuario es obligatorio")
    private String usuarioId;

    // Si quieres añadir la fecha de creación, puedes hacerlo de esta manera:
    @Schema(description = "Fecha en que se realiza el comentario", example = "2025-04-18T14:30:00", required = true)
    private String fechaCreacion;
}
