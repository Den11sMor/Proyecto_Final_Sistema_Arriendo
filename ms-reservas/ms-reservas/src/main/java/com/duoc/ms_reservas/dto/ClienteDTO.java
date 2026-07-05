package com.duoc.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private Integer telefono;
    private Boolean activo;
    private LocalDate fechaRegistro;

}
