package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.model.Notificacion;
import co.edu.uniquindio.proyecto.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Document("notificaciones") // Asegura que se guarde en la colección "notificaciones"

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Notificaciones", description = "API para gestionar las notificaciones de los usuarios")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva notificación")
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        return ResponseEntity.ok(notificacionService.crearNotificacion(notificacion));
    }

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Obtener las notificaciones de un usuario específico")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones(@PathVariable String usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNotificacionesPorUsuario(usuarioId));
    }

    @PatchMapping("/{id}/leida")
    @Operation(summary = "Marcar una notificación como leída")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable String id) {
        Optional<Notificacion> notificacion = notificacionService.marcarComoLeida(id);
        return notificacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una notificación por su ID")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable String id) {
        if (notificacionService.eliminarNotificacion(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
