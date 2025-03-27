package co.edu.uniquindio.proyecto.dto.response;

import co.edu.uniquindio.proyecto.model.EstadoReporte;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class AnalyticsResponse {
    private Map<String, Long> categorias;
    private Map<EstadoReporte, Long> estados;
    private Map<String, Map<EstadoReporte, Long>> historial;
    private Map<String, Long> tendencias;
}