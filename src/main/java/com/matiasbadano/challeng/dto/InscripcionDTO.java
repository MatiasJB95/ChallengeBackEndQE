package com.matiasbadano.challeng.dto;
public class InscripcionDTO {
    private Long id;
    private Long alumnoId;
    private Long cursoId;

    // Constructor, getters y setters

    public InscripcionDTO() {
    }

    public InscripcionDTO(Long id, Long alumnoId, Long cursoId) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.cursoId = cursoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}