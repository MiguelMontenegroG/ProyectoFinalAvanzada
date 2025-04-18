package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con ID: " + id));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario actualizarUsuario(String id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el usuario con ID: " + id);
        }
        usuario.setId(id); // mantener el ID original
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el usuario con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
