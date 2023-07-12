import com.matiasbadano.challeng.dto.InscripcionDTO;
import com.matiasbadano.challeng.models.Alumno;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Inscripcion;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.InscripcionRepository;
import com.matiasbadano.challeng.config.AlumnoNotFoundException;
import com.matiasbadano.challeng.config.CursoNotFoundException;
import com.matiasbadano.challeng.services.InscripcionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class InscripcionServiceTest {

    @Mock
    private InscripcionRepository inscripcionRepository;
    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private CursoRepository cursoRepository;

    private InscripcionService inscripcionService;

    public InscripcionServiceTest() {
        MockitoAnnotations.openMocks(this);
        inscripcionService = new InscripcionService(inscripcionRepository, alumnoRepository, cursoRepository);
    }

    @Test
    public void testGuardarInscripcion_AlumnoNoEncontrado() {
        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setAlumnoId(1L);
        inscripcionDTO.setCursoId(2L);

        when(alumnoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AlumnoNotFoundException.class, () -> inscripcionService.guardarInscripcion(inscripcionDTO));

        verify(alumnoRepository, times(1)).findById(1);
        verify(cursoRepository, never()).findById(anyLong());
        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }

    @Test
    public void testGuardarInscripcion_CursoNoEncontrado() {
        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setAlumnoId(1L);
        inscripcionDTO.setCursoId(2L);

        when(alumnoRepository.findById(1)).thenReturn(Optional.of(new Alumno()));
        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CursoNotFoundException.class, () -> inscripcionService.guardarInscripcion(inscripcionDTO));

        verify(alumnoRepository, times(1)).findById(1);
        verify(cursoRepository, times(1)).findById(2L);
        verify(inscripcionRepository, never()).save(any(Inscripcion.class));
    }

    @Test
    public void testGuardarInscripcion_AlumnoYCursoEncontrados() {
        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setAlumnoId(1L); // ID de alumno ficticio
        inscripcionDTO.setCursoId(2L); // ID de curso ficticio

        Alumno alumno = new Alumno();
        Curso curso = new Curso();

        when(alumnoRepository.findById(1)).thenReturn(Optional.of(alumno));
        when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));

        inscripcionService.guardarInscripcion(inscripcionDTO);

        verify(alumnoRepository, times(1)).findById(1);
        verify(cursoRepository, times(1)).findById(2L);
        verify(inscripcionRepository, times(1)).save(any(Inscripcion.class));
    }
    @Test
    public void testObtenerInscripcionesPorCursoId() {
        Long cursoId = 1L;
        List<Inscripcion> inscripcionesMock = new ArrayList<>();
        Inscripcion inscripcion1 = new Inscripcion();
        inscripcion1.setId(1);
        inscripcion1.setAlumno(new Alumno());
        inscripcion1.setCurso(new Curso());
        inscripcionesMock.add(inscripcion1);
        Inscripcion inscripcion2 = new Inscripcion();
        inscripcion2.setId(2);
        inscripcion2.setAlumno(new Alumno());
        inscripcion2.setCurso(new Curso());
        inscripcionesMock.add(inscripcion2);

        when(inscripcionRepository.findByCursoId(cursoId)).thenReturn(inscripcionesMock);

        List<InscripcionDTO> resultado = inscripcionService.obtenerInscripcionesPorCursoId(cursoId);

        assertEquals(inscripcionesMock.size(), resultado.size());
        assertEquals(inscripcion1.getId(), resultado.get(0).getId());
        assertEquals(inscripcion1.getAlumno().getId(), resultado.get(0).getAlumnoId());
        assertEquals(inscripcion1.getCurso().getId(), resultado.get(0).getCursoId());
        assertEquals(inscripcion2.getId(), resultado.get(1).getId());
        assertEquals(inscripcion2.getAlumno().getId(), resultado.get(1).getAlumnoId());
        assertEquals(inscripcion2.getCurso().getId(), resultado.get(1).getCursoId());

        verify(inscripcionRepository, times(1)).findByCursoId(cursoId);
    }

}