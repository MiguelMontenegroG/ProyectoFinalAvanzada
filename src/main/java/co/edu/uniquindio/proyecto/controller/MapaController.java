package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.model.MapaVisualizacion;
import co.edu.uniquindio.proyecto.service.MapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mapas")
@Tag(name = "Mapas", description = "API para gestionar la visualización de mapas y marcadores")
@RequiredArgsConstructor
public class MapaController {

    private final MapaService mapaService;

    @GetMapping("/visualizar")
    @Operation(summary = "Obtener la configuración actual del mapa")
    public ResponseEntity<MapaVisualizacion> visualizarMapa() {
        return ResponseEntity.ok(mapaService.obtenerMapa());
    }

    @PutMapping
    @Operation(summary = "Actualizar la configuración del mapa (centro y zoom)")
    public ResponseEntity<MapaVisualizacion> actualizarMapa(@RequestBody MapaVisualizacion mapa) {
        return ResponseEntity.ok(mapaService.actualizarMapa(mapa));
    }

    @GetMapping("/marcadores")
    @Operation(summary = "Obtener todos los marcadores del mapa")
    public ResponseEntity<List<MapaVisualizacion.Marcador>> obtenerMarcadores() {
        return ResponseEntity.ok(mapaService.obtenerMarcadores());
    }

    @PostMapping("/marcadores")
    @Operation(summary = "Agregar un nuevo marcador al mapa")
    public ResponseEntity<MapaVisualizacion.Marcador> agregarMarcador(@RequestBody MapaVisualizacion.Marcador marcador) {
        return ResponseEntity.ok(mapaService.agregarMarcador(marcador));
    }

    @DeleteMapping("/marcadores/{id}")
    @Operation(summary = "Eliminar un marcador por su ID")
    public ResponseEntity<Void> eliminarMarcador(@PathVariable String id) {
        mapaService.eliminarMarcador(id);
        return ResponseEntity.noContent().build();
    }
}
