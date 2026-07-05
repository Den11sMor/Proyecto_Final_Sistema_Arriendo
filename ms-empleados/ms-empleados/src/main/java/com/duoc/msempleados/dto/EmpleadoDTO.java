package com.duoc.msempleados.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmpleadoDTO {


    private Integer id;

    private String rut;

    private String nombre;

    private String cargo;

    private String email;

    private BigDecimal sueldo;

    private Boolean activo = true;

    private LocalDate fechaIngreso;

}
