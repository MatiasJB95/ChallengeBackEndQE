import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Profesor;
import com.matiasbadano.challeng.models.ProfesorCurso;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorCursoRepository;
import com.matiasbadano.challeng.services.ProfesorCursoService;
import com.matiasbadano.challeng.config.CursoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProfesorCursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ProfesorCursoRepository profesorCursoRepository;

    private ProfesorCursoService profesorCursoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        profesorCursoService = new ProfesorCursoService(profesorCursoRepository, cursoRepository);
    }
    @Test
    public void testRemoverProfesorDeCurso_CursoExistente() {
        Long profesorId = 1L;
        Long cursoId = 1L;

        Curso curso = new Curso();
        curso.setId(Math.toIntExact(cursoId));
        curso.setProfesor(new Profesor());

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(curso));

        profesorCursoService.removerProfesorDeCurso(profesorId, cursoId);

        verify(profesorCursoRepository, times(1)).deleteByProfesorIdAndCursoId(profesorId, cursoId);
        verify(cursoRepository, times(1)).save(curso);
        assertEquals(null, curso.getProfesor());
    }

    @Test
    public void testRemoverProfesorDeCurso_CursoNoExistente() {
        Long profesorId = 1L;
        Long cursoId = 1L;

        when(cursoRepository.findById(cursoId)).thenReturn(Optional.empty());

        try {
            profesorCursoService.removerProfesorDeCurso(profesorId, cursoId);
        } catch (CursoNotFoundException e) {
            assertEquals("Curso no encontrado", e.getMessage());
        }


        verify(cursoRepository, never()).save(any());
    }
    @Test
    public void testGuardarProfesorCurso() {
        ProfesorCurso profesorCurso = new ProfesorCurso();

        profesorCursoService.guardarProfesorCurso(profesorCurso);

        verify(profesorCursoRepository, times(1)).save(profesorCurso);
    }


}