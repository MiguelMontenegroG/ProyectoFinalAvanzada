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

    // Getters y Setters (ESSENCIALES)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}