package com.matiasbadano.challeng.dto;

import java.util.List;


public class CategoriaDTO {
    private Long id;
    private String nombre;
    private List<String> nombresCursos;



    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nombre,  List<String> nombresCursos) {
        this.id = id;
        this.nombre = nombre;
        this.nombresCursos = nombresCursos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getNombresCursos() {
        return nombresCursos;
    }

    public void setNombresCursos(List<String> nombresCursos) {
        this.nombresCursos = nombresCursos;
    }
}