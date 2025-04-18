package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.Notificacion;
import co.edu.uniquindio.proyecto.repository.NotificacionRepository;
import co.edu.uniquindio.proyecto.service.NotificacionService;
import co.edu.uniquindio.proyecto.service.impl.NotificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    private Notificacion notificacion;

    @BeforeEach
    public void setUp() {
        notificacion = new Notificacion();
        notificacion.setId("notif123");
        notificacion.setMensaje("Tienes una nueva notificación");
        notificacion.setUsuarioId("user123");
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setLeida(false);
    }

    @Test
    public void crearNotificacionTest() {
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        Notificacion resultado = notificacionService.crearNotificacion(notificacion);

        assertNotNull(resultado);
        assertEquals("notif123", resultado.getId());
        verify(notificacionRepository, times(1)).save(notificacion);
    }

    @Test
    public void obtenerNotificacionesPorUsuarioTest() {
        when(notificacionRepository.findByUsuarioId("user123")).thenReturn(List.of(notificacion));

        List<Notificacion> resultado = notificacionService.obtenerNotificacionesPorUsuario("user123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("user123", resultado.get(0).getUsuarioId());
        verify(notificacionRepository, times(1)).findByUsuarioId("user123");
    }

    @Test
    public void marcarComoLeidaTest() {
        when(notificacionRepository.findById("notif123")).thenReturn(Optional.of(notificacion));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacion);

        Optional<Notificacion> resultado = notificacionService.marcarComoLeida("notif123");

        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().isLeida());
        verify(notificacionRepository).findById("notif123");
        verify(notificacionRepository).save(notificacion);
    }

    @Test
    public void eliminarNotificacionTest() {
        when(notificacionRepository.existsById("notif123")).thenReturn(true);
        doNothing().when(notificacionRepository).deleteById("notif123");

        boolean resultado = notificacionService.eliminarNotificacion("notif123");

        assertTrue(resultado);
        verify(notificacionRepository).existsById("notif123");
        verify(notificacionRepository).deleteById("notif123");
    }

    @Test
    public void eliminarNotificacionNoExisteTest() {
        when(notificacionRepository.existsById("notif999")).thenReturn(false);

        boolean resultado = notificacionService.eliminarNotificacion("notif999");

        assertFalse(resultado);
        verify(notificacionRepository).existsById("notif999");
        verify(notificacionRepository, never()).deleteById(any());
    }
}
