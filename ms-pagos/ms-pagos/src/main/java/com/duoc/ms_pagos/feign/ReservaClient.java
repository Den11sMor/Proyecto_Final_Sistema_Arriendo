package com.duoc.ms_pagos.feign;

import com.duoc.ms_pagos.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para consultar reservas desde ms-reservas.
 */
@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    /**
     * Busca una reserva por id en el microservicio de reservas.
     *
     * @param id identificador de la reserva
     * @return datos de la reserva encontrada
     */
    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO findById(@PathVariable("id") Integer id);
}