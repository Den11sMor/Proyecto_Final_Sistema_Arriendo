package com.duoc.ms_reportes.repository;

import com.duoc.ms_reportes.model.Reporte;
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
class ReporteRepositoryTest {

    @Autowired
    private ReporteRepository reporteRepository;

    @Test
    @DisplayName("Debe guardar y buscar reporte por id")
    void debeGuardarYBuscarReportePorId() {
        Reporte reporte = reporteRepository.save(crearReporte(10, 5, true));

        Optional<Reporte> encontrado = reporteRepository.findById(reporte.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(10, encontrado.get().getReservaId());
        assertEquals(5, encontrado.get().getPagoId());
    }

    @Test
    @DisplayName("Debe buscar reportes por reserva")
    void debeBuscarReportesPorReserva() {
        reporteRepository.save(crearReporte(10, 5, true));
        reporteRepository.save(crearReporte(10, 6, true));
        reporteRepository.save(crearReporte(11, 7, false));

        List<Reporte> resultado = reporteRepository.findByReservaId(10);

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("Debe buscar reportes por pago confirmado")
    void debeBuscarReportesPorPagoConfirmado() {
        reporteRepository.save(crearReporte(10, 5, true));
        reporteRepository.save(crearReporte(11, 6, false));

        List<Reporte> resultado = reporteRepository.buscarPorPagoConfirmado(true);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isPagoConfirmado());
    }

    @Test
    @DisplayName("Debe eliminar reporte")
    void debeEliminarReporte() {
        Reporte reporte = reporteRepository.save(crearReporte(12, 8, true));

        reporteRepository.deleteById(reporte.getId());

        assertFalse(reporteRepository.findById(reporte.getId()).isPresent());
    }

    private Reporte crearReporte(Integer reservaId, Integer pagoId, boolean pagoConfirmado) {
        Reporte reporte = new Reporte();
        reporte.setReservaId(reservaId);
        reporte.setPagoId(pagoId);
        reporte.setTipoReporte("RESUMEN_RESERVA");
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setDescripcion("Reporte de prueba");
        reporte.setTotalReserva(new BigDecimal("125000"));
        reporte.setMontoPagado(new BigDecimal("125000"));
        reporte.setReservaActiva(true);
        reporte.setPagoConfirmado(pagoConfirmado);
        return reporte;
    }
}
