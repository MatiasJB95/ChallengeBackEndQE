package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.config.CategoriaNotFoundException;
import com.matiasbadano.challeng.config.CursoNotFoundException;
import com.matiasbadano.challeng.config.ProfesorNotFoundException;
import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CategoriaRepository;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProfesorRepository profesorRepository;


    public CursoService(CursoRepository cursoRepository,
                        CategoriaRepository categoriaRepository,
                        ProfesorRepository profesorRepository  ) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository =categoriaRepository;
        this.profesorRepository =profesorRepository;
    }
    public void guardarCurso(Curso curso) {

        cursoRepository.save(curso);
    }


    public Curso obtenerCursoPorId(Long id) {
        Optional<Curso> optionalCurso = cursoRepository.findById(id);
        if (optionalCurso.isPresent()) {
            return optionalCurso.get();
        } else {
            throw new CursoNotFoundException("El curso con ID " + id + " no existe.");
        }
    }


    public List<CursoDTO> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CursoDTO convertToDTO(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setNombre(curso.getNombre());
        cursoDTO.setTurno(curso.getTurno().name());

        Profesor profesor = curso.getProfesor();
        if (profesor != null) {
            cursoDTO.setNombreProfesor(profesor.getUsuario().getNombre());
        } else {
            cursoDTO.setNombreProfesor("Sin profesor asignado");
        }



        return cursoDTO;
    }


}
