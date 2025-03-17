package co.edu.uniquindio.proyecto.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Schema(description = "Estructura de respuesta con paginación")
@Data
@AllArgsConstructor
public class PaginacionResponse<T> {

    @Schema(description = "Lista de resultados de la página actual")
    private List<T> contenido;

    @Schema(description = "Número total de elementos disponibles", example = "250")
    private long totalElementos;

    @Schema(description = "Cantidad total de páginas", example = "25")
    private int totalPaginas;

    @Schema(description = "Número de la página actual", example = "3")
    private int paginaActual;
}
