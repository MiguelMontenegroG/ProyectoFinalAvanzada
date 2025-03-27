package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "usuarios") // Indica que es un documento de MongoDB
@Schema(description = "Información del usuario en el sistema")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Usuario {

    @Id
    @Schema(description = "Identificador único del usuario", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String id; // MongoDB manejará el ID si es null al guardar

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez", maxLength = 100, required = true)
    private String nombre;

    @Schema(description = "Correo electrónico válido", example = "juan@example.com", required = true)
    private String correo;

    @Schema(description = "Número de teléfono internacional", example = "+573001234567", pattern = "^\\+?[0-9]{7,15}$")
    private String telefono;

    @Schema(description = "Dirección física completa", example = "Carrera 15 #45-32")
    private String direccion;

    @Schema(description = "Ciudad de residencia", example = "Armenia")
    private String ciudad;

    @Schema(description = "Barrio o sector específico", example = "La Castellana")
    private String barrio;

    @Schema(description = "URL de la imagen de perfil en Cloudinary", example = "https://cloudinary.com/images/profile123.jpg")
    private String fotoPerfil;

    @Schema(description = "Descripción personal del usuario", example = "Ciudadano comprometido con mejorar mi comunidad", maxLength = 500)
    private String biografia;

    @Schema(description = "Estado de la cuenta (activada/desactivada)", example = "true", required = true)
    private boolean activo = true;

    @Schema(description = "Rol del usuario en el sistema", example = "cliente", allowableValues = {"cliente", "moderador", "administrador"})
    private String rol = "cliente";

    @Schema(description = "Contraseña encriptada", required = true, minLength = 8)
    private String password;

    // Campos para autenticación
    private String codigoActivacion;
    private LocalDateTime fechaExpiracionCodigo;
    private String codigoRecuperacion;

    // Métodos auxiliares
    public void generarCodigoActivacion() {
        this.codigoActivacion = UUID.randomUUID().toString();
        this.fechaExpiracionCodigo = LocalDateTime.now().plusHours(24);
    }

    public void generarCodigoRecuperacion() {
        this.codigoRecuperacion = UUID.randomUUID().toString();
        this.fechaExpiracionCodigo = LocalDateTime.now().plusHours(1);
    }

    public void limpiarCodigos() {
        this.codigoActivacion = null;
        this.codigoRecuperacion = null;
        this.fechaExpiracionCodigo = null;
    }
}
