package co.edu.uniquindio.proyecto.test;

import co.edu.uniquindio.proyecto.dto.CategoriaDTO;
import co.edu.uniquindio.proyecto.dto.response.CategoriaResponse;
import co.edu.uniquindio.proyecto.model.Categoria;
import co.edu.uniquindio.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyecto.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearCategoria() {
        // Arrange
        CategoriaDTO dto = new CategoriaDTO("Basura", "🗑️");
        Categoria categoria = Categoria.builder()
                .id("123")
                .nombre(dto.nombre())
                .icono(dto.icono())
                .build();

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        // Act
        String id = categoriaService.crearCategoria(dto);

        // Assert
        assertEquals("123", id);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    public void testListarCategorias() {
        // Arrange
        List<Categoria> categorias = Arrays.asList(
                Categoria.builder().id("1").nombre("Basura").icono("🗑️").build(),
                Categoria.builder().id("2").nombre("Huecos").icono("🕳️").build()
        );
        when(categoriaRepository.findAll()).thenReturn(categorias);

        // Act
        List<CategoriaResponse> resultado = categoriaService.listarCategorias();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Basura", resultado.get(0).getNombre());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testObtenerCategoriaExistente() {
        // Arrange
        Categoria categoria = Categoria.builder().id("1").nombre("Basura").icono("🗑️").build();
        when(categoriaRepository.findById("1")).thenReturn(Optional.of(categoria));

        // Act
        CategoriaResponse response = categoriaService.obtenerCategoria("1");

        // Assert
        assertEquals("Basura", response.getNombre());
        assertEquals("🗑️", response.getIcono());
        verify(categoriaRepository, times(1)).findById("1");
    }

    @Test
    public void testObtenerCategoriaNoExistente() {
        when(categoriaRepository.findById("999")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            categoriaService.obtenerCategoria("999");
        });

        assertEquals("Categoria no encontrada", ex.getMessage());
    }

    @Test
    public void testEliminarCategoriaExistente() {
        when(categoriaRepository.existsById("1")).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById("1");

        // Act
        categoriaService.eliminarCategoria("1");

        // Assert
        verify(categoriaRepository, times(1)).deleteById("1");
    }

    @Test
    public void testEliminarCategoriaNoExistente() {
        when(categoriaRepository.existsById("999")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            categoriaService.eliminarCategoria("999");
        });

        assertEquals("Categoria no encontrada", ex.getMessage());
    }
}
