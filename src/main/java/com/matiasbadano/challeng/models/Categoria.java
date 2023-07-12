package com.matiasbadano.challeng.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(name = "nombre", length = 100)
    private String nombre;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Curso> cursos;

    @Column(name = "profesor_id", insertable = false, updatable = false)
    private Integer profesorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public Categoria() {

    }



    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Categoria(long id, String nombre, List<Curso> cursos) {
        this.id = id;
        this.nombre = nombre;
        this.cursos = cursos;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Long  getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public Integer getProfesorId() {
        return profesorId;
    }
    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}