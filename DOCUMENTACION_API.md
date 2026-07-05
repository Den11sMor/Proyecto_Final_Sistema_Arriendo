# Documentación de APIs

Los microservicios exponen documentación interactiva Swagger/OpenAPI mientras están en ejecución.

| Servicio | URL Swagger | Acceso por Gateway |
| --- | --- | --- |
| Clientes | `http://localhost:8081/swagger-ui/index.html` | `http://localhost:8080/ms-clientes/api/v1/clientes` |
| Vehículos | `http://localhost:8082/swagger-ui/index.html` | `http://localhost:8080/ms-vehiculos/api/v1/vehiculos` |
| Reservas | `http://localhost:8083/swagger-ui/index.html` | `http://localhost:8080/ms-reservas/api/v1/reservas` |

## Orden de ejecución

1. Eureka Server (`8761`)
2. Clientes (`8081`)
3. Vehículos (`8082`)
4. Reservas (`8083`)
5. API Gateway (`8080`)

Eureka queda disponible en `http://localhost:8761` y debe mostrar los cuatro servicios registrados.
