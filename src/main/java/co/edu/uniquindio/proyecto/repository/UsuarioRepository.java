package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByCodigoActivacion(String codigo);
    Optional<Usuario> findByCodigoRecuperacion(String codigo);

}
