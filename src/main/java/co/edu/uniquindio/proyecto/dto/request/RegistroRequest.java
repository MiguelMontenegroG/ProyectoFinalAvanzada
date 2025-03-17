package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Petición para registrar un nuevo usuario")
@Data
public class RegistroRequest {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", required = true)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Correo electrónico válido", example = "juan@example.com", required = true)
    @Email(message = "El correo debe ser válido")
    private String correo;

    @Schema(description = "Contraseña segura", example = "Str0ngP@ssw0rd", required = true)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
}
