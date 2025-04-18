package co.edu.uniquindio.proyecto.dto.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegistroRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_noConstraintViolations() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("Juan Pérez");
        req.setCorreo("juan@example.com");
        req.setPassword("Str0ngP@ssw0rd");
        req.setTelefono("+573001234567");
        req.setRol("ROLE_USER");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertTrue(violations.isEmpty(), () -> "Se esperaban 0 violaciones, pero hubo: " + violations);
    }

    @Test
    void whenNombreBlank_violationOnNombre() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("");
        req.setCorreo("juan@example.com");
        req.setPassword("Str0ngP@ssw0rd");
        req.setTelefono("+573001234567");
        req.setRol("ROLE_USER");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre")
                        && v.getMessage().contains("obligatorio")));
    }

    @Test
    void whenCorreoInvalid_violationOnCorreo() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("Juan Pérez");
        req.setCorreo("juan#example.com");
        req.setPassword("Str0ngP@ssw0rd");
        req.setTelefono("+573001234567");
        req.setRol("ROLE_USER");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("correo")
                        && v.getMessage().contains("válido")));
    }

    @Test
    void whenPasswordTooShort_violationOnPassword() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("Juan Pérez");
        req.setCorreo("juan@example.com");
        req.setPassword("short");
        req.setTelefono("+573001234567");
        req.setRol("ROLE_USER");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password")
                        && v.getMessage().contains("al menos 8 caracteres")));
    }

    @Test
    void whenTelefonoBlank_violationOnTelefono() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("Juan Pérez");
        req.setCorreo("juan@example.com");
        req.setPassword("Str0ngP@ssw0rd");
        req.setTelefono("");
        req.setRol("ROLE_USER");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("telefono")
                        && v.getMessage().contains("obligatorio")));
    }

    @Test
    void whenRolInvalid_violationOnRol() {
        RegistroRequest req = new RegistroRequest();
        req.setNombre("Juan Pérez");
        req.setCorreo("juan@example.com");
        req.setPassword("Str0ngP@ssw0rd");
        req.setTelefono("+573001234567");
        req.setRol("INVALID_ROLE");

        Set<ConstraintViolation<RegistroRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("rol")
                        && v.getMessage().contains("ROLE_USER o ROLE_ADMIN")));
    }
}
