package com.matiasbadano.challeng.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoDTO {
    private int id;
    private String nombre;
    private String email;
    private String nacionalidad;
    private String paisResidencia;
    private Integer edad;
    private String telefono;
    private UsuarioDTO usuario;
    private List<String> cursos;
    private String turno;
}