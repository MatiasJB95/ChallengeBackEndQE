package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.models.Profesor;
import com.matiasbadano.challeng.dto.ProfesorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    @Query("SELECT new com.matiasbadano.challeng.dto.ProfesorDTO(" +
            "p.id, " +
            "u.nombre, " +
            "u.email, " +
            "c.nombre, " +
            "c.turno) " +
            "FROM Profesor p " +
            "JOIN p.usuario u " +
            "JOIN p.cursos pc " +
            "JOIN pc.curso c")
    List<ProfesorDTO> findAllProfesoresDTO();
}


