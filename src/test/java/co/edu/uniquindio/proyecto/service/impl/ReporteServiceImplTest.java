package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReporteServiceImplTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reporte = Reporte.builder()
                .id("id-123")
                .titulo("Test Report")
                .descripcion("Description")
                .usuarioId("user-1")
                .categoria(new ArrayList<>(List.of("infraestructura")))
                .ubicacion(Reporte.Ubicacion.builder().latitud(1.0).longitud(2.0).direccion("dir").mapaUrl("url").build())
                .estado("PENDIENTE")
                .build();
    }

    @Test
    void testCrearReporte() {
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        Reporte result = reporteService.crearReporte(reporte);

        assertNotNull(result);
        assertEquals("Test Report", result.getTitulo());
        verify(reporteRepository).save(reporte);
    }

    @Test
    void testObtenerReportePorIdFound() {
        when(reporteRepository.findById("id-123")).thenReturn(Optional.of(reporte));

        Optional<Reporte> result = reporteService.obtenerReportePorId("id-123");

        assertTrue(result.isPresent());
        assertEquals("id-123", result.get().getId());
    }

    @Test
    void testObtenerReportePorIdNotFound() {
        when(reporteRepository.findById("id-999")).thenReturn(Optional.empty());

        Optional<Reporte> result = reporteService.obtenerReportePorId("id-999");

        assertFalse(result.isPresent());
    }

    @Test
    void testObtenerTodosLosReportes() {
        List<Reporte> lista = List.of(reporte);
        when(reporteRepository.findAll()).thenReturn(lista);

        List<Reporte> result = reporteService.obtenerTodosLosReportes();

        assertEquals(1, result.size());
        assertEquals(reporte, result.get(0));
    }

    @Test
    void testObtenerReportesPorEstado() {
        List<Reporte> lista = List.of(reporte);
        when(reporteRepository.findByEstado("PENDIENTE")).thenReturn(lista);

        List<Reporte> result = reporteService.obtenerReportesPorEstado("PENDIENTE");

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerReportesPorUsuario() {
        List<Reporte> lista = List.of(reporte);
        when(reporteRepository.findByUsuarioId("user-1")).thenReturn(lista);

        List<Reporte> result = reporteService.obtenerReportesPorUsuario("user-1");

        assertEquals(1, result.size());
    }

    @Test
    void testActualizarReporteExists() {
        when(reporteRepository.existsById("id-123")).thenReturn(true);
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        Reporte updated = reporteService.actualizarReporte("id-123", reporte);

        assertNotNull(updated);
        assertEquals("Test Report", updated.getTitulo());
        verify(reporteRepository).save(reporte);
    }

    @Test
    void testActualizarReporteNotExists() {
        when(reporteRepository.existsById("id-999")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reporteService.actualizarReporte("id-999", reporte);
        });

        assertTrue(ex.getMessage().contains("No se encontró el reporte"));
    }

    @Test
    void testEliminarReporteExists() {
        when(reporteRepository.existsById("id-123")).thenReturn(true);
        doNothing().when(reporteRepository).deleteById("id-123");

        assertDoesNotThrow(() -> reporteService.eliminarReporte("id-123"));
        verify(reporteRepository).deleteById("id-123");
    }

    @Test
    void testEliminarReporteNotExists() {
        when(reporteRepository.existsById("id-999")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reporteService.eliminarReporte("id-999");
        });

        assertTrue(ex.getMessage().contains("No se encontró el reporte"));
    }
}
