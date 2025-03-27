package co.edu.uniquindio.proyecto.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ActivacionRequest {

    @NotBlank(message = "El código de activación es obligatorio")
    private String codigo;

    // Constructor vacío
    public ActivacionRequest() {
    }

    // Constructor con todos los campos
    public ActivacionRequest(String codigo) {
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}