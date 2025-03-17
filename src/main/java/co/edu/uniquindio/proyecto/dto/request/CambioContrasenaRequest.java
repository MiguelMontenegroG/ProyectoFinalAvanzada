package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "Petición para cambiar la contraseña")
@Data
public class CambioContrasenaRequest {

    @Schema(description = "Código de recuperación enviado por correo", example = "123456", required = true)
    @NotBlank(message = "El código de recuperación es obligatorio")
    private String codigo;

    @Schema(description = "Nueva contraseña", example = "Nuev0P@ssw0rd", required = true)
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String nuevaContrasena;
}
