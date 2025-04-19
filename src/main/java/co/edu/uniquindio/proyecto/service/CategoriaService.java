package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    String crearCategoria(CategoriaDTO categoriaDTO);
    List<CategoriaResponse> listarCategorias();
    CategoriaResponse obtenerCategoria(String id);
    void eliminarCategoria(String id);
}
