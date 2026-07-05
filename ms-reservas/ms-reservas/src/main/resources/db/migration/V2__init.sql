-- agregado: datos iniciales para estado_reserva
INSERT INTO estado_reserva (
    nombre,
    descripcion,
    prioridad,
    activo,
    fecha_creacion)
    VALUES
        ('Pendiente', 'Reserva creada pero aún no confirmada', 1, true, NOW()),
        ('Confirmada', 'Reserva aprobada y confirmada para el cliente', 2, true, NOW()),
        ('Cancelada', 'Reserva cancelada por el cliente o la empresa', 3, true, NOW());

-- agregado: datos iniciales para reserva
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
        (3, 3, '2026-07-01', '2026-07-05', 5, 180000.00, 'Reserva de vehículo automático', false, 3);