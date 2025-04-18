package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.model.Usuario;
import co.edu.uniquindio.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyecto.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService; // El servicio que estamos probando

    @Mock
    private UsuarioRepository usuarioRepository; // Simulamos el repositorio

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);

        // Creamos un usuario de prueba
        usuario = Usuario.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .nombre("Juan Pérez")
                .correo("juan@example.com")
                .telefono("+573001234567")
                .direccion("Carrera 15 #45-32")
                .ciudad("Armenia")
                .barrio("La Castellana")
                .fotoPerfil("https://cloudinary.com/images/profile123.jpg")
                .biografia("Ciudadano comprometido con mejorar mi comunidad")
                .activo(true)
                .rol("cliente")
                .password("Str0ngP@ssw0rd")
                .build();
    }

    @Test
    void testCrearUsuario() {
        // Configuramos el comportamiento del repositorio simulado
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Llamamos al método que estamos probando
        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);

        // Verificamos que el repositorio fue llamado y que los datos guardados son correctos
        verify(usuarioRepository).save(usuario);
        assertNotNull(usuarioGuardado);
        assertEquals("Juan Pérez", usuarioGuardado.getNombre());
        assertEquals("juan@example.com", usuarioGuardado.getCorreo());
    }

    @Test
    void testObtenerUsuario() {
        // Configuramos el comportamiento del repositorio simulado
        when(usuarioRepository.findById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(Optional.of(usuario));

        // Llamamos al método que estamos probando
        Usuario usuarioObtenido = usuarioService.obtenerUsuario("550e8400-e29b-41d4-a716-446655440000");

        // Verificamos que el usuario obtenido es el correcto
        assertNotNull(usuarioObtenido);
        assertEquals("Juan Pérez", usuarioObtenido.getNombre());
        assertEquals("juan@example.com", usuarioObtenido.getCorreo());
    }

    @Test
    void testListarUsuarios() {
        // Configuramos el comportamiento del repositorio simulado
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Llamamos al método que estamos probando
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Verificamos que la lista de usuarios no esté vacía
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
        assertEquals(1, usuarios.size());
    }

    @Test
    void testActualizarUsuario() {
        // Configuramos el comportamiento del repositorio simulado
        when(usuarioRepository.existsById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Llamamos al método que estamos probando
        Usuario usuarioActualizado = usuarioService.actualizarUsuario("550e8400-e29b-41d4-a716-446655440000", usuario);

        // Verificamos que el repositorio fue llamado y que los datos son los correctos
        verify(usuarioRepository).save(usuario);
        assertNotNull(usuarioActualizado);
        assertEquals("Juan Pérez", usuarioActualizado.getNombre());
    }

    @Test
    void testEliminarUsuario() {
        // Configuramos el comportamiento del repositorio simulado
        when(usuarioRepository.existsById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(true);

        // Llamamos al método que estamos probando
        usuarioService.eliminarUsuario("550e8400-e29b-41d4-a716-446655440000");

        // Verificamos que el repositorio fue llamado
        verify(usuarioRepository).deleteById("550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void testActualizarUsuarioInexistente() {
        when(usuarioRepository.existsById("id-falso")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.actualizarUsuario("id-falso", usuario));

        assertEquals("No se encontró el usuario con ID: id-falso", exception.getMessage());
    }

    @Test
    void testEliminarUsuarioInexistente() {
        when(usuarioRepository.existsById("id-falso")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> usuarioService.eliminarUsuario("id-falso"));

        assertEquals("No se encontró el usuario con ID: id-falso", exception.getMessage());
    }

    @Test
    void testListarVariosUsuarios() {
        Usuario otroUsuario = Usuario.builder()
                .id("id-2")
                .nombre("Maria Gómez")
                .correo("maria@example.com")
                .build();

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario, otroUsuario));

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        assertEquals(2, usuarios.size());
        assertEquals("Maria Gómez", usuarios.get(1).getNombre());
    }

}
