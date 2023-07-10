package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.models.ProfesorCurso;
import com.matiasbadano.challeng.repository.ProfesorCursoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfesorCursoService {
    private final ProfesorCursoRepository profesorCursoRepository;

    public ProfesorCursoService(ProfesorCursoRepository profesorCursoRepository) {
        this.profesorCursoRepository = profesorCursoRepository;
    }

    public void guardarProfesorCurso(ProfesorCurso profesorCurso) {
        profesorCursoRepository.save(profesorCurso);
    }
}