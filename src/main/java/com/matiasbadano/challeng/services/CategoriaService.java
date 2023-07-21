package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.dto.CategoriaDTO;
import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.repository.CategoriaRepository;
import com.matiasbadano.challeng.repository.CursoRepository;
import com.matiasbadano.challeng.repository.ProfesorRepository;
import com.matiasbadano.challeng.services.exception.CategoriaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoría con ID " + id + " no existe."));

        return convertToDTO(categoria);
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());


        return categoriaDTO;
    }

    public Categoria obtenerCategoriaEntityPorId(Long id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria.isPresent()) {
            return optionalCategoria.get();
        } else {
            throw new CategoriaNotFoundException("La categoría con ID " + id + " no existe.");
        }
    }
    public void eliminarCategoriaPorId(Long id) {
        categoriaRepository.deleteById(id);

    }

    public void actualizarCategoria(Long id, String nombre) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("La categoría con ID " + id + " no existe."));
        categoria.setNombre(nombre);
        categoriaRepository.save(categoria);
    }
}
