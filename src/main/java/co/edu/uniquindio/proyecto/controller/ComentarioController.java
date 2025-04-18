package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.request.ComentarioRequest;
import co.edu.uniquindio.proyecto.dto.response.ComentarioResponse;
import co.edu.uniquindio.proyecto.service.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentarios", description = "API para gestionar comentarios en reportes ciudadanos")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    @Operation(summary = "Agregar un nuevo comentario a un reporte")
    public ResponseEntity<ComentarioResponse> agregarComentario(@RequestBody ComentarioRequest comentarioRequest) {
        ComentarioResponse comentarioResponse = comentarioService.crearComentario(comentarioRequest);
        return ResponseEntity.ok(comentarioResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un comentario por su ID")
    public ResponseEntity<ComentarioResponse> obtenerComentario(@PathVariable String id) {
        Optional<ComentarioResponse> comentarioResponse = comentarioService.obtenerComentarioPorId(id);
        return comentarioResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/reporte/{reporteId}")
    @Operation(summary = "Obtener comentarios asociados a un reporte")
    public ResponseEntity<List<ComentarioResponse>> obtenerComentariosPorReporte(@PathVariable String reporteId) {
        List<ComentarioResponse> comentarios = comentarioService.obtenerComentariosPorReporte(reporteId);
        return ResponseEntity.ok(comentarios);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar el contenido de un comentario")
    public ResponseEntity<ComentarioResponse> actualizarComentario(@PathVariable String id, @RequestBody ComentarioRequest comentarioRequest) {
        ComentarioResponse comentarioResponse = comentarioService.actualizarComentario(id, comentarioRequest);
        return ResponseEntity.ok(comentarioResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un comentario por su ID")
    public ResponseEntity<Void> eliminarComentario(@PathVariable String id) {
        comentarioService.eliminarComentario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "Dar like a un comentario")
    public ResponseEntity<ComentarioResponse> darLike(@PathVariable String id, @RequestParam String usuarioId) {
        ComentarioResponse comentarioResponse = comentarioService.darLike(id, usuarioId);
        return ResponseEntity.ok(comentarioResponse);
    }

    @PostMapping("/{id}/unlike")
    @Operation(summary = "Quitar like de un comentario")
    public ResponseEntity<ComentarioResponse> quitarLike(@PathVariable String id, @RequestParam String usuarioId) {
        ComentarioResponse comentarioResponse = comentarioService.quitarLike(id, usuarioId);
        return ResponseEntity.ok(comentarioResponse);
    }
}
