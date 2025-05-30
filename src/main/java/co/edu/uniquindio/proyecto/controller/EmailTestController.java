package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.utils.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@Tag(name = "Emails", description = "API para pruebas y envío de correos")
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping("/test-email")
    public String testEmail(@RequestParam String correo) {
        String codigo = generarCodigo(); // Generamos código aleatorio
        System.out.println("Código generado para " + correo + ": " + codigo);
        emailService.enviarCorreoActivacion(correo, codigo);
        return "Correo enviado a " + correo;
    }

    private String generarCodigo() {
        SecureRandom random = new SecureRandom();
        int codigo = random.nextInt(900000) + 100000;
        return String.valueOf(codigo);
    }
}
