package co.edu.uniquindio.proyecto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para la creación de una categoría")
public record CategoriaDTO(

        @Schema(
                description = "Nombre de la categoría",
                example = "Vías dañadas"
        )
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Schema(
                description = "Ícono representativo de la categoría (emoji o nombre)",
                example = "🚧"
        )
        String icono

) {}
