package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    List<Inscripcion> findByAlumnoId(int alumnoId);

}