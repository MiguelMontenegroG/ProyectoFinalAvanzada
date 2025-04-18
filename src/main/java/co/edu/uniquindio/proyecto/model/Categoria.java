package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document("categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {

    @Id
    private String id;

    @NotBlank
    private String nombre;

    private String icono;
}
