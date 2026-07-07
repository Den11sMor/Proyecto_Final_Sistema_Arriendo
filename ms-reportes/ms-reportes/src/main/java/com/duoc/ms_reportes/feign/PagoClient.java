package com.duoc.ms_reportes.feign;

import com.duoc.ms_reportes.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Cliente Feign para consultar pagos desde ms-pagos
 */
@FeignClient(name = "ms-pagos")
public interface PagoClient {

    /**
     * Lista todos los pagos disponibles en el microservicio de pagos
     */
    @GetMapping("/api/v1/pagos")
    List<PagoDTO> findAll();
}