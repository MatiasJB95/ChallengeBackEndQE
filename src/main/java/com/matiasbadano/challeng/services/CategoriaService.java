package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.CategoriaDTO;
import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.repository.CategoriaRepository;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CursoRepository cursoRepository;
    private final ProfesorRepository profesorRepository;
    public CategoriaService(CategoriaRepository categoriaRepository, CursoRepository cursoRepository, ProfesorRepository profesorRepository
                          ) {
        this.categoriaRepository = categoriaRepository;
        this.cursoRepository = cursoRepository;
        this.profesorRepository = profesorRepository;



    }
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    public List<CategoriaDTO> obtenerNombresCategorias() {
        List<CategoriaDTO> categoriaDTOs = new ArrayList<>();

        List<Categoria> categorias = categoriaRepository.findAll();
        for (Categoria categoria : categorias) {
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId(categoria.getId());
            categoriaDTO.setNombre(categoria.getNombre());

            List<String> nombresCursos = cursoRepository.findNombresCursosByCategoriaId(categoria.getId());
            categoriaDTO.setNombresCursos(nombresCursos);

            categoriaDTOs.add(categoriaDTO);
        }

        return categoriaDTOs;
    }
}
