package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO para solicitar recuperación de contraseña")
@Getter
@Setter
public class RecuperacionRequest {

    @Schema(description = "Correo electrónico registrado", example = "usuario@example.com", required = true)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    private String correo;
}