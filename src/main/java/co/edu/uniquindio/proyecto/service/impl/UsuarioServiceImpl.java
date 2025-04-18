package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.service.UsuarioService;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuario(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null); // Si no se encuentra el usuario, se devuelve null
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizarUsuario(String id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id); // No perder el ID al actualizar
            return usuarioRepository.save(usuario);
        }
        return null; // Si no existe, retorna null
    }

    @Override
    public void eliminarUsuario(String id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }
}
