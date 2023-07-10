    package com.matiasbadano.challeng.dto;

    import com.matiasbadano.challeng.models.Contenido;
    import com.matiasbadano.challeng.models.Turno;

    public class CursoDTO {
        private int id;
        private String nombre;
        private String turno;
        private String contenido;
        private String nombreProfesor;

        public CursoDTO() {

        }

        public CursoDTO(int id,String nombre, Turno turno, Contenido contenido, String nombreProfesor) {
           this.id = id;
            this.nombre = nombre;
            this.turno = turno.name();
            this.contenido = contenido.getTitulo();
            this.nombreProfesor = nombreProfesor;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTurno() {
            return turno;
        }

        public void setTurno(String turno) {
            this.turno = turno;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public String getNombreProfesor() {
            return nombreProfesor;
        }

        public void setNombreProfesor(String nombreProfesor) {
            this.nombreProfesor = nombreProfesor;
        }
    }



