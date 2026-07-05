# Sistema de arriendo de vehículos

Aplicación basada en microservicios para administrar clientes, vehículos, sucursales, reservas, pagos, empleados y reportes de una empresa de arriendo de vehículos.

El proyecto está desarrollado con Java 17, Spring Boot, Spring Cloud, Spring Data JPA y MySQL. Los servicios se comunican mediante API REST y, cuando corresponde, con clientes OpenFeign.

## Arquitectura

```text
Cliente HTTP
    |
    v
API Gateway (8080) ----> Eureka Server (8761)
    |
    +--> ms-clientes (8081) <-----+
    +--> ms-vehiculos (8082) <----+-- ms-reservas (8083) <-- ms-pagos (8084)
    +--> ms-reservas (8083)        ^                         ^
                                      +------ ms-reportes -------+
```

El API Gateway centraliza el acceso a Clientes, Vehículos y Reservas. Las llamadas internas entre Reservas, Pagos y Reportes se realizan mediante OpenFeign.

## Servicios

| Componente | Puerto | Descripción | Endpoints principales |
|---|---:|---|---|
| `eureka-server` | 8761 | Servidor de descubrimiento de servicios. Mantiene el registro de los servicios que usan Eureka. | Panel: `http://localhost:8761` |
| `api-gateway` | 8080 | Punto de entrada único. Enruta las solicitudes de clientes, vehículos y reservas hacia los servicios registrados en Eureka. | `/api/v1/clientes/**`, `/api/v1/direcciones/**`, `/api/v1/vehiculos/**`, `/api/v1/categorias/**`, `/api/v1/reservas/**`, `/api/v1/estados-reserva/**` |
| `ms-clientes` | 8081 | Gestiona clientes y sus direcciones: creación, consulta, edición, eliminación y búsqueda de clientes por correo. Se registra en Eureka. | `/api/v1/clientes`, `/api/v1/direcciones` |
| `ms-vehiculos` | 8082 | Administra el catálogo de vehículos y sus categorías. Permite consultar vehículos disponibles bajo un precio máximo. Se registra en Eureka. | `/api/v1/vehiculos`, `/api/v1/categorias` |
| `ms-reservas` | 8083 | Servicio central del proceso de arriendo. Gestiona reservas y estados de reserva, validando el cliente en `ms-clientes` y el vehículo en `ms-vehiculos` mediante OpenFeign. | `/api/v1/reservas`, `/api/v1/estados-reserva` |
| `ms-pagos` | 8084 | Registra y administra pagos asociados a una reserva. Consulta la reserva correspondiente en `ms-reservas` mediante OpenFeign. | `/api/v1/pagos` |
| `ms-sucursales` | 8085 | Administra sucursales y regiones, incluyendo la consulta de sucursales operativas. | `/api/v1/sucursales`, `/api/v1/regiones` |
| `ms-empleados` | 8086 | Gestiona empleados y permite consultar empleados activos por año. | `/api/v1/empleados`, `/api/v1/activos/anio/{anio}` |
| `ms-reportes` | 8083* | Crea y administra reportes. Consulta reservas y pagos mediante OpenFeign, incluyendo reportes por reserva y de pagos confirmados. | `/api/v1/reportes` |

Todos los recursos principales implementan operaciones CRUD (`GET`, `POST`, `PUT` y `DELETE`) bajo el prefijo `/api/v1`.

## Comunicación entre servicios

- `ms-reservas` consulta clientes en `ms-clientes` y vehículos en `ms-vehiculos`.
- `ms-pagos` consulta reservas en `ms-reservas`.
- `ms-reportes` consulta reservas en `ms-reservas` y pagos en `ms-pagos`.
- `api-gateway`, `ms-clientes` y `ms-vehiculos` usan Eureka para descubrimiento de servicios.

## Requisitos

- Java 17.
- MySQL en ejecución.
- Maven Wrapper incluido en cada servicio (`mvnw.cmd` para Windows).

Las configuraciones actuales utilizan las bases de datos MySQL `prueba1`, `prueba2` y `prueba3`. Antes de iniciar, revisa usuario, contraseña y URL en cada archivo `src/main/resources/application*.properties`.

## Ejecución local

1. Inicia MySQL y crea las bases de datos requeridas si aún no existen.
2. Inicia primero el servidor Eureka:

   ```powershell
   cd eureka-server/eureka-server
   .\mvnw.cmd spring-boot:run
   ```

3. Inicia `ms-clientes`, `ms-vehiculos` y `ms-reservas`.
4. Inicia el API Gateway.
5. Inicia los demás servicios según la funcionalidad que necesites: sucursales, empleados, reservas, pagos y reportes.

Para iniciar cualquier microservicio, entra a su segunda carpeta (por ejemplo, `ms-reservas/ms-reservas`) y ejecuta:

```powershell
.\mvnw.cmd spring-boot:run
```

## Observaciones de configuración

- `ms-reportes` tiene configurado el puerto `8083`, que coincide con `ms-reservas`; ambos no pueden ejecutarse a la vez. Para usar Reportes, asígnale un puerto disponible, por ejemplo `8087`.
- El archivo de configuración de `ms-reportes` declara actualmente `spring.application.name=ms-reservas`. Conviene cambiarlo a `ms-reportes` al corregir su configuración.

## Pruebas

Cada módulo incluye pruebas con Spring Boot. Desde la carpeta interna de un servicio, ejecuta:

```powershell
.\mvnw.cmd test
```
