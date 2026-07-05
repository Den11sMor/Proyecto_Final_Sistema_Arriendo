-- agregado: creación de tabla estado_reserva
CREATE TABLE estado_reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    prioridad INT NOT NULL,
    activo BOOLEAN NOT NULL,
    fecha_creacion DATETIME NOT NULL
);

-- agregado: creación de tabla reserva
CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    vehiculo_id INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    cantidad_dias INT NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    observacion VARCHAR(150) NOT NULL,
    activa BOOLEAN NOT NULL,
    estado_reserva_id INT NOT NULL,
    CONSTRAINT fk_reserva_estado
        FOREIGN KEY (estado_reserva_id)
        REFERENCES estado_reserva(id)
);