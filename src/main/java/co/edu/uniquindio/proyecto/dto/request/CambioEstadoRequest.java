package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Petición para cambiar el estado de un reporte")
@Data
public class CambioEstadoRequest {

    @Schema(description = "Nuevo estado del reporte", example = "verificado", required = true)
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Schema(description = "Motivo del cambio de estado", example = "Verificado por moderador", required = true)
    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;
}
