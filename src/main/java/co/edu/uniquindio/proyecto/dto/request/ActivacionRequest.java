package co.edu.uniquindio.proyecto.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivacionRequest {
    @NotBlank(message = "El código de activación es obligatorio")
    private String codigo;
}
