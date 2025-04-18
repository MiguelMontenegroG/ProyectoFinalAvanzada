package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyecto.model.Reporte;
import co.edu.uniquindio.proyecto.service.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReporteServiceTest {

    @Autowired
    private ReporteService reporteService;

    private Reporte baseReporte;

    @BeforeEach
    void setUp() {
        baseReporte = Reporte.builder()
                .titulo("Alcantarilla sin tapa")
                .descripcion("Hay una alcantarilla sin tapa frente al parque principal.")
                .usuarioId("usuario-test-123")
                .categoria(Arrays.asList("infraestructura", "riesgo"))
                .ubicacion(Reporte.Ubicacion.builder()
                        .latitud(4.628963)
                        .longitud(-75.571442)
                        .direccion("Calle 15 #23-45, Barrio San José")
                        .mapaUrl("https://api.mapbox.com/styles/v1/mapbox/streets-v11.html")
                        .build())
                .prioridad("alta")
                .imagenes(Arrays.asList("https://cloudinary.com/image1.jpg"))
                .fechaCreacion(LocalDateTime.now())
                .build();
    }

    @Test
    void crearReporteTest() {
        Reporte creado = reporteService.crearReporte(baseReporte);
        assertNotNull(creado.getId());
        assertEquals("Alcantarilla sin tapa", creado.getTitulo());
        assertEquals(EstadoReporte.PENDIENTE.name(), creado.getEstado());
    }

    @Test
    void obtenerReportePorIdTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        Optional<Reporte> opt = reporteService.obtenerReportePorId(guardado.getId());
        assertTrue(opt.isPresent());
        Reporte encontrado = opt.get();
        assertEquals(guardado.getId(), encontrado.getId());
        assertEquals(guardado.getDescripcion(), encontrado.getDescripcion());
    }

    @Test
    void obtenerTodosLosReportesTest() {
        reporteService.crearReporte(baseReporte);
        List<Reporte> todos = reporteService.obtenerTodosLosReportes();
        assertNotNull(todos);
        assertFalse(todos.isEmpty());
    }

    @Test
    void actualizarReporteTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        guardado.setTitulo("Título modificado");
        Reporte actualizado = reporteService.actualizarReporte(guardado.getId(), guardado);
        assertEquals("Título modificado", actualizado.getTitulo());
    }

    @Test
    void eliminarReporteTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        reporteService.eliminarReporte(guardado.getId());
        Optional<Reporte> opt = reporteService.obtenerReportePorId(guardado.getId());
        assertFalse(opt.isPresent());
    }

    @Test
    void incrementarLikesYVerificarCambioDeEstadoTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        // Colocamos likes justo por debajo del umbral
        guardado.setLikes(guardado.getUmbralVerificacion() - 1);
        reporteService.actualizarReporte(guardado.getId(), guardado);

        // Incrementa un like
        guardado.incrementarLikes();
        Reporte afterLike = reporteService.actualizarReporte(guardado.getId(), guardado);

        assertEquals(EstadoReporte.VERIFICADO.name(), afterLike.getEstado());
        assertFalse(afterLike.getHistorialEstados().isEmpty());
        assertEquals("Verificado automáticamente por umbral de likes",
                afterLike.getHistorialEstados().get(0).getMotivo());
    }

    @Test
    void cambiarEstadoManualTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        guardado.cambiarEstado(EstadoReporte.EN_REVISION, "Comenzamos intervención");
        Reporte afterChange = reporteService.actualizarReporte(guardado.getId(), guardado);

        assertEquals(EstadoReporte.EN_REVISION.name(), afterChange.getEstado());
        assertEquals("Comenzamos intervención", afterChange.getHistorialEstados().get(0).getMotivo());
    }

    @Test
    void agregarImagenYCategoriaTest() {
        Reporte guardado = reporteService.crearReporte(baseReporte);
        // Agregamos nueva imagen y categoría
        guardado.agregarImagen("https://cloudinary.com/image2.jpg");
        guardado.agregarCategoria("ambiental");
        Reporte afterUpdate = reporteService.actualizarReporte(guardado.getId(), guardado);

        assertTrue(afterUpdate.getImagenes().contains("https://cloudinary.com/image2.jpg"));
        assertTrue(afterUpdate.getCategoria().contains("ambiental"));
    }
}
