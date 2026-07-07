package com.duoc.ms_reportes.feign;

import com.duoc.ms_reportes.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Cliente Feign para consultar reservas desde ms-reservas
 */
@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    /**
     * Lista todas las reservas disponibles en el microservicio de reservas
     */
    @GetMapping("/api/v1/reservas")
    List<ReservaDTO> findAll();
}