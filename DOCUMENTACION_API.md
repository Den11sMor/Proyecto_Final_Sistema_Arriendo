# Documentacion de APIs

Los microservicios exponen Swagger/OpenAPI directo por su puerto. Las rutas de
negocio tambien se prueban por API Gateway usando el prefijo del servicio.

## Infraestructura

Eureka:

```text
http://localhost:8761
```

Gateway:

```text
http://localhost:8080
```

La raiz del gateway puede responder 404 porque no existe una ruta `/`.

## Swagger y OpenAPI

Usar Swagger directo por microservicio:

```text
http://localhost:8081/doc/swagger-ui.html
http://localhost:8082/doc/swagger-ui.html
http://localhost:8083/doc/swagger-ui.html
http://localhost:8084/doc/swagger-ui.html
http://localhost:8085/doc/swagger-ui.html
http://localhost:8086/doc/swagger-ui.html
http://localhost:8087/doc/swagger-ui.html
```

OpenAPI JSON directo:

```text
http://localhost:8081/v3/api-docs
http://localhost:8082/v3/api-docs
http://localhost:8083/v3/api-docs
http://localhost:8084/v3/api-docs
http://localhost:8085/v3/api-docs
http://localhost:8086/v3/api-docs
http://localhost:8087/v3/api-docs
```

Nota: Swagger UI por gateway puede fallar por redirecciones internas. Para
Postman se deben usar las rutas `/api/...` por gateway.

## Rutas probadas por Gateway

### Clientes

Listar clientes:

```text
http://localhost:8080/ms-clientes/api/v1/clientes
http://localhost:8080/ms-clientes/api/v2/clientes
```

Buscar cliente y probar no encontrado:

```text
http://localhost:8080/ms-clientes/api/v1/clientes/1
http://localhost:8080/ms-clientes/api/v1/clientes/999999
http://localhost:8080/ms-clientes/api/v2/clientes/1
http://localhost:8080/ms-clientes/api/v2/clientes/999999
```

Buscar clientes por email:

```text
http://localhost:8080/ms-clientes/api/v1/clientes/buscar-email?texto=gmail
http://localhost:8080/ms-clientes/api/v2/clientes/buscar-email?texto=gmail
```

### Vehiculos

Listar vehiculos:

```text
http://localhost:8080/ms-vehiculos/api/v1/vehiculos
http://localhost:8080/ms-vehiculos/api/v2/vehiculos
```

Buscar vehiculo y probar no encontrado:

```text
http://localhost:8080/ms-vehiculos/api/v1/vehiculos/1
http://localhost:8080/ms-vehiculos/api/v1/vehiculos/999999
http://localhost:8080/ms-vehiculos/api/v2/vehiculos/1
http://localhost:8080/ms-vehiculos/api/v2/vehiculos/999999
```

Buscar vehiculos disponibles por precio:

```text
http://localhost:8080/ms-vehiculos/api/v1/vehiculos/disponibles/precio-menor/50000
http://localhost:8080/ms-vehiculos/api/v2/vehiculos/disponibles/precio-menor/50000
```

Listar categorias:

```text
http://localhost:8080/ms-vehiculos/api/v1/categorias
http://localhost:8080/ms-vehiculos/api/v2/categorias
```

Buscar categoria y probar no encontrada:

```text
http://localhost:8080/ms-vehiculos/api/v1/categorias/1
http://localhost:8080/ms-vehiculos/api/v1/categorias/999999
http://localhost:8080/ms-vehiculos/api/v2/categorias/1
http://localhost:8080/ms-vehiculos/api/v2/categorias/999999
```

### Reservas

Listar reservas:

```text
http://localhost:8080/ms-reservas/api/v1/reservas
http://localhost:8080/ms-reservas/api/v2/reservas
```

Buscar reserva y probar no encontrada:

```text
http://localhost:8080/ms-reservas/api/v1/reservas/1
http://localhost:8080/ms-reservas/api/v1/reservas/999999
http://localhost:8080/ms-reservas/api/v2/reservas/1
http://localhost:8080/ms-reservas/api/v2/reservas/999999
```

Buscar reservas desde fecha:

```text
http://localhost:8080/ms-reservas/api/v1/reservas/desde-fecha?fecha=2026-07-10
http://localhost:8080/ms-reservas/api/v2/reservas/desde-fecha?fecha=2026-07-10
```

Listar estados de reserva:

```text
http://localhost:8080/ms-reservas/api/v1/estados-reserva
http://localhost:8080/ms-reservas/api/v2/estados-reserva
```

