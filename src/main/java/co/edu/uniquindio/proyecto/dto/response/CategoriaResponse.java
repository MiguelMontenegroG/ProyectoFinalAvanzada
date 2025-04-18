package co.edu.uniquindio.proyecto.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaResponse {
    private String id;
    private String nombre;
    private String icono;
}
