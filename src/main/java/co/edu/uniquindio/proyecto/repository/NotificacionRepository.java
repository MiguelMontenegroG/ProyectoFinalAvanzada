package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByUsuarioId(String usuarioId);
}
