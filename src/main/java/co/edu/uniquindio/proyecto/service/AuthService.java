package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.request.*;
import co.edu.uniquindio.proyecto.dto.response.LoginResponse;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UserRepository;
import co.edu.uniquindio.proyecto.utils.JwtUtils;
import co.edu.uniquindio.proyecto.utils.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Transactional
    public Usuario registrarUsuario(RegistroRequest request) {
        if (userRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(false)
                .rol("CLIENTE") // Ahora en mayúsculas para consistencia
                .codigoActivacion(UUID.randomUUID().toString())
                .fechaExpiracionCodigo(LocalDateTime.now().plusHours(24))
                .build();

        Usuario usuarioGuardado = userRepository.save(usuario);
        emailService.enviarCorreoActivacion(usuario.getCorreo(), usuario.getCodigoActivacion());

        return usuarioGuardado;
    }

    @Transactional
    public void activarCuenta(ActivacionRequest request) {
        Usuario usuario = userRepository.findByCodigoActivacion(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Código de activación inválido"));

        if (LocalDateTime.now().isAfter(usuario.getFechaExpiracionCodigo())) {
            throw new RuntimeException("Código de activación expirado");
        }

        usuario.setActivo(true);
        usuario.setCodigoActivacion(null);
        usuario.setFechaExpiracionCodigo(null);
        userRepository.save(usuario);
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("La cuenta no está activada. Por favor revisa tu correo.");
        }

        String token = jwtUtils.generarToken(usuario);
        return LoginResponse.builder()
                .token(token)
                .expira(jwtUtils.obtenerExpiracion(token))
                .rol(usuario.getRol())
                .nombre(usuario.getNombre())
                .fotoPerfil(usuario.getFotoPerfil())
                .idUsuario(usuario.getId())
                .build();
    }

    @Transactional
    public void solicitarRecuperacionContrasena(RecuperacionRequest request) {
        Usuario usuario = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        usuario.setCodigoRecuperacion(UUID.randomUUID().toString());
        usuario.setFechaExpiracionCodigo(LocalDateTime.now().plusHours(1));
        userRepository.save(usuario);

        emailService.enviarCorreoRecuperacion(
                usuario.getCorreo(),
                usuario.getCodigoRecuperacion(),
                usuario.getNombre()
        );
    }

    @Transactional
    public void cambiarContrasena(CambioContrasenaRequest request) {
        Usuario usuario = userRepository.findByCodigoRecuperacion(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Código de recuperación inválido"));

        if (LocalDateTime.now().isAfter(usuario.getFechaExpiracionCodigo())) {
            throw new RuntimeException("Código de recuperación expirado");
        }

        usuario.setPassword(passwordEncoder.encode(request.getNuevaContrasena()));
        usuario.setCodigoRecuperacion(null);
        usuario.setFechaExpiracionCodigo(null);
        userRepository.save(usuario);
    }

    @Transactional
    public void actualizarPerfil(String usuarioId, ActualizarPerfilRequest request) {
        Usuario usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getNombre() != null) {
            usuario.setNombre(request.getNombre());
        }
        if (request.getTelefono() != null) {
            usuario.setTelefono(request.getTelefono());
        }
        if (request.getDireccion() != null) {
            usuario.setDireccion(request.getDireccion());
        }
        if (request.getFotoPerfil() != null) {
            usuario.setFotoPerfil(request.getFotoPerfil());
        }

        userRepository.save(usuario);
    }
}