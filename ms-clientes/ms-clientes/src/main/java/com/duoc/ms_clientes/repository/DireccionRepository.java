package com.duoc.ms_clientes.repository;


import com.duoc.ms_clientes.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DireccionRepository  extends JpaRepository<Direccion, Integer> {
//
}
