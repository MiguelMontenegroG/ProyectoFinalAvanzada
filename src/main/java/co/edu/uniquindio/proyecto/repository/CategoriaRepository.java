package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
}
