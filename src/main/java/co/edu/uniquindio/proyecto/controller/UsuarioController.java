package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.request.ActivacionDTO;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios en la plataforma")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo usuario")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por su ID")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable String id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuario(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los usuarios registrados")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar la información de un usuario")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar un usuario por su ID")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/activar")
    @Operation(
            summary = "Activar cuenta de usuario",
            description = "Valida el código de activación enviado al correo del usuario y activa la cuenta si el código es correcto y no ha expirado.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = ActivacionDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cuenta activada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Código incorrecto o expirado"),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
            }
    )
    public ResponseEntity<String> activarCuenta(@RequestBody ActivacionDTO dto) {
        usuarioService.activarCuenta(dto);
        return ResponseEntity.ok("Cuenta activada correctamente");
    }
}
