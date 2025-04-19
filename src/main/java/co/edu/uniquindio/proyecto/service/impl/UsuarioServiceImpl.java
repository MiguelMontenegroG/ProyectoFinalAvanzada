package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.dto.request.ActivacionDTO;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        // Generar código y fecha de expiración
        usuario.generarCodigoActivacion();

        // Guardar el usuario en la base de datos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Imprimir en consola para pruebas
        System.out.println("=== NUEVO USUARIO REGISTRADO ===");
        System.out.println("Correo: " + usuarioGuardado.getCorreo());
        System.out.println("Código de activación: " + usuarioGuardado.getCodigoActivacion());
        System.out.println("Expira en: " + usuarioGuardado.getFechaExpiracionCodigo());

        return usuarioGuardado;
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

    @Override
    public void activarCuenta(ActivacionDTO dto) {
        Usuario usuario = usuarioRepository.findByCorreo(dto.correo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + dto.correo()));

        if (usuario.getCodigoActivacion() == null || usuario.getFechaExpiracionCodigo() == null) {
            throw new RuntimeException("No hay un código de activación generado para este usuario.");
        }

        if (!usuario.getCodigoActivacion().equals(dto.codigo())) {
            throw new RuntimeException("El código de activación es incorrecto.");
        }

        if (usuario.getFechaExpiracionCodigo().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El código de activación ha expirado.");
        }

        usuario.setActivo(true);
        usuario.limpiarCodigos();
        usuarioRepository.save(usuario);
    }

}

