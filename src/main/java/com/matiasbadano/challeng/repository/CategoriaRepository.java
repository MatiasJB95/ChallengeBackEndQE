package com.matiasbadano.challeng.repository;

import com.matiasbadano.challeng.dto.CategoriaDTO;
import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT new com.matiasbadano.challeng.dto.CategoriaDTO(c.id, c.nombre, c.cursos) FROM Categoria c")
    List<CategoriaDTO> obtenerNombresCategorias();
    Categoria save(Categoria categoria);

}

