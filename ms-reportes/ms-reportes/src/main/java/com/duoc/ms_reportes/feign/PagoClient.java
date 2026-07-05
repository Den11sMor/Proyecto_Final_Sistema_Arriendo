package com.duoc.ms_reportes.feign;

import com.duoc.ms_reportes.dto.PagoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-pagos", url = "http://localhost:8084")
public interface PagoClient {

    @GetMapping("/api/v1/pagos")
    List<PagoDTO> findAll();
}
