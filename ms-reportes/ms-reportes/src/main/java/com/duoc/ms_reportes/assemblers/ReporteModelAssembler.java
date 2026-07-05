package com.duoc.ms_reportes.assemblers;

import com.duoc.ms_reportes.controller.ReporteControllerV2;
import com.duoc.ms_reportes.dto.ReporteDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<ReporteDTO, EntityModel<ReporteDTO>> {

    @Override
    public EntityModel<ReporteDTO> toModel(ReporteDTO reporte) {
        return EntityModel.of(reporte,
                linkTo(methodOn(ReporteControllerV2.class).findById(reporte.getId())).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withRel("reportes"),
                linkTo(methodOn(ReporteControllerV2.class).findByReservaId(reporte.getReservaId())).withRel("reportes-reserva"),
                linkTo(methodOn(ReporteControllerV2.class).findByPagoConfirmado(reporte.isPagoConfirmado())).withRel("reportes-pago-confirmado"));
    }
}