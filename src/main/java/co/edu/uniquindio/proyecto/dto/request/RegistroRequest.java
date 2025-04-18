package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Petición para registrar un nuevo usuario")
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

    @Schema(description = "Número de teléfono del usuario", example = "+573001234567", required = true)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Schema(description = "Rol del usuario", example = "ROLE_USER", required = true)
    @NotBlank(message = "El rol es obligatorio")
    // (Opcional) validación de roles aceptados, si quieres limitarlo explícitamente
    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "El rol debe ser ROLE_USER o ROLE_ADMIN")
    private String rol;
}
