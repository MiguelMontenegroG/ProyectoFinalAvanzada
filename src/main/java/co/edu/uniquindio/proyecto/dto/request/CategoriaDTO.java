package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la creación de una categoría")
public record CategoriaDTO(

        @Schema(
                description = "Nombre de la categoría",
                example = "Vías dañadas"
        )
        String nombre,

        @Schema(
                description = "Ícono representativo de la categoría (emoji o nombre)",
                example = "🚧"
        )
        String icono

) {}
