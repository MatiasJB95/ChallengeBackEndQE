package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
import com.matiasbadano.challeng.services.exception.ProfesorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {
    private final ProfesorRepository profesorRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ProfesorService(ProfesorRepository profesorRepository,
                           UsuarioRepository usuarioRepository, CursoRepository cursoRepository, PasswordEncoder passwordEncoder  ) {
        this.profesorRepository = profesorRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void crearProfesor(String nombre, String email, String contrasena, int categoriaId) {

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        usuario.setRol(Rol.PROFESOR);

        usuarioRepository.save(usuario);

        Profesor profesor = new Profesor();
        profesor.setUsuario(usuario);
        profesor.setCategoriaId(categoriaId);

        profesorRepository.save(profesor);
    }

    public void actualizarProfesor(Integer profesorId, ProfesorDTO profesorDTO, int categoriaId) {
        Optional<Profesor> optionalProfesor = profesorRepository.findById(profesorId);

        if (optionalProfesor.isPresent()) {
            Profesor profesor = optionalProfesor.get();
            Usuario usuario = profesor.getUsuario();

            usuario.setNombre(profesorDTO.getNombre());
            usuario.setEmail(profesorDTO.getEmail());
            profesor.setCategoriaId(categoriaId);

            profesorRepository.save(profesor);
        } else {
            throw new ProfesorNotFoundException("El profesor con ID " + profesorId + " no existe.");
        }
    }


    public List<ProfesorDTO> obtenerTodosLosProfesoresDTO() {
        List<Profesor> profesores = profesorRepository.findAll();
        List<ProfesorDTO> profesoresDTO = new ArrayList<>();

        for (Profesor profesor : profesores) {
            String nombreCategoria = profesor.getCategoria().getNombre();

            List<String> nombresCursos = new ArrayList<>();
            List<String> turnosCursos = new ArrayList<>();

            for (ProfesorCurso profesorCurso : profesor.getCursos()) {
                Curso curso = profesorCurso.getCurso();
                if (curso != null) {
                    nombresCursos.add(curso.getNombre());
                }

                Turno turno = profesorCurso.getTurno();
                String nombreTurno = turno != null ? turno.name() : "Sin turno asignado";
                turnosCursos.add(nombreTurno);
            }

            ProfesorDTO profesorDTO = new ProfesorDTO(
                    profesor.getId(),
                    profesor.getUsuario().getNombre(),
                    profesor.getUsuario().getEmail()
            );
            profesorDTO.setNombreCategoria(nombreCategoria);
            profesorDTO.setNombresCursos(nombresCursos);
            profesorDTO.setTurnosCursos(turnosCursos);

            profesoresDTO.add(profesorDTO);
        }

        return profesoresDTO;
    }

    public List<Profesor> obtenerTodosLosProfesores() {
        return profesorRepository.findAll();
    }
    public ProfesorDTO obtenerProfesorDTOPorId(Long profesorId) {
        Profesor profesor = profesorRepository.findById(profesorId.intValue())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el profesor con el ID proporcionado"));

        String nombreCategoria = profesor.getCategoria().getNombre();

        List<String> nombresCursos = new ArrayList<>();
        List<String> turnosCursos = new ArrayList<>();

        for (ProfesorCurso profesorCurso : profesor.getCursos()) {
            Curso curso = profesorCurso.getCurso();
            nombresCursos.add(curso.getNombre());
            turnosCursos.add(profesorCurso.getTurno().name());
        }

        ProfesorDTO profesorDTO = new ProfesorDTO(
                profesor.getId(),
                profesor.getUsuario().getNombre(),
                profesor.getUsuario().getEmail()
        );
        profesorDTO.setNombreCategoria(nombreCategoria);
        profesorDTO.setNombresCursos(nombresCursos);
        profesorDTO.setTurnosCursos(turnosCursos);

        return profesorDTO;
    }

    public Profesor obtenerProfesorPorId(Integer id) {
        Optional<Profesor> optionalProfesor = profesorRepository.findById(id);
        if (optionalProfesor.isPresent()) {
            return optionalProfesor.get();
        } else {
            throw new ProfesorNotFoundException("El profesor con ID " + id + " no existe.");
        }
    }

    public void eliminarProfesor(Integer id) {
        profesorRepository.deleteById(id);
    }


    public Categoria obtenerCategoriaPorProfesor(Long profesorId) {
        Profesor profesor = profesorRepository.findById(Math.toIntExact(profesorId))
                .orElseThrow(() -> new ProfesorNotFoundException("Profesor no encontrado"));
        return profesor.getCategoria();
    }

}
