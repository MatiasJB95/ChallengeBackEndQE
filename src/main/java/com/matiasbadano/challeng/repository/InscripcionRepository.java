package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.dto.InscripcionDTO;
import com.matiasbadano.challeng.models.Alumno;
import com.matiasbadano.challeng.models.Curso;
import com.matiasbadano.challeng.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByAlumnoId(int alumnoId);

    @Query("SELECT i.curso FROM Inscripcion i WHERE i.alumno = :alumno")
    List<Curso> findCursosByAlumno(@Param("alumno") Alumno alumno);
    @Query("SELECT inscripcion.curso.nombre FROM Inscripcion inscripcion WHERE inscripcion.alumno.id = :alumnoId")
    List<String> findCursosByAlumnoId(@Param("alumnoId") Long alumnoId);

    @Query("SELECT i FROM Inscripcion i WHERE i.alumno = :alumno")
    List<InscripcionDTO> obtenerInscripcionesPorAlumnoId(Long alumno);

}