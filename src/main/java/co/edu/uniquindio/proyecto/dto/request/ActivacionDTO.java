package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos necesarios para activar la cuenta de un usuario")
public record ActivacionDTO(

        @Schema(description = "Correo del usuario", example = "juan@example.com")
        String correo,

        @Schema(description = "Código de activación recibido por correo", example = "abc123")
        String codigo

) {}

