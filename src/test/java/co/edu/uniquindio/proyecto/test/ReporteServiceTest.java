package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyecto.service.impl.ReporteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    private Reporte reporte;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crea un reporte de prueba
        reporte = new Reporte();
        reporte.setId("r1");
        reporte.setTitulo("Hueco en la vía");
        reporte.setDescripcion("Hay un hueco gigante frente al parque principal");
        reporte.setEstado("PENDIENTE");
        reporte.setUsuarioId("u1");
    }

    @Test
    public void testCrearReporte() {
        when(reporteRepository.save(any(Reporte.class))).thenReturn(reporte);

        Reporte creado = reporteService.crearReporte(reporte);

        assertNotNull(creado);
        assertEquals("r1", creado.getId());
        verify(reporteRepository, times(1)).save(reporte);
    }

    @Test
    public void testObtenerReportePorId() {
        when(reporteRepository.findById("r1")).thenReturn(Optional.of(reporte));

        Optional<Reporte> encontrado = reporteService.obtenerReportePorId("r1");

        assertTrue(encontrado.isPresent());
        assertEquals("r1", encontrado.get().getId());
    }

    @Test
    public void testObtenerTodosLosReportes() {
        List<Reporte> lista = List.of(reporte);
        when(reporteRepository.findAll()).thenReturn(lista);

        List<Reporte> reportes = reporteService.obtenerTodosLosReportes();

        assertFalse(reportes.isEmpty());
        assertEquals(1, reportes.size());
    }

    @Test
    public void testActualizarReporte() {
        Reporte actualizado = new Reporte();
        actualizado.setTitulo("Hueco corregido");
        actualizado.setDescripcion("Ya lo arreglaron");
        actualizado.setEstado("SOLUCIONADO");

        when(reporteRepository.existsById("r1")).thenReturn(true);
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(inv -> inv.getArgument(0));

        Reporte result = reporteService.actualizarReporte("r1", actualizado);

        assertEquals("r1", result.getId());
        assertEquals("Hueco corregido", result.getTitulo());
    }

    @Test
    public void testEliminarReporte() {
        when(reporteRepository.existsById("r1")).thenReturn(true);
        doNothing().when(reporteRepository).deleteById("r1");

        assertDoesNotThrow(() -> reporteService.eliminarReporte("r1"));

        verify(reporteRepository, times(1)).deleteById("r1");
    }

    @Test
    public void testEliminarReporteInexistente() {
        when(reporteRepository.existsById("r2")).thenReturn(false);

        Exception ex = assertThrows(RuntimeException.class, () -> reporteService.eliminarReporte("r2"));
        assertEquals("No se encontró el reporte con ID: r2", ex.getMessage());
    }
}
