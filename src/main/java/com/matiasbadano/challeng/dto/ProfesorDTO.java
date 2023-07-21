package com.matiasbadano.challeng.dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfesorDTO {
    private Integer  id;
    private String nombre;
    private String email;
    private int categoriaId;
    private String nombreCategoria;
    private List<String> nombresCursos;
    private List<String> turnosCursos;

    public ProfesorDTO(Integer id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
}