    package com.matiasbadano.challeng.dto;
    import com.matiasbadano.challeng.models.Turno;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class CursoDTO {
        private int id;
        private String nombre;
        private Turno turno;
        private String contenido;
        private String nombreProfesor;
    }