Buscar estado de reserva y probar no encontrado:

```text
http://localhost:8080/ms-reservas/api/v1/estados-reserva/1
http://localhost:8080/ms-reservas/api/v1/estados-reserva/999999
http://localhost:8080/ms-reservas/api/v2/estados-reserva/1
http://localhost:8080/ms-reservas/api/v2/estados-reserva/999999
```

### Pagos

Listar pagos:

```text
http://localhost:8080/ms-pagos/api/v1/pagos
http://localhost:8080/ms-pagos/api/v2/pagos
```

Buscar pago y probar no encontrado:

```text
http://localhost:8080/ms-pagos/api/v1/pagos/1
http://localhost:8080/ms-pagos/api/v1/pagos/999999
http://localhost:8080/ms-pagos/api/v2/pagos/1
http://localhost:8080/ms-pagos/api/v2/pagos/999999
```

Buscar pagos por rango:

```text
http://localhost:8080/ms-pagos/api/v1/pagos/rango?min=1000&max=100000
http://localhost:8080/ms-pagos/api/v2/pagos/rango?min=1000&max=100000
```

### Sucursales

Listar sucursales:

```text
http://localhost:8080/ms-sucursales/api/v1/sucursales
http://localhost:8080/ms-sucursales/api/v2/sucursales
```

Buscar sucursal y probar no encontrada:

```text
http://localhost:8080/ms-sucursales/api/v1/sucursales/1
http://localhost:8080/ms-sucursales/api/v1/sucursales/999999
http://localhost:8080/ms-sucursales/api/v2/sucursales/1
http://localhost:8080/ms-sucursales/api/v2/sucursales/999999
```

Listar sucursales operativas:

```text
http://localhost:8080/ms-sucursales/api/v1/sucursales/operativas
http://localhost:8080/ms-sucursales/api/v2/sucursales/operativas
```

Listar regiones:

```text
http://localhost:8080/ms-sucursales/api/v1/regiones
http://localhost:8080/ms-sucursales/api/v2/regiones
```

Buscar region y probar no encontrada:

```text
http://localhost:8080/ms-sucursales/api/v1/regiones/1
http://localhost:8080/ms-sucursales/api/v1/regiones/999999
http://localhost:8080/ms-sucursales/api/v2/regiones/1
http://localhost:8080/ms-sucursales/api/v2/regiones/999999
```

### Empleados

Listar empleados:

```text
http://localhost:8080/ms-empleados/api/v1/empleados
http://localhost:8080/ms-empleados/api/v2/empleados
```

Buscar empleado y probar no encontrado:

```text
http://localhost:8080/ms-empleados/api/v1/empleados/1
http://localhost:8080/ms-empleados/api/v1/empleados/999999
http://localhost:8080/ms-empleados/api/v2/empleados/1
http://localhost:8080/ms-empleados/api/v2/empleados/999999
```

Buscar empleados activos por anio:

```text
http://localhost:8080/ms-empleados/api/v1/activos/anio/2024
http://localhost:8080/ms-empleados/api/v2/activos/anio/2024
```

### Reportes

Listar reportes:

```text
http://localhost:8080/ms-reportes/api/v1/reportes
http://localhost:8080/ms-reportes/api/v2/reportes
```

Buscar reporte y probar no encontrado:

```text
http://localhost:8080/ms-reportes/api/v1/reportes/1
http://localhost:8080/ms-reportes/api/v1/reportes/999999
http://localhost:8080/ms-reportes/api/v2/reportes/1
http://localhost:8080/ms-reportes/api/v2/reportes/999999
```

Buscar reportes por reserva:

```text
http://localhost:8080/ms-reportes/api/v1/reportes/reserva/1
http://localhost:8080/ms-reportes/api/v2/reportes/reserva/1
```

Buscar reportes con pago confirmado:

```text
http://localhost:8080/ms-reportes/api/v1/reportes/pago-confirmado
http://localhost:8080/ms-reportes/api/v2/reportes/pago-confirmado
```

## Rutas directas base por servicio

Para probar directo, usar el mismo path `/api/...` con el puerto del
microservicio:

```text
ms-clientes:   http://localhost:8081
ms-vehiculos:  http://localhost:8082
ms-reservas:   http://localhost:8083
ms-pagos:      http://localhost:8084
ms-sucursales: http://localhost:8085
ms-empleados:  http://localhost:8086
ms-reportes:   http://localhost:8087
```
