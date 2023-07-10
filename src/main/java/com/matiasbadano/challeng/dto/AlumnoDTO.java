package com.matiasbadano.challeng.dto;

import java.util.List;

public class AlumnoDTO {
    private int id;
    private String nombre;
    private String email;
    private String nacionalidad;
    private String paisResidencia;
    private int edad;
    private String telefono;
    private List<String> cursos;
    private String turno;

    public AlumnoDTO() {

    }

    public AlumnoDTO(int id,String nombre, String email,  String nacionalidad, String paisResidencia, int edad, String telefono, List<String> cursos, String turno  ) {
       this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.nacionalidad = nacionalidad;
        this.paisResidencia = paisResidencia;
        this.edad = edad;
        this.telefono = telefono;
        this.cursos = cursos;
        this.turno = turno;
    }

    public List<String> getCursos() {
        return cursos;
    }

    public int getId() {
        return id;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setCursos(List<String> cursos) {
        this.cursos = cursos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }


    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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


    public void setId(int id) {
        this.id = id;
    }
}
