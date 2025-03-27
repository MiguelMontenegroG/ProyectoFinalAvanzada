package co.edu.uniquindio.proyecto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO para actualización de perfil de usuario")
@Getter
@Setter
public class ActualizarPerfilRequest {

    @Schema(description = "Nuevo nombre del usuario", example = "María González")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Schema(description = "Nuevo número de teléfono", example = "+573001234567")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe tener formato internacional válido")
    private String telefono;

    @Schema(description = "Nueva dirección física", example = "Calle 123 #45-67")
    private String direccion;

    @Schema(description = "URL de la nueva foto de perfil", example = "https://cloudinary.com/imagen.jpg")
    private String fotoPerfil;

    @Schema(description = "Nueva biografía del usuario", example = "Amante de la comunidad y su seguridad")
    @Size(max = 500, message = "La biografía no puede exceder los 500 caracteres")
    private String biografia;
}