package com.duoc.ms_clientes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String calle;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 100)
    private String comuna;

    @Column(nullable = false, length = 150)
    private String ciudad;

    @Column(nullable = false, length = 150)
    private String referencia;

    @Column(nullable = false)
    private Boolean principal;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
