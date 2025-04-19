package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.dto.request.ComentarioRequest;
import co.edu.uniquindio.proyecto.dto.response.ComentarioResponse;
import co.edu.uniquindio.proyecto.model.Comentario;
import co.edu.uniquindio.proyecto.repository.ComentarioRepository;
import co.edu.uniquindio.proyecto.service.ComentarioService;
import co.edu.uniquindio.proyecto.exception.ComentarioNotFoundException;  // Asegúrate de importar la excepción
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Override
    public ComentarioResponse crearComentario(ComentarioRequest comentarioRequest) {
        Comentario comentario = Comentario.builder()
                .texto(comentarioRequest.getTexto())
                .usuarioId(comentarioRequest.getUsuarioId())
                .reporteId(comentarioRequest.getReporteId())
                .build();
        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        return new ComentarioResponse(comentarioGuardado);
    }

    @Override
    public Optional<ComentarioResponse> obtenerComentarioPorId(String comentarioId) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(comentarioId);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            return Optional.of(new ComentarioResponse(comentario));  // Devolvemos un Optional
        } else {
            throw new ComentarioNotFoundException("Comentario no encontrado");
        }
    }

    @Override
    public List<ComentarioResponse> obtenerComentariosPorReporte(String reporteId) {
        List<Comentario> comentarios = comentarioRepository.findByReporteId(reporteId);
        return comentarios.stream().map(ComentarioResponse::new).collect(Collectors.toList());
    }

    @Override
    public ComentarioResponse actualizarComentario(String id, ComentarioRequest comentarioRequest) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        comentario.setTexto(comentarioRequest.getTexto());
        comentario.setUsuarioId(comentarioRequest.getUsuarioId());
        comentario.setReporteId(comentarioRequest.getReporteId());
        comentarioRepository.save(comentario);
        return new ComentarioResponse(comentario);
    }

    @Override
    public void eliminarComentario(String id) {
        comentarioRepository.deleteById(id);
    }

    @Override
    public ComentarioResponse darLike(String id, String usuarioId) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        comentario.darLike(usuarioId);
        comentarioRepository.save(comentario);
        return new ComentarioResponse(comentario);
    }

    @Override
    public ComentarioResponse quitarLike(String id, String usuarioId) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        comentario.quitarLike(usuarioId);
        comentarioRepository.save(comentario);
        return new ComentarioResponse(comentario);
    }
}
