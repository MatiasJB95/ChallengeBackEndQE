import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.ProfesorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private CursoRepository cursoRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private ProfesorService profesorService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        profesorService = new ProfesorService(profesorRepository, usuarioRepository, cursoRepository, passwordEncoder);
    }

    @Test
    public void testCrearProfesor() {
        String nombre = "Pepe Sand";
        String email = "PepeSand@profesor.com";
        String contrasena = "password";
        int categoriaId = 1;

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setRol(Rol.PROFESOR);

        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(passwordEncoder.encode(contrasena)).thenReturn("encodedPassword");
        Profesor profesor = new Profesor();
        profesor.setUsuario(usuario);
        profesor.setCategoriaId(categoriaId);

        when(profesorRepository.save(any())).thenReturn(profesor);

        profesorService.crearProfesor(nombre, email, contrasena, categoriaId);

        verify(usuarioRepository, times(1)).save(any());
        verify(profesorRepository, times(1)).save(any());
    }
    @Test
    public void testActualizarProfesor_ProfesorExistente() {
        Integer profesorId = 1;
        ProfesorDTO profesorDTO = new ProfesorDTO();
        profesorDTO.setNombre("Nuevo Nombre");
        profesorDTO.setEmail("nuevo@email.com");
        int categoriaId = 2;

        Profesor profesorExistente = new Profesor();
        profesorExistente.setId(profesorId);
        Usuario usuario = new Usuario();
        usuario.setNombre("Nombre Anterior");
        usuario.setEmail("email@anterior.com");
        profesorExistente.setUsuario(usuario);

        when(profesorRepository.findById(profesorId)).thenReturn(Optional.of(profesorExistente));

        profesorService.actualizarProfesor(profesorId, profesorDTO, categoriaId);

        assertEquals(profesorDTO.getNombre(), usuario.getNombre());
        assertEquals(profesorDTO.getEmail()
                , usuario.getEmail());
        assertEquals(categoriaId, profesorExistente.getCategoriaId());

        verify(profesorRepository, times(1)).save(profesorExistente);
    }

    @Test
    public void testActualizarProfesor_ProfesorNoExistente() {
        Integer profesorId = 1;
        ProfesorDTO profesorDTO = new ProfesorDTO();
        int categoriaId = 2;

        when(profesorRepository.findById(profesorId)).thenReturn(Optional.empty());

        assertThrows(ProfesorNotFoundException.class,
                () -> profesorService.actualizarProfesor(profesorId, profesorDTO, categoriaId));

        verify(profesorRepository, never()).save(any());
    }
    @Test
    public void testObtenerTodosLosProfesoresDTO() {
        Categoria categoria1 = new Categoria(1L, "Categoria 1", new ArrayList<>());

        Curso curso1 = new Curso();
        curso1.setId(1);
        curso1.setNombre("Curso 1");
        curso1.setCategoria(categoria1);

        Curso curso2 = new Curso();
        curso2.setId(2);
        curso2.setNombre("Curso 2");
        curso2.setCategoria(categoria1);

        Profesor profesor1 = new Profesor(1, new Usuario("Pepe Sand", "PepeSand@profesor.com"));
        profesor1.setCategoria(categoria1);

        ProfesorCurso profesorCurso1 = new ProfesorCurso();
        profesorCurso1.setProfesor(profesor1);
        profesorCurso1.setCurso(curso1);
        profesorCurso1.setTurno(Turno.Mañana);

        ProfesorCurso profesorCurso2 = new ProfesorCurso();
        profesorCurso2.setProfesor(profesor1);
        profesorCurso2.setCurso(curso2);
        profesorCurso2.setTurno(Turno.Tarde);

        profesor1.setCursos(Arrays.asList(profesorCurso1, profesorCurso2));

        Profesor profesor2 = new Profesor(2, new Usuario("Virginia Cafe", "VirginiaCafe@profesor.com"));
        profesor2.setCategoria(categoria1);

        ProfesorCurso profesorCurso3 = new ProfesorCurso();
        profesorCurso3.setProfesor(profesor2);
        profesorCurso3.setCurso(curso1);
        profesorCurso3.setTurno(Turno.Mañana);

        ProfesorCurso profesorCurso4 = new ProfesorCurso();
        profesorCurso4.setProfesor(profesor2);
        profesorCurso4.setCurso(curso2);
        profesorCurso4.setTurno(Turno.Tarde);

        profesor2.setCursos(Arrays.asList(profesorCurso3, profesorCurso4));

        List<Profesor> profesoresMock = Arrays.asList(profesor1, profesor2);

        when(profesorRepository.findAll()).thenReturn(profesoresMock);

        List<ProfesorDTO> resultado = profesorService.obtenerTodosLosProfesoresDTO();

        assertEquals(2, resultado.size());

        ProfesorDTO resultadoProfesor1 = resultado.get(0);
        assertEquals(profesor1.getId(), resultadoProfesor1.getId());
        assertEquals(profesor1.getUsuario().getNombre(), resultadoProfesor1.getNombre());
        assertEquals(profesor1.getUsuario().getEmail(), resultadoProfesor1.getEmail());
        assertEquals(categoria1.getNombre(), resultadoProfesor1.getNombreCategoria());
        assertEquals(2, resultadoProfesor1.getNombresCursos().size());
        assertEquals(2, resultadoProfesor1.getTurnosCursos().size());

        ProfesorDTO resultadoProfesor2 = resultado.get(1);
        assertEquals(profesor2.getId(), resultadoProfesor2.getId());
        assertEquals(profesor2.getUsuario().getNombre(), resultadoProfesor2.getNombre());
        assertEquals(profesor2.getUsuario().getEmail(), resultadoProfesor2.getEmail());
        assertEquals(categoria1.getNombre(), resultadoProfesor2.getNombreCategoria());
        assertEquals(2, resultadoProfesor2.getNombresCursos().size());
        assertEquals(2, resultadoProfesor2.getTurnosCursos().size());
    }
    @Test
    public void testObtenerTodosLosProfesores() {
        List<Profesor> profesoresMock = new ArrayList<>();
        profesoresMock.add(new Profesor(1, new Usuario("Pepe Sand", "PepeSand@profesor.com")));
        profesoresMock.add(new Profesor(2, new Usuario("Virgina Cafe", "VirginiaCafe@profesor.com")));
        when(profesorRepository.findAll()).thenReturn(profesoresMock);
        List<Profesor> resultado = profesorService.obtenerTodosLosProfesores();

        assertEquals(profesoresMock.size(), resultado.size());
        for (int i = 0; i < profesoresMock.size(); i++) {
            Profesor profesorMock = profesoresMock.get(i);
            Profesor profesorResultado = resultado.get(i);
            assertEquals(profesorMock.getId(), profesorResultado.getId());
            assertEquals(profesorMock.getUsuario().getNombre(), profesorResultado.getUsuario().getNombre());
            assertEquals(profesorMock.getUsuario().getEmail(), profesorResultado.getUsuario().getEmail());
        }
    }
    @Test
    public void testObtenerProfesorDTOPorId() {
        Profesor profesorMock = new Profesor(1, new Usuario("Pepe Sand", "PepeSand@profesor.com"));
        profesorMock.setCategoria(new Categoria(1L, "Categoria 1"));

        Curso curso1 = new Curso();
        curso1.setId(1);
        curso1.setNombre("Curso 1");
        Curso curso2 = new Curso();
        curso2.setId(2);
        curso2.setNombre("Curso 2");

        ProfesorCurso profesorCurso1 = new ProfesorCurso();
        profesorCurso1.setProfesor(profesorMock);
        profesorCurso1.setCurso(curso1);
        profesorCurso1.setTurno(Turno.Mañana);
        ProfesorCurso profesorCurso2 = new ProfesorCurso();
        profesorCurso2.setProfesor(profesorMock);
        profesorCurso2.setCurso(curso2);
        profesorCurso2.setTurno(Turno.Tarde);
        profesorMock.setCursos(Arrays.asList(profesorCurso1, profesorCurso2));

        when(profesorRepository.findById(1)).thenReturn(Optional.of(profesorMock));

        ProfesorDTO resultado = profesorService.obtenerProfesorDTOPorId(1L);

        assertEquals(profesorMock.getId(), resultado.getId());
        assertEquals(profesorMock.getUsuario().getNombre(), resultado.getNombre());
        assertEquals(profesorMock.getUsuario().getEmail(), resultado.getEmail());
        assertEquals(profesorMock.getCategoria().getNombre(), resultado.getNombreCategoria());
        assertEquals(2, resultado.getNombresCursos().size());
        assertEquals(2, resultado.getTurnosCursos().size());
    }
    @Test
    public void testObtenerProfesorPorId_ProfesorExistente() {

        Profesor profesorMock = new Profesor(1, new Usuario("Pepe Sand", "PepeSand@profesor.com"));


        when(profesorRepository.findById(1)).thenReturn(Optional.of(profesorMock));


        Profesor resultado = profesorService.obtenerProfesorPorId(1);

        assertEquals(profesorMock.getId(), resultado.getId());
        assertEquals(profesorMock.getUsuario().getNombre(), resultado.getUsuario().getNombre());
        assertEquals(profesorMock.getUsuario().getEmail(), resultado.getUsuario().getEmail());
    }

    @Test
    public void testObtenerProfesorPorId_ProfesorNoExistente() {

        when(profesorRepository.findById(1)).thenReturn(Optional.empty());

        ProfesorNotFoundException excepcion = assertThrows(ProfesorNotFoundException.class, () -> {
            profesorService.obtenerProfesorPorId(1);
        });


        assertEquals("El profesor con ID 1 no existe.", excepcion.getMessage());
    }
    @Test
    public void testEliminarProfesor() {

        Integer profesorId = 1;

        profesorService.eliminarProfesor(profesorId);

        verify(profesorRepository).deleteById(profesorId);
    }
    @Test
    public void testObtenerCategoriaPorProfesor() {

        Long profesorId = 1L;

        Categoria categoria = new Categoria(1L, "Categoria 1");


        Profesor profesor = new Profesor(1, new Usuario("Profesor 1", "profesor1@example.com"));
        profesor.setCategoria(categoria);

        when(profesorRepository.findById(profesorId.intValue())).thenReturn(Optional.of(profesor));

        Categoria resultado = profesorService.obtenerCategoriaPorProfesor(profesorId);

        verify(profesorRepository).findById(profesorId.intValue());

        assertEquals(categoria, resultado);
    }



}