package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.dto.AlumnoDTO;
import com.matiasbadano.challeng.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    @Query("SELECT new com.matiasbadano.challeng.dto.AlumnoDTO(u.nombre, u.email, ia.nacionalidad, ia.paisResidencia, ia.edad, ia.telefono) FROM Alumno a JOIN a.usuario u JOIN a.informacionAdicional ia")
    List<AlumnoDTO> obtenerTodosLosAlumnosDTO();
    List<Alumno> findByInscripcionesCursoId(Long cursoId);
    List<Alumno> findByUsuarioNombreContainingIgnoreCase(String nombre);


}
