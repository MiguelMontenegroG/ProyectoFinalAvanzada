package co.edu.uniquindio.proyecto.service;

import co.edu.uniquindio.proyecto.model.MapaVisualizacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MapaService {

    private MapaVisualizacion mapaVisualizacion = new MapaVisualizacion();

    // Obtener la configuración del mapa
    public MapaVisualizacion obtenerMapa() {
        log.info("Obteniendo configuración del mapa");
        return mapaVisualizacion;
    }

    // Actualizar la configuración del mapa
    public MapaVisualizacion actualizarMapa(MapaVisualizacion nuevoMapa) {
        log.info("Actualizando mapa con nuevos valores");
        this.mapaVisualizacion = nuevoMapa;
        return mapaVisualizacion;
    }

    // Obtener todos los marcadores
    public List<MapaVisualizacion.Marcador> obtenerMarcadores() {
        return mapaVisualizacion.getMarcadores();
    }

    // Agregar un nuevo marcador
    public MapaVisualizacion.Marcador agregarMarcador(MapaVisualizacion.Marcador marcador) {
        mapaVisualizacion.getMarcadores().add(marcador);
        log.info("Marcador agregado: {}", marcador);
        return marcador;
    }

    // Eliminar un marcador por ID
    public void eliminarMarcador(String id) {
        Optional<MapaVisualizacion.Marcador> marcadorEncontrado = mapaVisualizacion.getMarcadores()
                .stream().filter(m -> m.getId().equals(id)).findFirst();

        marcadorEncontrado.ifPresent(marcador -> {
            mapaVisualizacion.getMarcadores().remove(marcador);
            log.info("Marcador eliminado: {}", id);
        });
    }
}
