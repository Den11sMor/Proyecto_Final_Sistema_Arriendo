package com.duoc.ms_reservas.feign;

import com.duoc.ms_reservas.dto.VehiculoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para consultar informacion del microservicio ms-vehiculos.
 */
@FeignClient(name = "ms-vehiculos")
public interface VehiculoClient {

    /**
     * Busca un vehiculo por su identificador.
     *
     * @param id identificador del vehiculo
     * @return datos del vehiculo encontrado
     */
    @GetMapping("/api/v1/vehiculos/{id}")
    VehiculoDTO findById(@PathVariable("id") Integer id);
}
