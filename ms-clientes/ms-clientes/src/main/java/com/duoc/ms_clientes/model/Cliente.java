package com.duoc.ms_clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Representa la entidad cliente almacenada en la base de datos
 */
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un cliente del sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del cliente", example = "1")
    private Integer id;

    @Column(nullable = false, length = 12, unique = true)
    @Schema(description = "Rut del cliente", example = "12345678-9")
    private String rut;

    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Column(nullable = false, length = 100)
    @Schema(description = "Apellido del cliente", example = "Perez")
    private String apellido;

    @Column(nullable = false, length = 100, unique = true)
    @Schema(description = "Email del cliente", example = "juan.perez@gmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Telefono del cliente", example = "987654321")
    private Integer telefono;

    @Column(nullable = false)
    @Schema(description = "Indica si el cliente esta activo", example = "true")
    private Boolean activo = true;

    @Column(nullable = false)
    @Schema(description = "Fecha de registro del cliente", example = "2024-06-01")
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Direcciones asociadas al cliente")
    private List<Direccion> direcciones;
}