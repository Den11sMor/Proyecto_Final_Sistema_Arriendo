package com.duoc.ms_pagos.feign;

import com.duoc.ms_pagos.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas", url = "http://localhost:8083")
public interface ReservaClient {

    //  GET /api/v1/reservas/{id} de ms-reservas
    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO findById(@PathVariable("id") Integer id);
}
