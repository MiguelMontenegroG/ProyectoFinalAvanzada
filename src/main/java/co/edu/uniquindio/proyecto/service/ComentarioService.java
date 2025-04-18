package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.dto.request.ComentarioRequest;
import co.edu.uniquindio.proyecto.dto.response.ComentarioResponse;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    ComentarioResponse crearComentario(ComentarioRequest comentarioRequest);
    Optional<ComentarioResponse> obtenerComentarioPorId(String id);
    List<ComentarioResponse> obtenerComentariosPorReporte(String reporteId);
    ComentarioResponse actualizarComentario(String id, ComentarioRequest comentarioRequest);
    void eliminarComentario(String id);
    ComentarioResponse darLike(String id, String usuarioId);
    ComentarioResponse quitarLike(String id, String usuarioId);
}
