package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.models.ProfesorCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorCursoRepository extends JpaRepository<ProfesorCurso, Integer> {
    void deleteByProfesorIdAndCursoId(Long profesorId, Long cursoId);


    }

