package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.request.*;
import co.edu.uniquindio.proyecto.dto.response.LoginResponse;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.service.impl.AuthServiceImpl;
import co.edu.uniquindio.proyecto.utils.EmailService;
import co.edu.uniquindio.proyecto.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registrarUsuarioTest() {
        RegistroRequest request = new RegistroRequest();
        request.setNombre("Pedro");
        request.setCorreo("pedro@email.com");
        request.setPassword("12345678");
        request.setTelefono("+573001112233");
        request.setRol("USUARIO");

        when(usuarioRepository.existsByCorreo(request.getCorreo())).thenReturn(false);
        when(passwordEncoder.encode("12345678")).thenReturn("encrypted");
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Usuario usuario = authService.registrarUsuario(request);

        assertEquals(request.getCorreo(), usuario.getCorreo());
        assertEquals(request.getRol(), usuario.getRol());
        verify(emailService).enviarCorreoActivacion(eq(request.getCorreo()), anyString());
    }


    @Test
    public void activarCuentaTest() {
        String codigo = "codigo123";
        Usuario usuario = Usuario.builder()
                .correo("correo@email.com")
                .codigoActivacion(codigo)
                .fechaExpiracionCodigo(LocalDateTime.now().plusHours(1))
                .build();

        when(usuarioRepository.findByCodigoActivacion(codigo)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        assertDoesNotThrow(() -> authService.activarCuenta(new ActivacionRequest(codigo)));
        assertNull(usuario.getCodigoActivacion());
        assertTrue(usuario.isActivo());
    }

    @Test
    public void loginTest() {
        String correo = "test@email.com";
        String password = "1234";
        Usuario usuario = Usuario.builder()
                .correo(correo)
                .password("encoded")
                .rol("CLIENTE")
                .activo(true)
                .nombre("Juan")
                .id("123")
                .build();

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(password, "encoded")).thenReturn(true);
        when(jwtUtils.generarToken(usuario)).thenReturn("token123");
        when(jwtUtils.obtenerExpiracion("token123")).thenReturn(
                LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        // Crear LoginRequest con setters
        LoginRequest request = new LoginRequest();
        request.setCorreo(correo);
        request.setPassword(password);

        // Realizar login
        LoginResponse response = authService.login(request);

        assertEquals("token123", response.getToken());
        assertEquals("Juan", response.getNombre());
    }


    @Test
    public void solicitarRecuperacionContrasenaTest() {
        String correo = "recupera@email.com";
        Usuario usuario = Usuario.builder()
                .correo(correo)
                .nombre("Ana")
                .build();

        // Creamos una instancia de RecuperacionRequest con setters
        RecuperacionRequest request = new RecuperacionRequest();
        request.setCorreo(correo);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        // Realizamos la solicitud de recuperación de contraseña
        assertDoesNotThrow(() -> authService.solicitarRecuperacionContrasena(request));

        // Verificamos que el correo de recuperación fue enviado
        verify(emailService).enviarCorreoRecuperacion(eq(correo), anyString(), eq("Ana"));
    }


    @Test
    public void cambiarContrasenaTest() {
        String codigo = "recupera123";
        Usuario usuario = Usuario.builder()
                .codigoRecuperacion(codigo)
                .fechaExpiracionCodigo(LocalDateTime.now().plusHours(1))
                .build();

        when(usuarioRepository.findByCodigoRecuperacion(codigo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode("nueva")).thenReturn("hashed");

        CambioContrasenaRequest request = new CambioContrasenaRequest();
        request.setCodigo(codigo);
        request.setNuevaContrasena("nueva");

        assertDoesNotThrow(() -> authService.cambiarContrasena(request));
        assertNull(usuario.getCodigoRecuperacion());
        assertEquals("hashed", usuario.getPassword());
    }

    @Test
    public void actualizarPerfilTest() {
        String id = "usuario123";
        Usuario usuario = Usuario.builder()
                .id(id)
                .nombre("Original")
                .build();

        ActualizarPerfilRequest request = new ActualizarPerfilRequest();
        request.setNombre("Nuevo Nombre");
        request.setTelefono("123456");
        request.setDireccion("Nueva Dir");
        request.setFotoPerfil("urlFoto");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> authService.actualizarPerfil(id, request));
        assertEquals("Nuevo Nombre", usuario.getNombre());
        assertEquals("123456", usuario.getTelefono());
        assertEquals("Nueva Dir", usuario.getDireccion());
        assertEquals("urlFoto", usuario.getFotoPerfil());
    }
}
