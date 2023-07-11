import com.matiasbadano.challeng.models.Alumno;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class AlumnoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

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
        String nombre = "John Doe";
        String email = "john.doe@example.com";
        String contrasena = "password";
        
        when(passwordEncoder.encode(contrasena)).thenReturn("encodedPassword");

        alumnoService.crearAlumno(nombre, email, contrasena);

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }
}