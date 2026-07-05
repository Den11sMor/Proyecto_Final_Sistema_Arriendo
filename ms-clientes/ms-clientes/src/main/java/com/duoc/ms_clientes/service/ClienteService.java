package com.duoc.ms_clientes.service;

import com.duoc.ms_clientes.dto.ClienteDTO;
import com.duoc.ms_clientes.dto.ClienteRequestDTO;
import com.duoc.ms_clientes.exception.ResourceNotFoundException;
import com.duoc.ms_clientes.mapper.ClienteMapper;
import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public List<ClienteDTO> findAll(){
        log.info("Listando todos los clientes");

        try {
            return clienteRepository.findAll()
                    .stream()
                    .map(clienteMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al listar clientes", e);
            throw e;
        }
    }

    public ClienteDTO findById(Integer id){
        log.info("Buscando cliente por id: {}", id);

        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: "+ id));
            return clienteMapper.toDTO(cliente);
        } catch (Exception e) {
            log.error("Error al buscar cliente con id: {}", id, e);
            throw e;
        }
    }

    public ClienteDTO save(ClienteRequestDTO request){
        log.info("Guardando nuevo cliente");

        try {
            Cliente cliente = clienteMapper.toEntity(request);
            Cliente clienteGuardado = clienteRepository.save(cliente);

            return clienteMapper.toDTO(clienteGuardado);
        } catch (Exception e) {
            log.error("Error al guardar cliente", e);
            throw e;
        }

    }

    public ClienteDTO update(Integer id, ClienteRequestDTO request){
        log.info("Actualizando cliente con id: {}", id);

        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: "+ id));

            cliente.setRut(request.getRut());
            cliente.setNombre(request.getNombre());
            cliente.setApellido(request.getApellido());
            cliente.setEmail(request.getEmail());
            cliente.setTelefono(request.getTelefono());
            cliente.setActivo(request.getActivo());
            cliente.setFechaRegistro(request.getFechaRegistro());

            Cliente clienteActualizado = clienteRepository.save(cliente);

            return clienteMapper.toDTO(clienteActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar cliente con id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id){
        log.info("Eliminando cliente por id: {}", id);

        try {
            Cliente cliente = clienteRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: "+ id));

            clienteRepository.delete(cliente);
        } catch (Exception e) {
            log.error("Error al eliminar cliente con id: {}", id, e);
            throw e;
        }
    }

    public List<ClienteDTO> buscarPorEmail(String texto){
        log.info("Buscando clientes cuyo email contenga: {}", texto);

        try {
            return  clienteRepository.findByEmailContainingIgnoreCase(texto)
                    .stream()
                    .map(clienteMapper::toDTO)
                    .toList();
        } catch (Exception e) {
            log.error("Error al buscar clientes por email: {}", texto, e);
            throw e;
        }
    }

}
