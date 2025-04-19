package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.response.CategoriaResponse;
import co.edu.uniquindio.proyecto.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "API para gestionar las categorías disponibles en la plataforma")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Crear una categoría", description = "Permite registrar una nueva categoría con su nombre e icono")
    @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente")
    @PostMapping
    public ResponseEntity<String> crearCategoria(@Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.crearCategoria(dto));
    }

    @Operation(summary = "Listar todas las categorías", description = "Devuelve la lista completa de categorías registradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @Operation(summary = "Obtener una categoría por ID", description = "Devuelve la información de una categoría específica dado su ID")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada exitosamente")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerCategoria(@PathVariable String id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(id));
    }

    @Operation(summary = "Eliminar una categoría", description = "Elimina una categoría existente dado su ID")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable String id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
