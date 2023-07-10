package com.matiasbadano.challeng.services;


import com.matiasbadano.challeng.config.AlumnoNotFoundException;
import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.InformacionAdicionalRepository;
import com.matiasbadano.challeng.repository.InscripcionRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final InformacionAdicionalRepository informacionAdicionalRepository;
    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AlumnoService(AlumnoRepository alumnoRepository,
                         InformacionAdicionalRepository informacionAdicionalRepository,
                         InscripcionRepository inscripcionRepository,
                         UsuarioRepository usuarioRepository) {
        this.alumnoRepository = alumnoRepository;
        this.informacionAdicionalRepository =informacionAdicionalRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void crearAlumno(String nombre, String email, String contrasena) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setRol(Rol.ALUMNO);

        usuarioRepository.save(usuario);

        Alumno alumno = new Alumno();
        alumno.setUsuario(usuario);


        alumnoRepository.save(alumno);
    }

    public AlumnoDTO obtenerAlumnoPorId(Long id) {
        Integer idInteger = id.intValue();
        Alumno alumno = alumnoRepository.findById(idInteger)
                .orElseThrow(() -> new AlumnoNotFoundException("El alumno con ID " + id + " no existe."));

        AlumnoDTO alumnoDTO = new AlumnoDTO();
        alumnoDTO.setId(alumno.getId());
        alumnoDTO.setNombre(alumno.getUsuario().getNombre());
        alumnoDTO.setEmail(alumno.getUsuario().getEmail());

        InformacionAdicional informacionAdicional = informacionAdicionalRepository.findByAlumnoId(alumno.getId());
        if (informacionAdicional != null) {
            alumnoDTO.setNacionalidad(informacionAdicional.getNacionalidad());
            alumnoDTO.setPaisResidencia(informacionAdicional.getPaisResidencia());
            alumnoDTO.setEdad(informacionAdicional.getEdad());
            alumnoDTO.setTelefono(informacionAdicional.getTelefono());
        }

        List<Inscripcion> inscripciones = inscripcionRepository.findByAlumnoId(alumno.getId());
        List<String> nombresCursos = new ArrayList<>();

        for (Inscripcion inscripcion : inscripciones) {
            nombresCursos.add(inscripcion.getCurso().getNombre());
        }
        alumnoDTO.setCursos(nombresCursos);

        return alumnoDTO;
    }

    public List<AlumnoDTO> getAllAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        List<AlumnoDTO> alumnoDTOs = new ArrayList<>();

        for (Alumno alumno : alumnos) {
            AlumnoDTO alumnoDTO = new AlumnoDTO();
            alumnoDTO.setId(alumno.getId());
            alumnoDTO.setNombre(alumno.getUsuario().getNombre());
            alumnoDTO.setEmail(alumno.getUsuario().getEmail());

            InformacionAdicional informacionAdicional = informacionAdicionalRepository.findByAlumnoId(alumno.getId());
            if (informacionAdicional != null) {
                alumnoDTO.setNacionalidad(informacionAdicional.getNacionalidad());
                alumnoDTO.setPaisResidencia(informacionAdicional.getPaisResidencia());
                alumnoDTO.setEdad(informacionAdicional.getEdad());
                alumnoDTO.setTelefono(informacionAdicional.getTelefono());
            }

            List<Inscripcion> inscripciones = inscripcionRepository.findByAlumnoId(alumno.getId());
            List<String> nombresCursos = new ArrayList<>();

            for (Inscripcion inscripcion : inscripciones) {

                nombresCursos.add(inscripcion.getCurso().getNombre());

            }
            alumnoDTO.setCursos(nombresCursos);
            alumnoDTOs.add(alumnoDTO);
        }

        return alumnoDTOs;
    }

    public void actualizarAlumno(AlumnoDTO alumno) {
        Alumno alumnoExistente = alumnoRepository.findById(alumno.getId())
                .orElseThrow(() -> new AlumnoNotFoundException("El alumno con ID " + alumno.getId() + " no existe."));

        Usuario usuario = alumnoExistente.getUsuario();
        usuario.setNombre(alumno.getNombre());
        usuario.setEmail(alumno.getEmail());

        InformacionAdicional informacionAdicional = alumnoExistente.getInformacionAdicional();
        if (informacionAdicional == null) {
            informacionAdicional = new InformacionAdicional();
            informacionAdicional.setAlumno(alumnoExistente);
            alumnoExistente.setInformacionAdicional(informacionAdicional);
        }
        informacionAdicional.setNacionalidad(alumno.getNacionalidad());
        informacionAdicional.setPaisResidencia(alumno.getPaisResidencia());
        informacionAdicional.setEdad(alumno.getEdad());
        informacionAdicional.setTelefono(alumno.getTelefono());

        // Guardar la instancia de InformacionAdicional antes de guardar el Alumno
        informacionAdicionalRepository.save(informacionAdicional);
        alumnoRepository.save(alumnoExistente);
    }
    public List<Alumno> obtenerAlumnosPorCurso(Long cursoId) {
        return alumnoRepository.findByInscripcionesCursoId(cursoId);
    }
    public List<Alumno> buscarPorNombre(String nombre) {
  return alumnoRepository.findByUsuarioNombreContainingIgnoreCase(nombre);
    }

    public void eliminarAlumno(Integer id) {
        alumnoRepository.deleteById(id);
    }
}
