CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reserva_id INT NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    codigo_transaccion VARCHAR(100) NOT NULL,
    pagado BOOLEAN NOT NULL,
    fecha_pago DATE NOT NULL,
    observacion VARCHAR(150)
);


