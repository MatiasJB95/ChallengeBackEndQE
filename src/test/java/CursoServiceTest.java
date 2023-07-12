import com.matiasbadano.challeng.config.CursoNotFoundException;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.services.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;
@Mock
    private CursoService cursoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cursoService = new CursoService(cursoRepository);
    }

    @Test
    public void testGuardarCurso() {
        Curso curso = new Curso();

        cursoService.guardarCurso(curso);

        verify(cursoRepository, times(1)).save(curso);
    }
    @Test
    public void testObtenerCursoPorId_CursoExistente() {
        Long cursoId = 1L;
        Curso cursoMock = new Curso();
        cursoMock.setId(Math.toIntExact(cursoId));

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(cursoMock));

        Curso resultado = cursoService.obtenerCursoPorId(cursoId);

        assertNotNull(resultado);
        assertEquals(cursoId, resultado.getId());
        verify(cursoRepository, times(1)).findById(cursoId);
    }
    @Test
    public void testObtenerCursoPorId_CursoNoExistente() {
        Long cursoId = 1L;

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.empty());

        assertThrows(CursoNotFoundException.class, () -> {
            cursoService.obtenerCursoPorId(cursoId);
        });

        verify(cursoRepository, times(1)).findById(cursoId);
    }
}


