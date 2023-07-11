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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        // Alumno mock
        Alumno alumnoMock = new Alumno();
        alumnoMock.setId(Math.toIntExact(id));
        alumnoMock.setUsuario(usuarioMock);

        // InformacionAdicional mock
        InformacionAdicional informacionAdicionalMock = new InformacionAdicional();
        informacionAdicionalMock.setNacionalidad("Argentina");
        informacionAdicionalMock.setPaisResidencia("Argentina");
        informacionAdicionalMock.setEdad(25);
        informacionAdicionalMock.setTelefono("123456789");

        // Inscripcion mock
        Curso cursoMock = new Curso();
        cursoMock.setNombre("Curso 1");

        Inscripcion inscripcionMock = new Inscripcion();
        inscripcionMock.setCurso(cursoMock);

        // Configurar comportamientos simulados para los repositorios
        when(alumnoRepository.findById(id.intValue())).thenReturn(Optional.of(alumnoMock));
        when(informacionAdicionalRepository.findByAlumnoId(Math.toIntExact(id))).thenReturn(informacionAdicionalMock);
        when(inscripcionRepository.findByAlumnoId(Math.toIntExact(id))).thenReturn(List.of(inscripcionMock));

        // Llamada al método que se va a probar
        AlumnoDTO alumnoDTO = alumnoService.obtenerAlumnoPorId(id);

        // Verificaciones
        assertNotNull(alumnoMock);
        assertEquals(id, alumnoMock.getId());
        assertEquals("Argentina", alumnoDTO.getNacionalidad());
        assertEquals("Argentina", alumnoDTO.getPaisResidencia());
        assertEquals(25, alumnoDTO.getEdad());
        assertEquals("123456789", alumnoDTO.getTelefono());
        assertEquals(1, alumnoDTO.getCursos().size());
        assertEquals("Curso 1", alumnoDTO.getCursos().get(0));

        // Verificar llamadas a los repositorios
        verify(alumnoRepository, times(1)).findById(id.intValue());
        verify(informacionAdicionalRepository, times(1)).findByAlumnoId(Math.toIntExact(id));
        verify(inscripcionRepository, times(1)).findByAlumnoId(Math.toIntExact(id));
    }
}