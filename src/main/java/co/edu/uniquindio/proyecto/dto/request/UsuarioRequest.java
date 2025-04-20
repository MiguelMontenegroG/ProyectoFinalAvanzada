package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo", example = "Juan Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    @Schema(description = "Correo electrónico", example = "juan@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "Mínimo 8 caracteres")
    @Schema(description = "Contraseña", example = "ClaveSegura123!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Formato de teléfono inválido")
    @Schema(description = "Teléfono", example = "+573001234567")
    private String telefono;

    @Schema(description = "Dirección", example = "Carrera 15 #45-32")
    private String direccion;

    @Schema(description = "Ciudad", example = "Armenia")
    private String ciudad;

    @Schema(description = "Barrio", example = "La Castellana")
    private String barrio;

    @Schema(description = "URL foto de perfil", example = "https://cloudinary.com/images/profile123.jpg")
    private String fotoPerfil;

    @Size(max = 500, message = "Máximo 500 caracteres")
    @Schema(description = "Biografía", example = "Ciudadano comprometido")
    private String biografia;

    @Schema(description = "Rol del usuario", example = "cliente")
    private String rol;
}