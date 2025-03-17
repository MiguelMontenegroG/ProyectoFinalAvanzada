package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Petición para iniciar sesión")
@Data
public class LoginRequest {

    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com", required = true)
    @Email(message = "El correo debe ser válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @Schema(description = "Contraseña del usuario", example = "Str0ngP@ssw0rd", required = true)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
