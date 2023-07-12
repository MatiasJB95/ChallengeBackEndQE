package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.models.*;
import com.matiasbadano.challeng.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CursoService {

    private final CursoRepository cursoRepository;




    public CursoService(CursoRepository cursoRepository  ) {
        this.cursoRepository = cursoRepository;

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

    public CursoDTO convertToDTO(Curso curso) {
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setNombre(curso.getNombre());
        cursoDTO.setTurno(Turno.valueOf(curso.getTurno().name()));

        Profesor profesor = curso.getProfesor();
        if (profesor != null) {
            cursoDTO.setNombreProfesor(profesor.getUsuario().getNombre());
        } else {
            cursoDTO.setNombreProfesor("Sin profesor asignado");
        }



        return cursoDTO;
    }

    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
