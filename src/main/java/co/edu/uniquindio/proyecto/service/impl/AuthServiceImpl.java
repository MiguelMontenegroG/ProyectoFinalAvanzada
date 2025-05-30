package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.dto.request.*;
import co.edu.uniquindio.proyecto.dto.response.LoginResponse;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.service.AuthService;
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
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Transactional
    @Override
    public Usuario registrarUsuario(RegistroRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .activo(false)
                .rol("USUARIO")
                .codigoActivacion(UUID.randomUUID().toString())
                .fechaExpiracionCodigo(LocalDateTime.now().plusHours(24))
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        emailService.enviarCorreoActivacion(usuario.getCorreo(), usuario.getCodigoActivacion());

        return usuarioGuardado;
    }

    @Transactional
    @Override
    public void activarCuenta(ActivacionRequest request) {
        Usuario usuario = usuarioRepository.findByCodigoActivacion(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Código de activación inválido"));

        if (LocalDateTime.now().isAfter(usuario.getFechaExpiracionCodigo())) {
            throw new RuntimeException("Código de activación expirado");
        }

        usuario.setActivo(true);
        usuario.setCodigoActivacion(null);
        usuario.setFechaExpiracionCodigo(null);
        usuarioRepository.save(usuario);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
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
    @Override
    public void solicitarRecuperacionContrasena(RecuperacionRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        usuario.setCodigoRecuperacion(UUID.randomUUID().toString());
        usuario.setFechaExpiracionCodigo(LocalDateTime.now().plusHours(1));
        usuarioRepository.save(usuario);

        emailService.enviarCorreoRecuperacion(
                usuario.getCorreo(),
                usuario.getCodigoRecuperacion(),
                usuario.getNombre()
        );
    }

    @Transactional
    @Override
    public void cambiarContrasena(CambioContrasenaRequest request) {
        Usuario usuario = usuarioRepository.findByCodigoRecuperacion(request.getCodigo())
                .orElseThrow(() -> new RuntimeException("Código de recuperación inválido"));

        if (LocalDateTime.now().isAfter(usuario.getFechaExpiracionCodigo())) {
            throw new RuntimeException("Código de recuperación expirado");
        }

        usuario.setPassword(passwordEncoder.encode(request.getNuevaContrasena()));
        usuario.setCodigoRecuperacion(null);
        usuario.setFechaExpiracionCodigo(null);
        usuarioRepository.save(usuario);
    }

    @Transactional
    @Override
    public void actualizarPerfil(String usuarioId, ActualizarPerfilRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
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

        usuarioRepository.save(usuario);
    }
}
