package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyecto.model.Reporte;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends MongoRepository<Reporte, String> {

    // ======================
// CONSULTAS BÁSICAS
// ======================

    @Query("{ 'usuarioId': ?0 }")
    List<Reporte> findByUsuarioId(String usuarioId);

    @Query("{ 'estado': ?0 }")
    List<Reporte> findByEstado(String estado);

    default List<Reporte> findByEstado(EstadoReporte estado) {
        return findByEstado(estado.name());
    }

    @Query("{ 'prioridad': ?0 }")
    List<Reporte> findByPrioridad(String prioridad);

    @Query("{ 'categoria': ?0 }")
    List<Reporte> findByCategoria(String categoria);

    // ======================
    // CONSULTAS POR FECHA
    // ======================

    @Query("{ 'fechaCreacion': { $gte: ?0, $lte: ?1 } }")
    List<Reporte> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("{ 'fechaCreacion': { $gte: ?0 } }")
    List<Reporte> findByFechaCreacionAfter(LocalDateTime inicio);

    @Query("{ 'fechaCreacion': { $lte: ?0 } }")
    List<Reporte> findByFechaCreacionBefore(LocalDateTime fin);

    // ======================
    // CONSULTAS DE CONTEO
    // ======================

    @Query(value = "{ 'categoria': { $in: ?0 } }", count = true)
    long countByCategoriaIn(List<String> categorias);

    @Query(value = "{ 'estado': ?0 }", count = true)
    long countByEstado(String estado);

    default long countByEstado(EstadoReporte estado) {
        return countByEstado(estado.name());
    }

    @Query(value = "{ 'usuarioId': ?0 }", count = true)
    long countByUsuarioId(String usuarioId);

    // ======================
    // AGREGACIONES PARA ANALYTICS
    // ======================

    @Aggregation(pipeline = {
            "{ $match: { 'fechaCreacion': { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { _id: { $dateToString: { format: '%Y-%m-%d', date: '$fechaCreacion' } }, count: { $sum: 1 } } }"
    })
    List<FechaCount> countReportesPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Aggregation(pipeline = {
            "{ $match: { 'fechaCreacion': { $gte: ?0, $lte: ?1 }, 'estado': ?2 } }",
            "{ $group: { _id: { $dateToString: { format: '%Y-%m-%d', date: '$fechaCreacion' } }, count: { $sum: 1 } } }"
    })
    List<FechaCount> countReportesPorFechaYEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado);

    default List<FechaCount> countReportesPorFechaYEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, EstadoReporte estado) {
        return countReportesPorFechaYEstado(fechaInicio, fechaFin, estado.name());
    }

    @Aggregation(pipeline = {
            "{ $unwind: '$categoria' }",
            "{ $group: { _id: '$categoria', count: { $sum: 1 } } }"
    })
    List<CategoriaCount> countByCategoria();

    @Aggregation(pipeline = {
            "{ $unwind: '$historialEstados' }",
            "{ $group: { _id: { " +
                    "fecha: { $dateToString: { format: '%Y-%m-%d', date: '$historialEstados.fecha' } }, " +
                    "estado: '$historialEstados.estado' " +
                    "}, count: { $sum: 1 } } }"
    })
    List<HistorialCount> countHistorialEstados();

    @Aggregation(pipeline = {
            "{ $unwind: '$categoria' }",
            "{ $group: { _id: { categoria: '$categoria', estado: '$estado' }, count: { $sum: 1 } } }"
    })
    List<CategoriaEstadoCount> countByCategoriaAndEstado();

    @Aggregation(pipeline = {
            "{ $group: { _id: '$estado', count: { $sum: 1 } } }"
    })
    List<EstadoCount> countTotalByEstado();

    @Aggregation(pipeline = {
            "{ $match: { 'ubicacion.latitud': { $exists: true }, 'ubicacion.longitud': { $exists: true } } }",
            "{ $project: { " +
                    "titulo: 1, " +
                    "estado: 1, " +
                    "categoria: 1, " +
                    "prioridad: 1, " +
                    "ubicacion: 1, " +
                    "fechaCreacion: 1 " +
                    "} }"
    })
    List<Reporte> findAllConUbicacion();

    // ======================
    // INTERFACES PARA PROYECCIONES
    // ======================

    interface FechaCount {
        String get_id();  // Fecha en formato YYYY-MM-DD
        int getCount();   // Cantidad de reportes
    }

    interface HistorialCount {
        FechaEstado get_id();
        int getCount();

        interface FechaEstado {
            String getFecha();
            String getEstado();
        }
    }

    interface CategoriaEstadoCount {
        CategoriaEstado get_id();
        int getCount();

        interface CategoriaEstado {
            String getCategoria();
            String getEstado();
        }
    }

    interface EstadoCount {
        String get_id();  // Representa el estado
        int getCount();  // Representa el conteo
    }

    interface ReporteResumen {
        String getId();
        String getTitulo();
        String getEstado();
        LocalDateTime getFechaCreacion();
    }

    interface CategoriaCount {
        String get_id();   // Nombre de la categoría
        int getCount();    // Cantidad de reportes
    }

    // ======================
    // CONSULTAS GEOESPACIALES
    // ======================

    @Query("{ " +
            "'ubicacion.latitud': { $exists: true }, " +
            "'ubicacion.longitud': { $exists: true }, " +
            "'ubicacion.latitud': { $gte: ?0, $lte: ?2 }, " +
            "'ubicacion.longitud': { $gte: ?1, $lte: ?3 } " +
            "}")
    List<Reporte> findByArea(double minLat, double minLng, double maxLat, double maxLng);

    @Query(value = "{ " +
            "'ubicacion': { " +
            "$near: { " +
            "$geometry: { type: 'Point', coordinates: [ ?1, ?0 ] }, " +
            "$maxDistance: ?2 " +
            "} " +
            "} " +
            "}")
    List<Reporte> findByProximidad(double latitud, double longitud, double distanciaMetros);
}