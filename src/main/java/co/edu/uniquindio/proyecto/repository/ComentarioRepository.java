package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Comentario;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Document(collection = "comentarios")

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    List<Comentario> findByReporteId(String reporteId);
}
