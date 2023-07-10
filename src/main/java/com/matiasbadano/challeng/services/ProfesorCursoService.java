package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.config.CursoNotFoundException;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.ProfesorCurso;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorCursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfesorCursoService {
    private final ProfesorCursoRepository profesorCursoRepository;
private final CursoRepository cursoRepository;
    public ProfesorCursoService(ProfesorCursoRepository profesorCursoRepository,
                                CursoRepository cursoRepository  ) {
        this.profesorCursoRepository = profesorCursoRepository;
        this.cursoRepository = cursoRepository;
    }
    @Transactional
    public void removerProfesorDeCurso(Long profesorId, Long cursoId) {
        profesorCursoRepository.deleteByProfesorIdAndCursoId(profesorId, cursoId);
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotFoundException("Curso no encontrado"));

        curso.setProfesor(null);
        cursoRepository.save(curso);
    }

    public void guardarProfesorCurso(ProfesorCurso profesorCurso) {

        profesorCursoRepository.save(profesorCurso);
    }
}

