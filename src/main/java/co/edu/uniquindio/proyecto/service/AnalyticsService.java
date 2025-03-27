package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.response.AnalyticsResponse;
import co.edu.uniquindio.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyecto.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ReporteRepository reporteRepository;

    public AnalyticsResponse generarReportes(List<String> metricas, LocalDate fechaInicio, LocalDate fechaFin) {
        AnalyticsResponse.AnalyticsResponseBuilder builder = AnalyticsResponse.builder();

        for (String metrica : metricas) {
            switch (metrica.toLowerCase()) {
                case "categorias":
                    builder.categorias(calcularMetricaCategorias());
                    break;
                case "estados":
                    builder.estados(calcularMetricaEstados());
                    break;
                case "historial":
                    builder.historial(calcularMetricaHistorial());
                    break;
                case "tendencias":
                    builder.tendencias(calcularMetricaTendencias(fechaInicio, fechaFin));
                    break;
            }
        }

        return builder.build();
    }

    private Map<String, Long> calcularMetricaCategorias() {
        return reporteRepository.countByCategoria().stream()
                .collect(Collectors.toMap(
                        ReporteRepository.CategoriaCount::get_id,
                        item -> (long) item.getCount()
                ));
    }

    private Map<EstadoReporte, Long> calcularMetricaEstados() {
        return reporteRepository.countTotalByEstado().stream()
                .collect(Collectors.toMap(
                        item -> EstadoReporte.fromString(item.get_id()),
                        item -> (long) item.getCount()
                ));
    }

    private Map<String, Map<EstadoReporte, Long>> calcularMetricaHistorial() {
        Map<String, Map<EstadoReporte, Long>> resultado = new HashMap<>();

        reporteRepository.countHistorialEstados().forEach(item -> {
            String fecha = item.get_id().getFecha();
            EstadoReporte estado = EstadoReporte.fromString(item.get_id().getEstado());

            resultado.computeIfAbsent(fecha, k -> new HashMap<>())
                    .merge(estado, (long) item.getCount(), Long::sum);
        });

        return resultado;
    }

    private Map<String, Long> calcularMetricaTendencias(LocalDate fechaInicio, LocalDate fechaFin) {
        // Convertir LocalDate a LocalDateTime (inicio del día para fechaInicio, fin del día para fechaFin)
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : null;

        // Si no se especifican fechas, obtener todos los datos
        if (inicio == null && fin == null) {
            inicio = LocalDateTime.of(1970, 1, 1, 0, 0); // Fecha muy antigua
            fin = LocalDateTime.now();
        } else if (inicio == null) {
            inicio = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else if (fin == null) {
            fin = LocalDateTime.now();
        }

        return reporteRepository.countReportesPorFecha(inicio, fin).stream()
                .collect(Collectors.toMap(
                        ReporteRepository.FechaCount::get_id,
                        fc -> (long) fc.getCount()
                ));
    }

    // Método adicional si necesitas filtrar por estado también
    private Map<String, Long> calcularMetricaTendenciasPorEstado(LocalDate fechaInicio, LocalDate fechaFin, EstadoReporte estado) {
        LocalDateTime inicio = fechaInicio != null ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fin = fechaFin != null ? fechaFin.atTime(LocalTime.MAX) : null;

        if (inicio == null && fin == null) {
            inicio = LocalDateTime.of(1970, 1, 1, 0, 0);
            fin = LocalDateTime.now();
        } else if (inicio == null) {
            inicio = LocalDateTime.of(1970, 1, 1, 0, 0);
        } else if (fin == null) {
            fin = LocalDateTime.now();
        }

        return reporteRepository.countReportesPorFechaYEstado(inicio, fin, estado).stream()
                .collect(Collectors.toMap(
                        ReporteRepository.FechaCount::get_id,
                        fc -> (long) fc.getCount()
                ));
    }
}