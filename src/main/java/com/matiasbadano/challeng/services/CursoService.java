package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.models.Contenido;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Profesor;
import com.matiasbadano.challeng.models.Turno;
import com.matiasbadano.challeng.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
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
