package com.matiasbadano.challeng.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "informacionadicional")
public class InformacionAdicional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @Column(name = "nacionalidad", length = 100)
    private String nacionalidad;

    @Column(name = "pais_residencia", length = 100)
    private String paisResidencia;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "telefono", length = 20)
    private String telefono;

}