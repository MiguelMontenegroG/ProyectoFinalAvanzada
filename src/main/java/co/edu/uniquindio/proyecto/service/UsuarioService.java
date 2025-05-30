package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.dto.ActivacionDTO;

import java.util.List;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);

    Usuario obtenerUsuario(String id);

    List<Usuario> listarUsuarios();

    Usuario actualizarUsuario(String id, Usuario usuario);

    void eliminarUsuario(String id);

    void activarCuenta(ActivacionDTO dto);
}
