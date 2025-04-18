package co.edu.uniquindio.proyecto.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoActivacion(String destino, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject("Activa tu cuenta en Reportes Ciudadanos");
        mensaje.setText(String.format(
                "¡Bienvenido!\n\n" +
                        "Tu código de activación es: %s\n\n" +
                        "Este código expirará en 24 horas.",
                codigo
        ));
        mailSender.send(mensaje);
    }

    public void enviarCorreoRecuperacion(String destino, String codigo, String nombreUsuario) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject("Recuperación de contraseña - Reportes Ciudadanos");
        mensaje.setText(String.format(
                "Hola %s,\n\n" +
                        "Hemos recibido una solicitud para restablecer tu contraseña.\n\n" +
                        "Tu código de recuperación es: %s\n\n" +
                        "Este código expirará en 1 hora.\n\n" +
                        "Si no solicitaste este cambio, por favor ignora este mensaje.",
                nombreUsuario, codigo
        ));
        mailSender.send(mensaje);
    }
}
