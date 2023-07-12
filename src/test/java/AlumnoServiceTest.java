import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.InformacionAdicionalRepository;
import com.matiasbadano.challeng.repository.InscripcionRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class AlumnoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private InformacionAdicionalRepository informacionAdicionalRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;
    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    public PasswordEncoder passwordEncoder;
    @InjectMocks
    private AlumnoService alumnoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearAlumno() {
        String nombre = "Pepito Sand";
        String email = "Pepitosand@alumno.com";
        String contrasena = "password";

        when(passwordEncoder.encode(contrasena)).thenReturn("encodedPassword");

        alumnoService.crearAlumno(nombre, email, contrasena);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }
    @Test
    public void testObtenerAlumnoPorId() {
        Long id = 1L;

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1);
        usuarioMock.setNombre("Pepito Sand");
        usuarioMock.setEmail("Pepitosand@alumno.com");

        Alumno alumnoMock = new Alumno();
        alumnoMock.setId(Math.toIntExact(id));
        alumnoMock.setUsuario(usuarioMock);
        InformacionAdicional informacionAdicionalMock = new InformacionAdicional();
        informacionAdicionalMock.setNacionalidad("Argentina");
        informacionAdicionalMock.setPaisResidencia("Argentina");
        informacionAdicionalMock.setEdad(25);
        informacionAdicionalMock.setTelefono("123456789");

        Curso cursoMock = new Curso();
        cursoMock.setNombre("Curso 1");

        Inscripcion inscripcionMock = new Inscripcion();
        inscripcionMock.setCurso(cursoMock);

        when(alumnoRepository.findById(id.intValue())).thenReturn(Optional.of(alumnoMock));
        when(informacionAdicionalRepository.findByAlumnoId(Math.toIntExact(id))).thenReturn(informacionAdicionalMock);
        when(inscripcionRepository.findByAlumnoId(Math.toIntExact(id))).thenReturn(List.of(inscripcionMock));

        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorId(id);

        assertNotNull(alumnoMock);
        assertEquals(id, alumnoMock.getId());
        assertEquals("Argentina", alumnoDTO.getNacionalidad());
        assertEquals("Argentina", alumnoDTO.getPaisResidencia());
        assertEquals(25, alumnoDTO.getEdad());
        assertEquals("123456789", alumnoDTO.getTelefono());
        assertEquals(1, alumnoDTO.getCursos().size());
        assertEquals("Curso 1", alumnoDTO.getCursos().get(0));

        verify(alumnoRepository, times(1)).findById(id.intValue());
        verify(informacionAdicionalRepository, times(1)).findByAlumnoId(Math.toIntExact(id));
        verify(inscripcionRepository, times(1)).findByAlumnoId(Math.toIntExact(id));
    }
    @Test
    public void testGetAllAlumnos() {
        Alumno alumno1 = new Alumno();
        alumno1.setId(1);
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("John Doe");
        usuario1.setEmail("john.doe@example.com");
        alumno1.setUsuario(usuario1);

        Alumno alumno2 = new Alumno();
        alumno2.setId(2);
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Jane Smith");
        usuario2.setEmail("jane.smith@example.com");
        alumno2.setUsuario(usuario2);

        InformacionAdicional informacionAdicional = new InformacionAdicional();
        informacionAdicional.setNacionalidad("Argentina");
        informacionAdicional.setPaisResidencia("Argentina");
        informacionAdicional.setEdad(null);
        informacionAdicional.setTelefono("123456789");

        Inscripcion inscripcion = new Inscripcion();
        Curso curso = new Curso();
        curso.setNombre("Curso 1");
        inscripcion.setCurso(curso);

        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno1, alumno2));
        when(informacionAdicionalRepository.findByAlumnoId(eq(alumno1.getId()))).thenReturn(informacionAdicional);
        when(informacionAdicionalRepository.findByAlumnoId(eq(alumno2.getId()))).thenReturn(null);
        when(inscripcionRepository.findByAlumnoId(anyInt())).thenReturn(Arrays.asList(inscripcion));

        List<AlumnoDTO> resultado = alumnoService.getAllAlumnos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        AlumnoDTO alumnoDTO1 = resultado.get(0);
        assertEquals(alumno1.getId(), alumnoDTO1.getId());
        assertEquals(usuario1.getNombre(), alumnoDTO1.getNombre());
        assertEquals(usuario1.getEmail(), alumnoDTO1.getEmail());
        assertEquals(informacionAdicional.getNacionalidad(), alumnoDTO1.getNacionalidad());
        assertEquals(informacionAdicional.getPaisResidencia(), alumnoDTO1.getPaisResidencia());
        assertNull(alumnoDTO1.getEdad());
        assertEquals(informacionAdicional.getTelefono(), alumnoDTO1.getTelefono());
        assertEquals(1, alumnoDTO1.getCursos().size());
        assertEquals(curso.getNombre(), alumnoDTO1.getCursos().get(0));

        AlumnoDTO alumnoDTO2 = resultado.get(1);
        assertEquals(alumno2.getId(), alumnoDTO2.getId());
        assertEquals(usuario2.getNombre(), alumnoDTO2.getNombre());
        assertEquals(usuario2.getEmail(), alumnoDTO2.getEmail());
        assertNull(alumnoDTO2.getNacionalidad());
        assertNull(alumnoDTO2.getPaisResidencia());
        assertNull(alumnoDTO2.getEdad());
        assertNull(alumnoDTO2.getTelefono());
        assertEquals(1, alumnoDTO2.getCursos().size());
        assertEquals(curso.getNombre(), alumnoDTO2.getCursos().get(0));

        verify(alumnoRepository, times(1)).findAll();
        verify(informacionAdicionalRepository, times(1)).findByAlumnoId(eq(alumno1.getId()));
        verify(informacionAdicionalRepository, times(1)).findByAlumnoId(eq(alumno2.getId()));
        verify(inscripcionRepository, times(2)).findByAlumnoId(anyInt());
    }

}