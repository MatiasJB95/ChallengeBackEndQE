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

public class CategoriaDTO {
    private Long id;
    private String nombre;
    private List<String> nombresCursos;
}