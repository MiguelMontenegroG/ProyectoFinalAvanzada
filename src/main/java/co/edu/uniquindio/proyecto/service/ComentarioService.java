package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.Comentario;
import co.edu.uniquindio.proyecto.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario crearComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public Optional<Comentario> obtenerComentarioPorId(String id) {
        return comentarioRepository.findById(id);
    }

    public List<Comentario> obtenerComentariosPorReporte(String reporteId) {
        return comentarioRepository.findByReporteId(reporteId);
    }

    public Comentario actualizarComentario(String id, Comentario comentario) {
        return comentarioRepository.findById(id)
                .map(existingComentario -> {
                    existingComentario.setTexto(comentario.getTexto());
                    return comentarioRepository.save(existingComentario);
                })
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    public void eliminarComentario(String id) {
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentario no encontrado");
        }
        comentarioRepository.deleteById(id);
    }

    public Comentario darLike(String id, String usuarioId) {
        return comentarioRepository.findById(id)
                .map(comentario -> {
                    if (comentario.darLike(usuarioId)) {
                        return comentarioRepository.save(comentario);
                    }
                    return comentario;
                })
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    public Comentario quitarLike(String id, String usuarioId) {
        return comentarioRepository.findById(id)
                .map(comentario -> {
                    if (comentario.quitarLike(usuarioId)) {
                        return comentarioRepository.save(comentario);
                    }
                    return comentario;
                })
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }
}
