package com.matiasbadano.challeng.services;

import com.matiasbadano.challeng.models.Categoria;
import com.matiasbadano.challeng.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;


    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }

}