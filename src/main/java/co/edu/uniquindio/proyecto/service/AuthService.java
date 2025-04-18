package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.request.*;
import co.edu.uniquindio.proyecto.dto.response.LoginResponse;
import co.edu.uniquindio.proyecto.model.Usuario;

public interface AuthService {
    Usuario registrarUsuario(RegistroRequest request);
    void activarCuenta(ActivacionRequest request);
    LoginResponse login(LoginRequest request);
    void solicitarRecuperacionContrasena(RecuperacionRequest request);
    void cambiarContrasena(CambioContrasenaRequest request);
    void actualizarPerfil(String usuarioId, ActualizarPerfilRequest request);
}
