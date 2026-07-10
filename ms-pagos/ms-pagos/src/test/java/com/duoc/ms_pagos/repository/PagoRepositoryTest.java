package com.duoc.ms_pagos.repository;

import com.duoc.ms_pagos.model.Pago;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PagoRepositoryTest {

    @Autowired
    private PagoRepository pagoRepository;

    @Test
    @DisplayName("Debe guardar y buscar pago por id")
    void debeGuardarYBuscarPagoPorId() {
        Pago pago = pagoRepository.save(crearPago(1, new BigDecimal("50000"), "TRX-TEST-001"));

        Optional<Pago> encontrado = pagoRepository.findById(pago.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("TRX-TEST-001", encontrado.get().getCodigoTransaccion());
    }

    @Test
    @DisplayName("Debe buscar pagos por rango de monto")
    void debeBuscarPagosPorRangoDeMonto() {
        pagoRepository.save(crearPago(1, new BigDecimal("40000"), "TRX-TEST-002"));
        pagoRepository.save(crearPago(2, new BigDecimal("80000"), "TRX-TEST-003"));
        pagoRepository.save(crearPago(3, new BigDecimal("150000"), "TRX-TEST-004"));

        List<Pago> resultado = pagoRepository.buscarPagosPorRangoMonto(
                new BigDecimal("50000"),
                new BigDecimal("100000")
        );

        assertEquals(1, resultado.size());
        assertEquals("TRX-TEST-003", resultado.get(0).getCodigoTransaccion());
    }

    @Test
    @DisplayName("Debe eliminar pago")
    void debeEliminarPago() {
        Pago pago = pagoRepository.save(crearPago(4, new BigDecimal("70000"), "TRX-TEST-005"));

        pagoRepository.deleteById(pago.getId());

        assertFalse(pagoRepository.findById(pago.getId()).isPresent());
    }

    private Pago crearPago(Integer reservaId, BigDecimal monto, String codigoTransaccion) {
        Pago pago = new Pago();
        pago.setReservaId(reservaId);
        pago.setMetodoPago("Tarjeta de credito");
        pago.setMonto(monto);
        pago.setCodigoTransaccion(codigoTransaccion);
        pago.setPagado(true);
        pago.setFechaPago(LocalDate.now());
        pago.setObservacion("Pago de prueba");
        return pago;
    }
}
