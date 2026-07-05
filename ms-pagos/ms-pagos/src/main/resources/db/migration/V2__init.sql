INSERT INTO pagos (
    reserva_id,
    metodo_pago,
    monto,
    codigo_transaccion,
    pagado,
    fecha_pago,
    observacion
) VALUES
      (1, 'Tarjeta de crédito', 75000.00, 'TRX-001', true, '2025-05-01', 'Pago completo de reserva'),
      (2, 'Transferencia bancaria', 120000.00, 'TRX-002', true, '2025-05-03', 'Pago confirmado'),
      (3, 'Débito', 55000.00, 'TRX-003', true, '2025-05-05', 'Pago realizado correctamente');