package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.request.ComentarioRequest;
import co.edu.uniquindio.proyecto.dto.response.ComentarioResponse;
import co.edu.uniquindio.proyecto.model.Comentario;
import co.edu.uniquindio.proyecto.repository.ComentarioRepository;
import co.edu.uniquindio.proyecto.service.impl.ComentarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  // Asegúrate de añadir esta línea
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    private ComentarioRequest comentarioRequest;
    private Comentario comentario;

    @BeforeEach
    void setUp() {
        // Definir el comentarioRequest para la creación
        comentarioRequest = new ComentarioRequest();
        comentarioRequest.setTexto("Comentario de prueba");
        comentarioRequest.setReporteId("reporte-123");

        // Crear un Comentario simulado para pruebas
        comentario = new Comentario();
        comentario.setId("comentario-123");
        comentario.setTexto("Comentario de prueba");
        comentario.setReporteId("reporte-123");
        comentario.setUsuarioId("usuario-123");
        comentario.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void crearComentarioTest() {
        // Simulamos el comportamiento del repositorio para guardar el comentario
        when(comentarioRepository.save(any(Comentario.class))).thenReturn(comentario);

        // Llamamos al método de servicio para crear el comentario
        ComentarioResponse resultado = comentarioService.crearComentario(comentarioRequest);

        // Verificamos que el comentario fue creado correctamente
        assertNotNull(resultado);
        assertEquals("Comentario de prueba", resultado.getTexto());
        assertEquals("reporte-123", resultado.getReporteId());
        assertEquals("usuario-123", resultado.getUsuarioId());

        // Verificamos que el repositorio fue llamado para guardar el comentario
        verify(comentarioRepository).save(any(Comentario.class));
    }

    @Test
    void obtenerComentarioPorIdTest() {
        // Simulamos que el repositorio devuelve un comentario por su ID
        when(comentarioRepository.findById("comentario-123")).thenReturn(Optional.of(comentario));

        // Llamamos al método de servicio para obtener el comentario por ID
        Optional<ComentarioResponse> resultado = comentarioService.obtenerComentarioPorId("comentario-123");

        // Verificamos que el resultado está presente en el Optional
        assertTrue(resultado.isPresent(), "El comentario debe estar presente en el Optional");

        // Verificamos que se obtuvo el comentario correctamente
        assertEquals("comentario-123", resultado.get().getId());
        assertEquals("Comentario de prueba", resultado.get().getTexto());
    }

    @Test
    void obtenerComentariosPorReporteTest() {
        // Simulamos que el repositorio devuelve una lista de comentarios asociados a un reporte
        when(comentarioRepository.findByReporteId("reporte-123")).thenReturn(List.of(comentario));

        // Llamamos al método de servicio para obtener los comentarios de un reporte
        List<ComentarioResponse> resultado = comentarioService.obtenerComentariosPorReporte("reporte-123");

        // Verificamos que se obtuvieron los comentarios correctamente
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Comentario de prueba", resultado.get(0).getTexto());
    }

    @Test
    void eliminarComentarioTest() {
        // Usamos lenient() para evitar que Mockito marque la simulación como innecesaria
        lenient().when(comentarioRepository.existsById("comentario-123")).thenReturn(true);
        doNothing().when(comentarioRepository).deleteById("comentario-123");

        // Llamamos al método de servicio para eliminar el comentario
        assertDoesNotThrow(() -> comentarioService.eliminarComentario("comentario-123"));

        // Verificamos que el repositorio fue llamado para eliminar el comentario
        verify(comentarioRepository).deleteById("comentario-123");
    }

}
