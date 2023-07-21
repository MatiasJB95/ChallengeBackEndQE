package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.dto.CursoDTO;
import com.matiasbadano.challeng.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    @Query("SELECT new com.matiasbadano.challeng.dto.CursoDTO(c.nombre, c.turno, c.contenido) FROM Curso c")
    List<CursoDTO> findAllCursosDTO();
    @Query("SELECT c.nombre FROM Curso c WHERE c.categoria.id = :categoriaId")
    List<String> findNombresCursosByCategoriaId(@Param("categoriaId") Long categoriaId);


}
