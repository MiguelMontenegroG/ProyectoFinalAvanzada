package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    Notificacion crearNotificacion(Notificacion notificacion);

    List<Notificacion> obtenerNotificacionesPorUsuario(String usuarioId);

    Optional<Notificacion> marcarComoLeida(String id);

    boolean eliminarNotificacion(String id);
}
