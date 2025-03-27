package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventarioRecursos {

    @Id
    private String id;

    private String item;
    private String propietarioId; // Referencia al ID del usuario propietario
    private boolean disponible = true;
    private Ubicacion ubicacion;
    private CondicionUso condicionesUso = CondicionUso.PRESTAMO;

    public enum CondicionUso {
        PRESTAMO, DONACION, VENTA
    }
}