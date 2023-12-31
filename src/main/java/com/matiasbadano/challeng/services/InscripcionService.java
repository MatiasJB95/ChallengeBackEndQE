package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.InscripcionDTO;
import com.matiasbadano.challeng.models.Alumno;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Inscripcion;
import com.matiasbadano.challeng.repository.AlumnoRepository;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.InscripcionRepository;
import com.matiasbadano.challeng.services.exception.AlumnoNotFoundException;
import com.matiasbadano.challeng.services.exception.CursoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository, AlumnoRepository alumnoRepository, CursoRepository cursoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.alumnoRepository = alumnoRepository;
        this.cursoRepository = cursoRepository;
    }

    public void guardarInscripcion(InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcion = new Inscripcion();

        Alumno alumno = alumnoRepository.findById(inscripcionDTO.getAlumnoId().intValue())
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno no encontrado"));

        Curso curso = cursoRepository.findById(inscripcionDTO.getCursoId())
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado"));


        inscripcion.setAlumno(alumno);
        inscripcion.setCurso(curso);


        inscripcionRepository.save(inscripcion);
    }


    public List<InscripcionDTO> obtenerInscripcionesPorCursoId(Long cursoId) {
        List<Inscripcion> inscripciones = inscripcionRepository.findByCursoId(cursoId);
        List<InscripcionDTO> inscripcionesDTO = new ArrayList<>();

        for (Inscripcion inscripcion : inscripciones) {
            InscripcionDTO inscripcionDTO = new InscripcionDTO();
            inscripcionDTO.setId((long) inscripcion.getId());
            inscripcionDTO.setAlumnoId((long) inscripcion.getAlumno().getId());
            inscripcionDTO.setCursoId((long) inscripcion.getCurso().getId());

            inscripcionesDTO.add(inscripcionDTO);
        }

        return inscripcionesDTO;
    }

}