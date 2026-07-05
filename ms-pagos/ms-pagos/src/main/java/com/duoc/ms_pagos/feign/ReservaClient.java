package com.duoc.ms_pagos.feign;

import com.duoc.ms_pagos.dto.ReservaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO findById(@PathVariable("id") Integer id);
}
