import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.*;
import com.matiasbadano.challeng.services.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasCategorias() {
        Categoria categoria1 = new Categoria(1L, "Categoria 1", new ArrayList<>());
        Categoria categoria2 = new Categoria(2L, "Categoria 2", new ArrayList<>());
        List<Categoria> categoriasMock = Arrays.asList(categoria1, categoria2);

        when(categoriaRepository.findAll()).thenReturn(categoriasMock);

        List<Categoria> resultado = categoriaService.obtenerTodasLasCategorias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Categoria resultadoCategoria1 = resultado.get(0);
        assertEquals(categoria1.getId(), resultadoCategoria1.getId());
        assertEquals(categoria1.getNombre(), resultadoCategoria1.getNombre());

        Categoria resultadoCategoria2 = resultado.get(1);
        assertEquals(categoria2.getId(), resultadoCategoria2.getId());
        assertEquals(categoria2.getNombre(), resultadoCategoria2.getNombre());

        verify(categoriaRepository, times(1)).findAll();
    }
}