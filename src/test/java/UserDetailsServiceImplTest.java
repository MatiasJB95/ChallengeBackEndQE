import com.matiasbadano.challeng.models.Rol;
import com.matiasbadano.challeng.models.Usuario;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        userDetailsService = new UserDetailsServiceImpl(usuarioRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "user@example.com";

        Usuario usuario = Mockito.mock(Usuario.class);
        Mockito.when(usuario.getEmail()).thenReturn(username);
        Mockito.when(usuario.getContrasena()).thenReturn("password");
        Mockito.when(usuario.getRol()).thenReturn(Rol.PROFESOR);

        when(usuarioRepository.findByEmail(username)).thenReturn(usuario);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        verify(usuarioRepository).findByEmail(username);

        assertEquals(usuario.getEmail(), userDetails.getUsername());
        assertEquals(usuario.getContrasena(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());

    }
    @Test
    public void testGetAuthorities() {
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(usuarioRepository);

        Rol rol = Rol.PROFESOR;

        Collection<? extends GrantedAuthority> authorities = userDetailsService.getAuthorities(rol);

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_PROFESOR")));
    }
}