package co.edu.uniquindio.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reportes") // Indica que esta clase se almacena en la colección "reportes" en MongoDB

@Schema(description = "Información de ubicación geográfica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    @Schema(description = "Coordenada geográfica de latitud", example = "4.5454", minimum = "-90", maximum = "90", required = true)
    private Double latitud;

    @Schema(description = "Coordenada geográfica de longitud", example = "-75.6757", minimum = "-180", maximum = "180", required = true)
    private Double longitud;

    @Schema(description = "Dirección humana legible", example = "Calle 21 #15-32, Armenia")
    private String direccion;

    @Schema(description = "URL de Mapbox para visualización", example = "https://mapbox.com/map/12345")
    private String mapaUrl;
}
