package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.model.Comentario;
import co.edu.uniquindio.proyecto.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Document(collection = "comentarios")
@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentarios", description = "API para gestionar comentarios en reportes ciudadanos")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    @Operation(summary = "Agregar un nuevo comentario a un reporte")
    public ResponseEntity<Comentario> agregarComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioService.crearComentario(comentario));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un comentario por su ID")
    public ResponseEntity<Comentario> obtenerComentario(@PathVariable String id) {
        Optional<Comentario> comentario = comentarioService.obtenerComentarioPorId(id);
        return comentario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reporte/{reporteId}")
    @Operation(summary = "Obtener comentarios asociados a un reporte")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorReporte(@PathVariable String reporteId) {
        return ResponseEntity.ok(comentarioService.obtenerComentariosPorReporte(reporteId));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar el contenido de un comentario")
    public ResponseEntity<Comentario> actualizarComentario(@PathVariable String id, @RequestBody Comentario comentario) {
        return ResponseEntity.ok(comentarioService.actualizarComentario(id, comentario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un comentario por su ID")
    public ResponseEntity<Void> eliminarComentario(@PathVariable String id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "Dar like a un comentario")
    public ResponseEntity<Comentario> darLike(@PathVariable String id, @RequestParam String usuarioId) {
        return ResponseEntity.ok(comentarioService.darLike(id, usuarioId));
    }

    @PostMapping("/{id}/unlike")
    @Operation(summary = "Quitar like de un comentario")
    public ResponseEntity<Comentario> quitarLike(@PathVariable String id, @RequestParam String usuarioId) {
        return ResponseEntity.ok(comentarioService.quitarLike(id, usuarioId));
    }
}