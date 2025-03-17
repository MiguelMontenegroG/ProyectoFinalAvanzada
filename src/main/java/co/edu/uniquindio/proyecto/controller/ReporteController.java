package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "API para gestionar los reportes ciudadanos sobre incidentes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @PostMapping
    @Operation(summary = "Crear un nuevo reporte de incidente")
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte reporte) {
        return ResponseEntity.ok(reporteService.crearReporte(reporte));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por su ID")
    public ResponseEntity<Reporte> obtenerReporte(@PathVariable String id) {
        Optional<Reporte> reporte = reporteService.obtenerReportePorId(id);
        return reporte.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar todos los reportes registrados")
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        return ResponseEntity.ok(reporteService.obtenerTodosLosReportes());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener reportes filtrados por estado")
    public ResponseEntity<List<Reporte>> obtenerReportesPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(reporteService.obtenerReportesPorEstado(estado));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtener reportes realizados por un usuario específico")
    public ResponseEntity<List<Reporte>> obtenerReportesPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(reporteService.obtenerReportesPorUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar la información de un reporte")
    public ResponseEntity<Reporte> actualizarReporte(@PathVariable String id, @RequestBody Reporte reporte) {
        return ResponseEntity.ok(reporteService.actualizarReporte(id, reporte));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte por su ID")
    public ResponseEntity<Void> eliminarReporte(@PathVariable String id) {
        reporteService.eliminarReporte(id);
        return ResponseEntity.noContent().build();
    }
}