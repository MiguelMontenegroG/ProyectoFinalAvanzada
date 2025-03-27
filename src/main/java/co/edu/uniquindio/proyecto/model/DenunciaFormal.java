package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "denuncias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DenunciaFormal {

    @Id
    private String id;

    private String titulo;
    private String descripcion;
    private List<String> evidencias; // URLs de imágenes/docs

    private AutoridadDestino autoridadDestino;
    private EstadoSeguimiento estadoSeguimiento = EstadoSeguimiento.RECIBIDO;
    private String respuestaOficial;
    private LocalDateTime fechaCreacion;

    public enum AutoridadDestino {
        POLICIA, DEFENSA_CIVIL, MUNICIPIO, PROTECCION_ANIMALES
    }

    public enum EstadoSeguimiento {
        RECIBIDO, EN_PROCESO, RESPONDIDO, CERRADO
    }
}