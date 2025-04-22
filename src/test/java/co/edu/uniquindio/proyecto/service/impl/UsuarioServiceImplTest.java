package co.edu.uniquindio.proyecto.service.impl;

import co.edu.uniquindio.proyecto.dto.ActivacionDTO;
import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = Usuario.builder()
                .id("123")
                .nombre("Juan")
                .correo("juan@correo.com")
                .password("clave123")
                .activo(false)
                .codigoActivacion("654321")
                .fechaExpiracionCodigo(LocalDateTime.now().plusMinutes(15))
                .build();
    }

    @Test
    void crearUsuario_CorreoNoExistente_DeberiaGuardarUsuario() {
        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        Usuario creado = usuarioService.crearUsuario(usuario);

        assertNotNull(creado);
        assertEquals(usuario.getCorreo(), creado.getCorreo());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void crearUsuario_CorreoExistente_DeberiaLanzarExcepcion() {
        when(usuarioRepository.existsByCorreo(usuario.getCorreo())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(usuario));
        assertEquals("El correo ya está registrado", ex.getMessage());
    }

    @Test
    void obtenerUsuario_IdValido_DeberiaRetornarUsuario() {
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.obtenerUsuario("123");

        assertNotNull(encontrado);
        assertEquals("Juan", encontrado.getNombre());
    }

    @Test
    void obtenerUsuario_IdInvalido_DeberiaLanzarExcepcion() {
        when(usuarioRepository.findById("999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.obtenerUsuario("999"));
        assertTrue(ex.getMessage().contains("No se encontró el usuario"));
    }

    @Test
    void listarUsuarios_DeberiaRetornarListaUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }

    @Test
    void actualizarUsuario_IdValido_DeberiaActualizar() {
        Usuario actualizado = Usuario.builder().nombre("Pedro").build();
        when(usuarioRepository.existsById("123")).thenReturn(true);
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Usuario result = usuarioService.actualizarUsuario("123", actualizado);

        assertEquals("Pedro", result.getNombre());
        assertEquals("123", result.getId());
    }

    @Test
    void eliminarUsuario_IdValido_DeberiaEliminar() {
        when(usuarioRepository.existsById("123")).thenReturn(true);

        usuarioService.eliminarUsuario("123");

        verify(usuarioRepository).deleteById("123");
    }

    @Test
    void activarCuenta_DatosCorrectos_DeberiaActivarCuenta() {
        ActivacionDTO dto = new ActivacionDTO(usuario.getCorreo(), usuario.getCodigoActivacion());
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        usuarioService.activarCuenta(dto);

        assertTrue(usuario.isActivo());
        assertNull(usuario.getCodigoActivacion());
        assertNull(usuario.getFechaExpiracionCodigo());
    }

    @Test
    void activarCuenta_CodigoIncorrecto_DeberiaLanzarExcepcion() {
        usuario.setCodigoActivacion("000000");
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        ActivacionDTO dto = new ActivacionDTO(usuario.getCorreo(), "123456");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.activarCuenta(dto));
        assertEquals("El código de activación es incorrecto.", ex.getMessage());
    }

    @Test
    void activarCuenta_CodigoExpirado_DeberiaLanzarExcepcion() {
        usuario.setFechaExpiracionCodigo(LocalDateTime.now().minusMinutes(1));
        when(usuarioRepository.findByCorreo(usuario.getCorreo())).thenReturn(Optional.of(usuario));

        ActivacionDTO dto = new ActivacionDTO(usuario.getCorreo(), usuario.getCodigoActivacion());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.activarCuenta(dto));
        assertEquals("El código de activación ha expirado.", ex.getMessage());
    }
}
