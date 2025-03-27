package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document(collection = "encuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncuestaComunitaria {

    @Id
    private String id;

    private String pregunta;
    private List<String> opciones;
    private Map<String, Integer> votos; // Clave: opción, Valor: conteo
    private LocalDate fechaLimite;
    private String creadorId; // Referencia al ID del usuario creador
    private boolean resultadosPublicos = true;
}