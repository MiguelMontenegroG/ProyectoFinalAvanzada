package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "reportes") // Indica que esta clase se almacena en la colección "reportes" en MongoDB
@Schema(description = "Información de un reporte ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id  // Define este campo como el identificador único en MongoDB
    @Schema(description = "Identificador único del reporte", example = "550e8400-e29b-41d4-a716-446655440000")
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @Schema(description = "Título descriptivo del incidente", example = "Bache peligroso en la vía", maxLength = 120, required = true)
    private String titulo;

    @Schema(description = "Detalles completos del reporte", example = "Hay un bache muy grande que está causando accidentes. Requiere atención urgente.", maxLength = 1000, required = true)
    private String descripcion;

    @Schema(description = "Categorías aplicables al reporte", example = "[\"infraestructura\", \"seguridad\"]", required = true)
    @Builder.Default
    private List<String> categoria = new ArrayList<>();

    @Schema(description = "Nivel de prioridad según impacto", example = "alta", allowableValues = {"baja", "media", "alta", "urgente"})
    @Builder.Default
    private String prioridad = "media";

    @Schema(description = "Ubicación geográfica del incidente", required = true)
    private Ubicacion ubicacion;

    @Schema(description = "URLs de imágenes en Cloudinary", example = "[\"https://cloudinary.com/images/bache1.jpg\"]")
    @Builder.Default
    private List<String> imagenes = new ArrayList<>();

    @Schema(description = "Estado actual del reporte", example = "pendiente", allowableValues = {"pendiente", "en_revision", "verificado", "rechazado", "resuelto"})
    @Builder.Default
    private String estado = "pendiente";

    @Schema(description = "Historial completo de cambios de estado")
    @Builder.Default
    private List<HistorialEstado> historialEstados = new ArrayList<>();

    @Schema(description = "Total de votos 'Es importante'", example = "42")
    @Builder.Default
    private Integer likes = 0;

    @Schema(description = "Límite para verificación automática", example = "50")
    @Builder.Default
    private Integer umbralVerificacion = 50;

    @Schema(description = "ID del usuario creador", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    private String usuarioId;

    @Schema(description = "Fecha y hora de creación", example = "2023-03-15T10:30:00")
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    /**
     * Método para cambiar el estado del reporte y registrar en el historial
     */
    public void cambiarEstado(String nuevoEstado, String motivo) {
        this.estado = nuevoEstado;
        this.historialEstados.add(new HistorialEstado(nuevoEstado, motivo));
    }

    /**
     * Método para incrementar los likes y verificar si se alcanza el umbral de verificación
     */
    public void incrementarLikes() {
        this.likes++;
        if (this.likes >= this.umbralVerificacion && "pendiente".equals(this.estado)) {
            cambiarEstado("verificado", "Verificado automáticamente por umbral de likes");
        }
    }
}
