package com.duoc.ms_reportes.assemblers;

import com.duoc.ms_reportes.dto.ReporteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Construye la representacion HATEOAS para las respuestas de reportes
 */
@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<ReporteDTO, EntityModel<ReporteDTO>> {

    @Override
    @NonNull
    public EntityModel<ReporteDTO> toModel(@NonNull ReporteDTO reporte) {
        return EntityModel.of(reporte,
                Link.of("/api/v2/reportes/" + reporte.getId()).withSelfRel(),
                Link.of("/api/v2/reportes").withRel("reportes"),
                Link.of("/api/v2/reportes/reserva/" + reporte.getReservaId()).withRel("reportes-reserva"),
                Link.of("/api/v2/reportes/pago-confirmado?confirmado=" + reporte.isPagoConfirmado()).withRel("reportes-pago-confirmado"));
    }
}
