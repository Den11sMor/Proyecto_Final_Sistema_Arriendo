package com.duoc.ms_clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa la entidad direccion almacenada en la base de datos
 */
@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una direccion de cliente")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la direccion", example = "1")
    private Integer id;

    @Column(nullable = false, length = 100)
    @Schema(description = "Calle de la direccion", example = "Av Siempre Viva")
    private String calle;

    @Column(nullable = false)
    @Schema(description = "Numero de la direccion", example = "742")
    private Integer numero;

    @Column(nullable = false, length = 100)
    @Schema(description = "Comuna de la direccion", example = "Santiago")
    private String comuna;

    @Column(nullable = false, length = 150)
    @Schema(description = "Ciudad de la direccion", example = "Santiago")
    private String ciudad;

    @Column(nullable = false, length = 150)
    @Schema(description = "Referencia de la direccion", example = "Casa azul")
    private String referencia;

    @Column(nullable = false)
    @Schema(description = "Indica si es la direccion principal", example = "true")
    private Boolean principal;

    @Column(nullable = false)
    @Schema(description = "Fecha de registro de la direccion", example = "2024-06-01")
    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @Schema(description = "Cliente asociado a la direccion")
    private Cliente cliente;
}