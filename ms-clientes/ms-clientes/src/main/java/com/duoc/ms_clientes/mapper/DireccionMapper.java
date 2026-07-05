package com.duoc.ms_clientes.mapper;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.model.Direccion;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    public Direccion toEntity(DireccionRequestDTO request, Cliente cliente){
        if (request == null){
            return null;
        }

        Direccion direccion = new Direccion();
        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());
        direccion.setComuna(request.getComuna());
        direccion.setCiudad(request.getCiudad());
        direccion.setReferencia(request.getReferencia());
        direccion.setPrincipal(request.getPrincipal());
        direccion.setFechaRegistro(request.getFechaRegistro());
        direccion.setCliente(cliente);

        return direccion;
    }

    public DireccionDTO toDTO(Direccion direccion){
        if(direccion == null){
            return null;
        }

        return new DireccionDTO(
                direccion.getId(),
                direccion.getCalle(),
                direccion.getNumero(),
                direccion.getComuna(),
                direccion.getCiudad(),
                direccion.getReferencia(),
                direccion.getPrincipal(),
                direccion.getFechaRegistro(),
                direccion.getCliente().getId()
        );
    }
}
