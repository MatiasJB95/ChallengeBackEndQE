package com.matiasbadano.challeng.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
    @Table(name = "alumno")
    public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private InformacionAdicional informacionAdicional;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.LAZY)
    private List<Inscripcion> inscripciones;


    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public InformacionAdicional getInformacionAdicional() {
        return informacionAdicional;
    }


    public void setInformacionAdicional(InformacionAdicional informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

    }


}







