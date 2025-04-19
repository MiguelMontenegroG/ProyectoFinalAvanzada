package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.utils.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping("/test-email")
    public String testEmail(@RequestParam String correo) {
        emailService.enviarCorreoActivacion(correo, "123456");
        return "Correo enviado a " + correo;
    }
}
