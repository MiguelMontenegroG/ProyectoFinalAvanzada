package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "reportes")
@Schema(description = "Información de un reporte ciudadano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
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

    @Schema(description = "Estado actual del reporte", example = "pendiente")
    @Builder.Default
    private String estado = EstadoReporte.PENDIENTE.name(); // Cambiado a String pero inicializado con enum

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
     * @param nuevoEstado Nuevo estado del reporte (como String)
     * @param motivo Motivo del cambio de estado
     */
    public void cambiarEstado(String nuevoEstado, String motivo) {
        EstadoReporte estadoValidado = EstadoReporte.fromString(nuevoEstado);
        this.estado = estadoValidado.name();
        registrarEnHistorial(estadoValidado, motivo);
    }

    /**
     * Método para cambiar el estado del reporte y registrar en el historial
     * @param nuevoEstado Nuevo estado del reporte (como Enum)
     * @param motivo Motivo del cambio de estado
     */
    public void cambiarEstado(EstadoReporte nuevoEstado, String motivo) {
        this.estado = nuevoEstado.name();
        registrarEnHistorial(nuevoEstado, motivo);
    }

    /**
     * Método para obtener el estado actual como Enum
     * @return EstadoReporte correspondiente al estado actual
     */
    public EstadoReporte getEstadoEnum() {
        return EstadoReporte.fromString(this.estado);
    }

    private void registrarEnHistorial(EstadoReporte estado, String motivo) {
        this.historialEstados.add(HistorialEstado.builder()
                .estado(estado)
                .fecha(LocalDateTime.now())
                .motivo(motivo)
                .build());
    }

    /**
     * Método para incrementar los likes y verificar si se alcanza el umbral de verificación
     */
    public void incrementarLikes() {
        this.likes++;
        if (this.likes >= this.umbralVerificacion && EstadoReporte.PENDIENTE.name().equals(this.estado)) {
            cambiarEstado(EstadoReporte.VERIFICADO, "Verificado automáticamente por umbral de likes");
        }
    }

    /**
     * Método para agregar una imagen al reporte
     * @param urlImagen URL de la imagen en Cloudinary
     */
    public void agregarImagen(String urlImagen) {
        this.imagenes.add(urlImagen);
    }

    /**
     * Método para agregar una categoría al reporte
     * @param categoria Nueva categoría a agregar
     */
    public void agregarCategoria(String categoria) {
        if (!this.categoria.contains(categoria)) {
            this.categoria.add(categoria);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ubicacion {
        @Schema(description = "Latitud geográfica", example = "4.628963")
        private Double latitud;

        @Schema(description = "Longitud geográfica", example = "-75.571442")
        private Double longitud;

        @Schema(description = "Dirección humana legible", example = "Calle 15 # 23-45, Barrio San José")
        private String direccion;

        @Schema(description = "URL de Mapbox para visualización", example = "https://api.mapbox.com/styles/v1/mapbox/streets-v11.html")
        private String mapaUrl;
    }
}