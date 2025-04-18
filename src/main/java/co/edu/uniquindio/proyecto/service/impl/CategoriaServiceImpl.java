package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.dto.request.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.response.CategoriaResponse;
import co.edu.uniquindio.proyecto.model.Categoria;
import co.edu.uniquindio.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyecto.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public String crearCategoria(CategoriaDTO dto) {
        Categoria categoria = Categoria.builder()
                .nombre(dto.nombre())
                .icono(dto.icono())
                .build();
        return categoriaRepository.save(categoria).getId();
    }

    @Override
    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(cat -> CategoriaResponse.builder()
                        .id(cat.getId())
                        .nombre(cat.getNombre())
                        .icono(cat.getIcono())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponse obtenerCategoria(String id) {
        Categoria cat = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return CategoriaResponse.builder()
                .id(cat.getId())
                .nombre(cat.getNombre())
                .icono(cat.getIcono())
                .build();
    }

    @Override
    public void eliminarCategoria(String id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
