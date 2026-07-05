package com.duoc.ms_reservas.feign;

import com.duoc.ms_reservas.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// agregado: comunicación con ms-clientes
@FeignClient(name = "ms-clientes")
public interface ClienteClient {

    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerClientePorId(@PathVariable Integer id);
}

