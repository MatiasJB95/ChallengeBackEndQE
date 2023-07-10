package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.ProfesorDTO;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Profesor;
import com.matiasbadano.challeng.models.ProfesorCurso;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfesorService {
    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
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

    }
