package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByCorreo(String correo); // Buscar usuario por correo

    boolean existsByCorreo(String correo); // Verificar si un correo ya está registrado
}
