package com.duoc.ms_reportes.mapper;

import com.duoc.ms_reportes.dto.PagoDTO;
import com.duoc.ms_reportes.dto.ReporteDTO;
import com.duoc.ms_reportes.dto.ReporteRequestDTO;
import com.duoc.ms_reportes.dto.ReservaDTO;
import com.duoc.ms_reportes.model.Reporte;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReporteMapper {

    public ReporteDTO toDTO(Reporte reporte) {
        if (reporte == null) {
            return null;
        }

        return new ReporteDTO(
                reporte.getId(),
                reporte.getReservaId(),
                reporte.getPagoId(),
                reporte.getTipoReporte(),
                reporte.getFechaGeneracion(),
                reporte.getDescripcion(),
                reporte.getTotalReserva(),
                reporte.getMontoPagado(),
                reporte.isReservaActiva(),
                reporte.isPagoConfirmado()
        );
    }

    public Reporte toEntity(ReporteRequestDTO requestDTO, ReservaDTO reservaDTO, PagoDTO pagoDTO) {
        if (requestDTO == null) {
            return null;
        }

        Reporte reporte = new Reporte();
        updateEntity(reporte, requestDTO, reservaDTO, pagoDTO);
        reporte.setFechaGeneracion(LocalDate.now());

        return reporte;
    }

    public void updateEntity(
            Reporte reporte,
            ReporteRequestDTO requestDTO,
            ReservaDTO reservaDTO,
            PagoDTO pagoDTO
    ) {
        reporte.setReservaId(requestDTO.getReservaId());
        reporte.setPagoId(requestDTO.getPagoId());
        reporte.setTipoReporte(requestDTO.getTipoReporte());
        reporte.setDescripcion(requestDTO.getDescripcion());
        reporte.setTotalReserva(reservaDTO.getMontoTotal());
        reporte.setMontoPagado(pagoDTO.getMonto());
        reporte.setReservaActiva(reservaDTO.isActiva());
        reporte.setPagoConfirmado(pagoDTO.isPagado());
    }
}
