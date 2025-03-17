package co.edu.uniquindio.proyecto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Respuesta tras un inicio de sesión exitoso")
@Data
@AllArgsConstructor
public class LoginResponse {

    @Schema(description = "Token de autenticación JWT", example = "eyJhbGciOiJIUzI1...")
    private String token;

    @Schema(description = "Fecha y hora de expiración del token", example = "2024-03-15T12:00:00Z")
    private String expira;
}
