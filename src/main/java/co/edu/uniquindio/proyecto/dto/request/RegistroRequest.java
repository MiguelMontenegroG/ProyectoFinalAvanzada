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
    private String rol;

    // Getters y Setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
