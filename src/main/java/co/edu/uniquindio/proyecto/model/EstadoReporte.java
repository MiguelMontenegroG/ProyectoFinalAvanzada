package co.edu.uniquindio.proyecto.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum EstadoReporte {
    PENDIENTE,
    EN_REVISION,
    VERIFICADO,
    RECHAZADO,
    RESUELTO;

    // Permite deserializar ignorando mayúsculas/minúsculas
    @JsonCreator
    public static EstadoReporte fromString(String value) {
        if (value == null) return null;
        try {
            return EstadoReporte.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Valor '" + value +
                    "' no válido para EstadoReporte. Valores aceptados: " +
                    Arrays.toString(EstadoReporte.values()));
        }
    }

    // Para serialización consistente
    @JsonValue
    public String toValue() {
        return this.name().toLowerCase();
    }
}