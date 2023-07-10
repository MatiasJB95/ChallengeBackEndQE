package com.matiasbadano.challeng.dto;

import java.util.List;

public class ProfesorDTO {
    private Integer  id;
    private String nombre;
    private String email;
    private int categoriaId;
    private String nombreCategoria;
    private List<String> nombresCursos;
    private List<String> turnosCursos;

    public ProfesorDTO() {
    }

    public ProfesorDTO(Integer  id, String nombre, String email ) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;

    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<String> getNombresCursos() {
        return nombresCursos;
    }

    public void setNombresCursos(List<String> nombresCursos) {
        this.nombresCursos = nombresCursos;
    }

    public List<String> getTurnosCursos() {
        return turnosCursos;
    }

    public void setTurnosCursos(List<String> turnosCursos) {
        this.turnosCursos = turnosCursos;
    }

    public Integer  getId() {
        return id;
    }

    public void setId(Integer  id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}