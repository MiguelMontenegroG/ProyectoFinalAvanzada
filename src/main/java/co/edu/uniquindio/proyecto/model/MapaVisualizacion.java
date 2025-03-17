package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Schema(description = "Configuración de visualización del mapa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapaVisualizacion {

    @Schema(description = "Centro del mapa")
    @Builder.Default
    private Centro centro = new Centro();

    @Schema(description = "Nivel de zoom del mapa", minimum = "1", maximum = "20", example = "10")
    @Builder.Default
    private int zoom = 10;

    @Schema(description = "Lista de marcadores en el mapa")
    @Builder.Default
    private List<Marcador> marcadores = new ArrayList<>();

    /**
     * Clase interna que representa el centro del mapa.
     */
    @Schema(description = "Centro del mapa con coordenadas geográficas")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Centro {
        @Schema(description = "Latitud del centro del mapa", example = "4.6097")
        @Builder.Default
        private double latitud = 0.0;

        @Schema(description = "Longitud del centro del mapa", example = "-74.0817")
        @Builder.Default
        private double longitud = 0.0;
    }

    /**
     * Clase interna que representa un marcador en el mapa.
     */
    @Schema(description = "Marcador geográfico en el mapa")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Marcador {
        @Schema(description = "ID del marcador", example = "550e8400-e29b-41d4-a716-446655440000")
        @Builder.Default
        private String id = UUID.randomUUID().toString();

        @Schema(description = "Coordenada latitud del marcador", example = "4.6097")
        private double latitud;

        @Schema(description = "Coordenada longitud del marcador", example = "-74.0817")
        private double longitud;

        @Schema(description = "Tipo de marcador", example = "reporte", allowableValues = {"reporte", "casa", "emergencia"})
        private String tipo;

        @Schema(description = "Información adicional", example = "Bache en la vía")
        private String info;
    }
}
