package com.duoc.ms_pagos.mapper;

import com.duoc.ms_pagos.dto.PagoDTO;
import com.duoc.ms_pagos.dto.PagoRequestDTO;
import com.duoc.ms_pagos.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoDTO toDTO(Pago pago) {
        if (pago == null) {
            return null;
        }

        return new PagoDTO(
                pago.getId(),
                pago.getReservaId(),
                pago.getMetodoPago(),
                pago.getMonto(),
                pago.getCodigoTransaccion(),
                pago.isPagado(),
                pago.getFechaPago(),
                pago.getObservacion()
        );
    }

    public Pago toEntity(PagoRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        Pago pago = new Pago();
        pago.setReservaId(requestDTO.getReservaId());
        pago.setMetodoPago(requestDTO.getMetodoPago());
        pago.setMonto(requestDTO.getMonto());
        pago.setCodigoTransaccion(requestDTO.getCodigoTransaccion());
        pago.setPagado(requestDTO.isPagado());
        pago.setFechaPago(requestDTO.getFechaPago());
        pago.setObservacion(requestDTO.getObservacion());

        return pago;
    }

    // agregado: actualizar campo por campo para usarlo en PUT
    public void updateEntity(Pago pago, PagoRequestDTO requestDTO) {
        pago.setReservaId(requestDTO.getReservaId());
        pago.setMetodoPago(requestDTO.getMetodoPago());
        pago.setMonto(requestDTO.getMonto());
        pago.setCodigoTransaccion(requestDTO.getCodigoTransaccion());
        pago.setPagado(requestDTO.isPagado());
        pago.setFechaPago(requestDTO.getFechaPago());
        pago.setObservacion(requestDTO.getObservacion());
    }
}