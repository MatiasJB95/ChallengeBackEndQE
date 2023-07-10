package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.config.ProfesorNotFoundException;
import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.repository.UsuarioRepository;
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
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ProfesorService(ProfesorRepository profesorRepository,
                           UsuarioRepository usuarioRepository  ) {
        this.profesorRepository = profesorRepository;
        this.usuarioRepository = usuarioRepository;
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

            profesoresDTO.add(profesorDTO);
        }

        return profesoresDTO;
    }
    public String obtenerNombreProfesorPorId(Integer profesorId) {
        Optional<Profesor> optionalProfesor = profesorRepository.findById(profesorId);
        if (optionalProfesor.isPresent()) {
            Profesor profesor = optionalProfesor.get();
            int idProfesor = profesor.getId();
            Usuario usuario = profesor.getUsuario();
            return usuario.getNombre();
        } else {
            throw new ProfesorNotFoundException("El profesor con ID " + profesorId + " no existe.");
        }
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
}
