import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.services.CursoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @Test
    public void testGetAllCursos() {
        Curso curso1 = new Curso();
        curso1.setId(1);
        curso1.setNombre("Curso 1");
        curso1.setTurno(Turno.Mañana);
        Categoria categoria1 = new Categoria(1L, "Categoria 1", new ArrayList<>());
        curso1.setCategoria(categoria1);

        Curso curso2 = new Curso();
        curso2.setId(2);
        curso2.setNombre("Curso 2");
        curso2.setTurno(Turno.Tarde);
        curso2.setCategoria(categoria1);

        List<Curso> cursosMock = Arrays.asList(curso1, curso2);

        when(cursoRepository.findAll()).thenReturn(cursosMock);

        List<CursoDTO> resultado = cursoService.getAllCursos();

        assertEquals(2, resultado.size());

        CursoDTO cursoDTO1 = resultado.get(0);
        assertEquals(curso1.getNombre(), cursoDTO1.getNombre());
        assertEquals(curso1.getTurno(), cursoDTO1.getTurno());


        CursoDTO cursoDTO2 = resultado.get(1);
        assertEquals(curso2.getNombre(), cursoDTO2.getNombre());
        assertEquals(curso2.getTurno(), cursoDTO2.getTurno());

    }
    @Test
    public void testConvertToDTO() {
        Curso curso = new Curso();
        curso.setId(1);
        curso.setNombre("Curso 1");
        curso.setTurno(Turno.Mañana);
        Profesor profesor = new Profesor();
        Usuario usuario = new Usuario();
        usuario.setNombre("Pepe Sand");
        profesor.setUsuario(usuario);
        curso.setProfesor(profesor);

        CursoDTO cursoDTO = cursoService.convertToDTO(curso);

        assertNotNull(cursoDTO);
        assertEquals(curso.getId(), cursoDTO.getId());
        assertEquals(curso.getNombre(), cursoDTO.getNombre());
        assertEquals(Turno.Mañana, cursoDTO.getTurno());
        assertEquals("Pepe Sand", cursoDTO.getNombreProfesor());
    }
    @Test
    public void testEliminarCurso() {
        Long id = 1L;
        cursoService.eliminarCurso(id);
        verify(cursoRepository, times(1)).deleteById(id);
    }
}




