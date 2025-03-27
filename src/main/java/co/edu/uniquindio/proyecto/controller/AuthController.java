package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.request.*;
import co.edu.uniquindio.proyecto.dto.response.LoginResponse;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "API para gestión de autenticación, registro y perfil de usuarios")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    @Operation(summary = "Registrar nuevo usuario",
            description = "Crea una nueva cuenta de usuario con los datos proporcionados")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody RegistroRequest request) {
        return ResponseEntity.ok(authService.registrarUsuario(request));
    }

    @PostMapping("/activar")
    @Operation(summary = "Activar cuenta",
            description = "Activa una cuenta de usuario mediante el código de verificación")
    public ResponseEntity<Void> activarCuenta(@RequestBody ActivacionRequest request) {
        authService.activarCuenta(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión",
            description = "Autentica al usuario y genera un token de acceso")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/recuperar")
    @Operation(summary = "Solicitar recuperación de contraseña",
            description = "Inicia el proceso de recuperación de contraseña enviando un código al email registrado")
    public ResponseEntity<Void> solicitarRecuperacion(@RequestBody RecuperacionRequest request) {
        authService.solicitarRecuperacionContrasena(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cambiar-contrasena")
    @Operation(summary = "Cambiar contraseña",
            description = "Permite establecer una nueva contraseña tras verificación")
    public ResponseEntity<Void> cambiarContrasena(@RequestBody CambioContrasenaRequest request) {
        authService.cambiarContrasena(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/perfil/{usuarioId}")
    @Operation(summary = "Actualizar perfil de usuario",
            description = "Modifica la información del perfil del usuario identificado por su ID")
    public ResponseEntity<Void> actualizarPerfil(
            @PathVariable String usuarioId,
            @RequestBody ActualizarPerfilRequest request) {
        authService.actualizarPerfil(usuarioId, request);
        return ResponseEntity.ok().build();
    }
}