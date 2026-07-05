package com.duoc.msempleados.service;

import com.duoc.msempleados.dto.EmpleadoDTO;
import com.duoc.msempleados.dto.EmpleadoRequestDTO;
import com.duoc.msempleados.exception.ResourceNotFoundException;
import com.duoc.msempleados.mapper.EmpleadoMapper;
import com.duoc.msempleados.model.Empleado;
import com.duoc.msempleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmpleadoService {

    private final EmpleadoMapper empleadoMapper;
    private final EmpleadoRepository empleadoRepository;

    public List<EmpleadoDTO> findAll() {
        try {
            log.info("Listando todos los empleados");

            return empleadoRepository.findAll()
                    .stream()
                    .map(empleadoMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar empleados", e);
            throw e;
        }
    }

    public EmpleadoDTO findById(Integer id){
        try {
            log.info("Buscando empleado por id: {}", id);

            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado por id: {}" + id));
            return empleadoMapper.toDTO(empleado);
        }catch (ResourceNotFoundException e){
            log.error("Empleado no econtrado por id: {}", id);
            throw  e;
        }catch (Exception e){
            log.error("Error al buscar empleado con id: {}", id, e);
            throw e;
        }
    }

    public EmpleadoDTO save(EmpleadoRequestDTO request){
        try {
            log.info("Guardando nuevo empleado");

            Empleado empleado = empleadoMapper.toEntity(request);
            Empleado empleadoGuardado = empleadoRepository.save(empleado);

            return empleadoMapper.toDTO(empleadoGuardado);
        }catch (Exception e){
            log.error("Error al guarda empleado", e);
            throw e;
        }
    }

    public EmpleadoDTO update(Integer id, EmpleadoRequestDTO request){
        try {
            log.info("Actualizando empleado por id: {}", id);

            Empleado empleado= empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("empleado no encontrado por id: {}" + id));

            empleadoMapper.updateEntity(empleado, request);

            Empleado empleadoActulizado = empleadoRepository.save(empleado);
            return empleadoMapper.toDTO(empleadoActulizado);
        }catch (ResourceNotFoundException e){
            log.error("Empleado no encontrado por id: {}", id);
            throw e;
        }catch (Exception e){
            log.error("Erro al actualizar empleado por id: {}", id, e);
            throw e;
        }
    }

    public void delete(Integer id){
        try {
            log.info("Eliminado empleado por id: {}", id);

            Empleado empleado = empleadoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: "+ id));

            empleadoRepository.delete(empleado);
        }catch (ResourceNotFoundException e){
            log.error("Empleado no encontrado con id: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error al eliminar empleado con id {}", id, e);
            throw e;
        }
    }

    public List<EmpleadoDTO> listarActivosPorAnio(Integer anio) {
        try {
            log.info("Listando empleados activos ingresados en el año: {}", anio);

            return empleadoRepository.listarEmpleadosActivosPorAnio(anio)
                    .stream()
                    .map(empleadoMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error al listar empleados activos por año: {}", anio, e);
            throw e;
        }
    }

}
