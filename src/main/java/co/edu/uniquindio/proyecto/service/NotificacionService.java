package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Notificacion;
import co.edu.uniquindio.proyecto.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    /**
     * Guarda una nueva notificación en la base de datos.
     * @param notificacion Notificación a guardar.
     * @return La notificación guardada.
     */
    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    /**
     * Obtiene todas las notificaciones de un usuario específico.
     * @param usuarioId ID del usuario.
     * @return Lista de notificaciones.
     */
    public List<Notificacion> obtenerNotificacionesPorUsuario(String usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Marca una notificación como leída.
     * @param id ID de la notificación.
     * @return La notificación actualizada, si existe.
     */
    public Optional<Notificacion> marcarComoLeida(String id) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(id);
        notificacionOpt.ifPresent(notificacion -> {
            notificacion.setLeida(true);
            notificacionRepository.save(notificacion);
        });
        return notificacionOpt;
    }

    /**
     * Elimina una notificación por su ID.
     * @param id ID de la notificación.
     * @return True si se eliminó, false si no se encontró.
     */
    public boolean eliminarNotificacion(String id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
