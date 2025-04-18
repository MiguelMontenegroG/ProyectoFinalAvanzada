package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Reporte;

import java.util.List;
import java.util.Optional;

public interface ReporteService {

    Reporte crearReporte(Reporte reporte);

    Optional<Reporte> obtenerReportePorId(String id);

    List<Reporte> obtenerTodosLosReportes();

    List<Reporte> obtenerReportesPorEstado(String estado);

    List<Reporte> obtenerReportesPorUsuario(String usuarioId);

    Reporte actualizarReporte(String id, Reporte reporteActualizado);

    void eliminarReporte(String id);
}
