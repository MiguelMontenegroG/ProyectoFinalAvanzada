package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.model.Notificacion;
import co.edu.uniquindio.proyecto.repository.NotificacionRepository;
import co.edu.uniquindio.proyecto.service.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionServiceImpl(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public List<Notificacion> obtenerNotificacionesPorUsuario(String usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Optional<Notificacion> marcarComoLeida(String id) {
        Optional<Notificacion> notificacionOpt = notificacionRepository.findById(id);
        notificacionOpt.ifPresent(notificacion -> {
            notificacion.setLeida(true);
            notificacionRepository.save(notificacion);
        });
        return notificacionOpt;
    }

    @Override
    public boolean eliminarNotificacion(String id) {
        if (notificacionRepository.existsById(id)) {
            notificacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
