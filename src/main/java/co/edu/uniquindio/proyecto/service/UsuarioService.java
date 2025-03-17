package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UserRepository userRepository;

    @Autowired
    public UsuarioService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Crear usuario
    public Usuario crearUsuario(Usuario usuario) {
        if (userRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        return userRepository.save(usuario);
    }

    // Obtener usuario por ID
    public Usuario obtenerUsuario(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return userRepository.findAll();
    }

    // Actualizar usuario
    public Usuario actualizarUsuario(String id, Usuario usuario) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.setId(id); // Mantener el mismo ID
        return userRepository.save(usuario);
    }

    // Eliminar usuario (Desactivar en lugar de borrar)
    public void eliminarUsuario(String id) {
        Usuario usuario = obtenerUsuario(id);
        usuario.setActivo(false); // Desactivamos el usuario en lugar de eliminarlo
        userRepository.save(usuario);
    }
}
