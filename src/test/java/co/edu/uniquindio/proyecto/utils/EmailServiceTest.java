package co.edu.uniquindio.proyecto.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEnviarCorreoActivacion() throws MessagingException {
        // Arrange
        String destino = "usuario@example.com";
        String codigo = "123456";

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailService.enviarCorreoActivacion(destino, codigo);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void testEnviarCorreoActivacion_conExcepcion() {
        // Arrange
        String destino = "usuario@example.com";
        String codigo = "123456";

        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Fallo al crear mensaje"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            emailService.enviarCorreoActivacion(destino, codigo);
        });
    }

    @Test
    public void testEnviarCorreoRecuperacion() {
        // Arrange
        String destino = "usuario@example.com";
        String codigo = "ABC123";
        String nombreUsuario = "Juan";

        // Act
        emailService.enviarCorreoRecuperacion(destino, codigo, nombreUsuario);

        // Assert
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage mensajeEnviado = captor.getValue();
        assert mensajeEnviado.getTo()[0].equals(destino);
        assert mensajeEnviado.getSubject().equals("Recuperación de contraseña - Reportes Ciudadanos");
        assert mensajeEnviado.getText().contains(codigo);
        assert mensajeEnviado.getText().contains(nombreUsuario);
    }
}
