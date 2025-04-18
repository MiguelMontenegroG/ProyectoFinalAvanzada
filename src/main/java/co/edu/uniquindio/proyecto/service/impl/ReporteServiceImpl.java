package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyecto.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;

    @Override
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    @Override
    public Optional<Reporte> obtenerReportePorId(String id) {
        return reporteRepository.findById(id);
    }

    @Override
    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    @Override
    public List<Reporte> obtenerReportesPorEstado(String estado) {
        return reporteRepository.findByEstado(estado);
    }

    @Override
    public List<Reporte> obtenerReportesPorUsuario(String usuarioId) {
        return reporteRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Reporte actualizarReporte(String id, Reporte reporteActualizado) {
        if (reporteRepository.existsById(id)) {
            reporteActualizado.setId(id);
            return reporteRepository.save(reporteActualizado);
        }
        throw new RuntimeException("No se encontró el reporte con ID: " + id);
    }

    @Override
    public void eliminarReporte(String id) {
        if (reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se encontró el reporte con ID: " + id);
        }
    }
}
