package com.duoc.ms_clientes.mapper;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente){
        if(cliente == null) return  null;
        return new ClienteDTO(
                cliente.getId(),
                cliente.getRut(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getActivo(),
                cliente.getFechaRegistro()
        );
    }

    public Cliente toEntity(ClienteRequestDTO request){
        if (request == null){
            return  null;
        }

        Cliente cliente = new Cliente();
        cliente.setRut(request.getRut());
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setActivo(request.getActivo());
        cliente.setFechaRegistro(request.getFechaRegistro());

        return cliente;
    }


//
}
