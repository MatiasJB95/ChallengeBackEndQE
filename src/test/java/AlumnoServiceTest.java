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
        usuario1.setNombre("Pepe Sand");
        usuario1.setEmail("PepeSand@alumno.com");
        alumno1.setUsuario(usuario1);

        Alumno alumno2 = new Alumno();
        alumno2.setId(2);
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Virginia Cafe");
        usuario2.setEmail( "VirginiaCafe@alumno.com");
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
    @Test
    public void testActualizarAlumno() {
        Long alumnoId = 1L;
        String nombre = "Pepe Sand";
        String email = "PepeSand@alumno.com";
        String nacionalidad = "Argentina";
        String paisResidencia = "Argentina";
        Integer edad = 25;
        String telefono = "123456789";

        // Mocks
        Alumno alumnoExistente = new Alumno();
        alumnoExistente.setId(Math.toIntExact(alumnoId));

        Usuario usuario = new Usuario();
        alumnoExistente.setUsuario(usuario);

        InformacionAdicional informacionAdicional = new InformacionAdicional();
        alumnoExistente.setInformacionAdicional(informacionAdicional);

        when(alumnoRepository.findById(Math.toIntExact(alumnoId))).thenReturn(Optional.of(alumnoExistente));

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(Math.toIntExact(alumnoId));
        alumnoDTO.setNombre(nombre);
        alumnoDTO.setEmail(email);
        alumnoDTO.setNacionalidad(nacionalidad);
        alumnoDTO.setPaisResidencia(paisResidencia);
        alumnoDTO.setEdad(edad);
        alumnoDTO.setTelefono(telefono);

        alumnoService.actualizarAlumno(alumnoDTO);

        assertEquals(nombre, usuario.getNombre());
        assertEquals(email, usuario.getEmail());
        assertEquals(nacionalidad, informacionAdicional.getNacionalidad());
        assertEquals(paisResidencia, informacionAdicional.getPaisResidencia());
        assertEquals(edad, informacionAdicional.getEdad());
        assertEquals(telefono, informacionAdicional.getTelefono());

        verify(alumnoRepository, times(1)).findById(Math.toIntExact(alumnoId));
        verify(informacionAdicionalRepository, times(1)).save(informacionAdicional);
        verify(alumnoRepository, times(1)).save(alumnoExistente);
    }
    @Test
    public void testBuscarPorNombre() {
        String nombre = "Pepe Sand";

        Alumno alumno1 = new Alumno();
        alumno1.setId(1);
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Pepe Sand");
        alumno1.setUsuario(usuario1);

        Alumno alumno2 = new Alumno();
        alumno2.setId(2);
        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Virginia Cafe");
        alumno2.setUsuario(usuario2);

        when(alumnoRepository.findByUsuarioNombreContainingIgnoreCase(nombre))
                .thenReturn(Arrays.asList(alumno1, alumno2));

        List<Alumno> resultado = alumnoService.buscarPorNombre(nombre);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        Alumno resultadoAlumno1 = resultado.get(0);
        assertEquals(alumno1.getId(), resultadoAlumno1.getId());
        assertEquals(usuario1.getNombre(), resultadoAlumno1.getUsuario().getNombre());

        Alumno resultadoAlumno2 = resultado.get(1);
        assertEquals(alumno2.getId(), resultadoAlumno2.getId());
        assertEquals(usuario2.getNombre(), resultadoAlumno2.getUsuario().getNombre());

        verify(alumnoRepository, times(1)).findByUsuarioNombreContainingIgnoreCase(nombre);
    }
    @Test
    public void testConvertirAlumnoAAlumnoDTO() {

        Alumno alumno = new Alumno();
        alumno.setId(1);
        Usuario usuario = new Usuario();
        usuario.setNombre("PepeSand");
        usuario.setEmail("PepeSand@alumno.com");
        alumno.setUsuario(usuario);


        AlumnoDTO alumnoDTO = alumnoService.convertirAlumnoAAlumnoDTO(alumno);

        assertNotNull(alumnoDTO);
        assertEquals(alumno.getId(), alumnoDTO.getId());
        assertEquals(usuario.getNombre(), alumnoDTO.getNombre());
        assertEquals(usuario.getEmail(), alumnoDTO.getEmail());
    }

    @Test
    public void testConvertirAlumnoAAlumnoDTO_AlumnoSinUsuario() {

        Alumno alumno = new Alumno();
        alumno.setId(1);


        AlumnoDTO alumnoDTO = alumnoService.convertirAlumnoAAlumnoDTO(alumno);


        assertNotNull(alumnoDTO);
        assertEquals(alumno.getId(), alumnoDTO.getId());
        assertNull(alumnoDTO.getNombre());
        assertNull(alumnoDTO.getEmail());
    }
    @Test
    public void testEliminarAlumno() {
        Integer id = 1;

        alumnoService.eliminarAlumno(id);
        
        verify(alumnoRepository, times(1)).deleteById(id);
    }
}