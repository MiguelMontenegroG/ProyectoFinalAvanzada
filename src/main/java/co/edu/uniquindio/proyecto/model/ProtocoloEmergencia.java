package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "protocolos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocoloEmergencia {

    @Id
    private String id;

    private String titulo;
    private List<String> procedimientos;
    private LocalDate ultimaActualizacion;
    private List<String> autoridadesRelacionadas;
}