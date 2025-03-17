package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Reporte;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Document(collection = "reportes") // Indica que esta clase se almacena en la colección "reportes" en MongoDB


@Repository
public interface ReporteRepository extends MongoRepository<Reporte, String> {

    // Buscar reportes por usuario
    List<Reporte> findByUsuarioId(String usuarioId);

    // Buscar reportes por estado (pendiente, verificado, resuelto, etc.)
    List<Reporte> findByEstado(String estado);
}
