INSERT INTO estado_reserva (
    nombre,
    descripcion,
    prioridad,
    activo,
    fecha_creacion
) VALUES
      ('Pendiente', 'Reserva creada pero aun no confirmada', 1, true, NOW()),
      ('Confirmada', 'Reserva aprobada y confirmada para el cliente', 2, true, NOW()),
      ('Cancelada', 'Reserva cancelada por el cliente o la empresa', 3, true, NOW()),
      ('En curso', 'Reserva activa durante el periodo de arriendo', 4, true, NOW()),
      ('Finalizada', 'Reserva cerrada despues de la devolucion', 5, true, NOW());

INSERT INTO reserva (
    cliente_id,
    vehiculo_id,
    fecha_inicio,
    fecha_fin,
    cantidad_dias,
    monto_total,
    observacion,
    activa,
    estado_reserva_id
) VALUES
      (1, 1, '2026-06-10', '2026-06-12', 3, 90000.00, 'Reserva para viaje familiar', true, 1),
      (2, 2, '2026-06-15', '2026-06-16', 2, 70000.00, 'Cliente solicita retiro en sucursal', true, 2),
      (3, 3, '2026-07-01', '2026-07-05', 5, 180000.00, 'Reserva de vehiculo automatico', false, 3),
      (4, 4, '2026-07-10', '2026-07-12', 3, 98000.00, 'Reserva para traslado de carga', true, 4),
      (5, 5, '2026-07-15', '2026-07-17', 3, 64000.00, 'Reserva para grupo familiar', true, 5);
