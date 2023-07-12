    package com.matiasbadano.challeng.dto;

    import com.matiasbadano.challeng.models.Contenido;
    import com.matiasbadano.challeng.models.Turno;

    public class CursoDTO {
        private int id;
        private String nombre;
        private Turno turno;
        private String contenido;
        private String nombreProfesor;

        public CursoDTO() {

        }

        public CursoDTO(int id,String nombre, Turno turno, String nombreProfesor) {
           this.id = id;
            this.nombre = nombre;
            this.turno = Turno.valueOf(turno.name());
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

        public Turno getTurno() {
            return turno;
        }

        public void setTurno(Turno turno) {
            this.turno = turno;
        }

        public String getNombreProfesor() {
            return nombreProfesor;
        }

        public void setNombreProfesor(String nombreProfesor) {
            this.nombreProfesor = nombreProfesor;
        }
    }



