package com.duoc.ms_reservas.feign;

import com.duoc.ms_reservas.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para consultar informacion del microservicio ms-clientes.
 */
@FeignClient(name = "ms-clientes")
public interface ClienteClient {

    /**
     * Busca un cliente por su identificador.
     *
     * @param id identificador del cliente
     * @return datos del cliente encontrado
     */
    @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO findById(@PathVariable("id") Integer id);
}

