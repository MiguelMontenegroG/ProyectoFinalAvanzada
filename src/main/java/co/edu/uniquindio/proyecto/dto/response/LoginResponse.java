package co.edu.uniquindio.proyecto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Respuesta tras un inicio de sesión exitoso")
@Data
@AllArgsConstructor
@Builder

public class LoginResponse {

    @Schema(description = "Token de autenticación JWT", example = "eyJhbGciOiJIUzI1...")
    private String token;

    @Schema(description = "Fecha y hora de expiración del token", example = "2024-03-15T12:00:00Z")
    private String expira;

    @Schema(description = "Rol del usuario", example = "CLIENTE", allowableValues = {"CLIENTE", "MODERADOR", "ADMINISTRADOR"})
    private String rol;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Schema(description = "URL de la foto de perfil", example = "https://cloudinary.com/profile.jpg")
    private String fotoPerfil;

    @Schema(description = "ID único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
    private String idUsuario;

}