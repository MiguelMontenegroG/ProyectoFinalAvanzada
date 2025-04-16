package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Reporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    private Reporte reporte;

    @BeforeEach
    void setUp() {
        reporte = new Reporte();
        reporte.setTitulo("Hueco en la calle");
        reporte.setUsuarioId("user123");
        reporte.setEstado("EN_PROCESO");
        reporte.setFechaCreacion(LocalDateTime.now());
        reporteRepository.save(reporte);
    }

    @Test
    void testBuscarPorUsuarioId() {
        List<Reporte> reportes = reporteRepository.findByUsuarioId("user123");
        assertEquals(1, reportes.size());
        assertEquals("Hueco en la calle", reportes.get(0).getTitulo());
    }

    @Test
    void testContarPorEstado() {
        long cantidad = reporteRepository.countByEstado("EN_PROCESO");
        assertEquals(1, cantidad);
    }
}
