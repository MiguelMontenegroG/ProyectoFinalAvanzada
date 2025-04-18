package co.edu.uniquindio.proyecto.exception;

public class ComentarioNotFoundException extends RuntimeException {
    public ComentarioNotFoundException(String mensaje) {
        super(mensaje);
    }
}
