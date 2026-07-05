package com.duoc.ms_clientes.service;

import com.duoc.ms_clientes.dto.DireccionDTO;
import com.duoc.ms_clientes.dto.DireccionRequestDTO;
import com.duoc.ms_clientes.exception.ResourceNotFoundException;
import com.duoc.ms_clientes.mapper.DireccionMapper;
import com.duoc.ms_clientes.model.Cliente;
import com.duoc.ms_clientes.model.Direccion;
import com.duoc.ms_clientes.repository.ClienteRepository;
import com.duoc.ms_clientes.repository.DireccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final ClienteRepository clienteRepository;
    private final DireccionMapper direccionMapper;

    public List<DireccionDTO> findAll(){
        log.info("Listando todas las direcciones");

        return direccionRepository.findAll()
                .stream()
                .map(direccionMapper::toDTO)
                .toList();
    }

    public DireccionDTO findById(Integer id){
        log.info("Buscando direccion por id: {}", id);

        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direccion no encontrada con id: "+ id));

        return direccionMapper.toDTO(direccion);
    }

    public DireccionDTO save(DireccionRequestDTO request){
        log.info("Guardando nueva direccion para cliente id: {}", request.getClienteId());

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: "+request.getClienteId()));

        Direccion direccion = direccionMapper.toEntity(request, cliente);
        Direccion direccionGuardada = direccionRepository.save(direccion);

        return direccionMapper.toDTO(direccionGuardada);
    }

    public DireccionDTO update(Integer id, DireccionRequestDTO request){
        log.info("Actualizar direcion con id: {}", id);

        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direccion no encontrada con id: "+ id));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));

        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());
        direccion.setComuna(request.getComuna());
        direccion.setCiudad(request.getCiudad());
        direccion.setReferencia(request.getReferencia());
        direccion.setPrincipal(request.getPrincipal());
        direccion.setFechaRegistro(request.getFechaRegistro());
        direccion.setCliente(cliente);

        Direccion direccionActualizada = direccionRepository.save(direccion);

        return direccionMapper.toDTO(direccionActualizada);
    }

    public void delete(Integer id) {
        log.info("Eliminando direccion con id: {}", id);

        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Direccion no encontrada con id: " + id));

        direccionRepository.delete(direccion);
    }

    }

