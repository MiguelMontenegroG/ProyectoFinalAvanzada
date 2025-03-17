package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    // Guardar un nuevo reporte
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    // Obtener un reporte por ID
    public Optional<Reporte> obtenerReportePorId(String id) {
        return reporteRepository.findById(id);
    }

    // Obtener todos los reportes
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    // Obtener reportes por estado
    public List<Reporte> obtenerReportesPorEstado(String estado) {
        return reporteRepository.findByEstado(estado);
    }

    // Obtener reportes por usuario
    public List<Reporte> obtenerReportesPorUsuario(String usuarioId) {
        return reporteRepository.findByUsuarioId(usuarioId);
    }

    // Actualizar un reporte
    public Reporte actualizarReporte(String id, Reporte reporteActualizado) {
        if (reporteRepository.existsById(id)) {
            reporteActualizado.setId(id);
            return reporteRepository.save(reporteActualizado);
        }
        throw new RuntimeException("No se encontró el reporte con ID: " + id);
    }

    // Eliminar un reporte por ID
    public void eliminarReporte(String id) {
        if (reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se encontró el reporte con ID: " + id);
        }
    }
}
