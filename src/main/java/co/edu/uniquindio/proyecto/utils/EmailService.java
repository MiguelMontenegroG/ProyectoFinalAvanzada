package co.edu.uniquindio.proyecto.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoActivacion(String destino, String codigo) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(destino);
            helper.setSubject("Activa tu cuenta en Reportes Ciudadanos");
            helper.setFrom("alertasciudadanas479@gmail.com"); // Fija el remitente
            helper.setText(
                    "¡Bienvenido!\n\n" +
                            "Tu código de activación es: " + codigo + "\n\n" +
                            "Este código expirará en 15 minutos.",
                    false // No es HTML
            );

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
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
