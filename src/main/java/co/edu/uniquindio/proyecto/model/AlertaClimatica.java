package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "alertas_climaticas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertaClimatica {

    @Id
    private String id;

    private TipoAlerta tipo;
    private Severidad severidad = Severidad.LEVE;
    private Ubicacion areaAfectada;
    private LocalDateTime fechaEmision;
    private LocalDateTime fechaExpiracion;
    private List<String> recomendaciones;

    public enum TipoAlerta {
        TORMENTA, INUNDACION, OLA_CALOR, HELADAS
    }

    public enum Severidad {
        LEVE, MODERADA, GRAVE
    }
}