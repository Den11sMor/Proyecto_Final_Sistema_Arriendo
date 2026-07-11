# Sistema de Arriendo de Vehiculos

Proyecto final basado en microservicios para administrar clientes, vehiculos,
reservas, pagos, sucursales, empleados y reportes de una empresa de arriendo de
vehiculos.

El sistema usa Java 17, Spring Boot, Spring Cloud, Spring Data JPA, MySQL,
Eureka, API Gateway, OpenFeign, HATEOAS, Swagger/OpenAPI y pruebas unitarias con
Spring Boot Test, Mockito, H2 y Datafaker.

## Arquitectura

```text
Cliente / Postman
      |
      v
API Gateway :8080
      |
      v
Eureka Server :8761
      |
      +-- ms-clientes    :8081
      +-- ms-vehiculos   :8082
      +-- ms-reservas    :8083
      +-- ms-pagos       :8084
      +-- ms-sucursales  :8085
      +-- ms-empleados   :8086
      +-- ms-reportes    :8087
```

El gateway enruta usando Eureka y el prefijo de cada microservicio:

```text
http://localhost:8080/ms-clientes/**
http://localhost:8080/ms-vehiculos/**
http://localhost:8080/ms-reservas/**
http://localhost:8080/ms-pagos/**
http://localhost:8080/ms-sucursales/**
http://localhost:8080/ms-empleados/**
http://localhost:8080/ms-reportes/**
```

## Servicios

| Servicio | Puerto | Descripcion |
|---|---:|---|
| `eureka-server` | 8761 | Registro y descubrimiento de servicios. |
| `api-gateway` | 8080 | Entrada unica para Postman y clientes HTTP. |
| `ms-clientes` | 8081 | Clientes y direcciones. |
| `ms-vehiculos` | 8082 | Vehiculos y categorias. |
| `ms-reservas` | 8083 | Reservas y estados de reserva. |
| `ms-pagos` | 8084 | Pagos asociados a reservas. |
| `ms-sucursales` | 8085 | Sucursales y regiones. |
| `ms-empleados` | 8086 | Empleados y busqueda de activos por anio. |
| `ms-reportes` | 8087 | Reportes generados desde reservas y pagos. |

## Perfiles

El perfil por defecto es `dev`.

### dev

Perfil para ejecucion local con MySQL.

| Servicio | Base de datos dev |
|---|---|
| `ms-clientes` | `prueba1_dev` |
| `ms-empleados` | `prueba1_dev` |
| `ms-sucursales` | `prueba1_dev` |
| `ms-reservas` | `prueba2_dev` |
| `ms-reportes` | `prueba2_dev` |
| `ms-pagos` | `prueba3_dev` |
| `ms-vehiculos` | `sistema_arriendo_dev` |

Ejecutar con perfil dev:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Tambien se puede usar:

```powershell
$env:SPRING_PROFILES_ACTIVE="dev"
.\mvnw.cmd spring-boot:run
```

### test

Perfil usado por las pruebas automatizadas. Cada microservicio tiene su archivo
en:

```text
src/test/resources/application-test.properties
src/test/resources/application-test.yml
```

El perfil `test` usa H2 y no requiere MySQL ni Eureka levantado para ejecutar
las pruebas unitarias/de controlador.

Ejecutar tests de un microservicio:

```powershell
.\mvnw.cmd test
```

Ejemplo:

```powershell
cd ms-clientes\ms-clientes
.\mvnw.cmd test
```

### prod

Perfil preparado para produccion o ambiente final con MySQL.

| Servicio | Base de datos prod |
|---|---|
| `ms-clientes` | `prueba1` |
| `ms-empleados` | `prueba1` |
| `ms-sucursales` | `prueba1` |
| `ms-reservas` | `prueba2` |
| `ms-reportes` | `prueba2` |
| `ms-pagos` | `prueba3` |
| `ms-vehiculos` | `sistema_arriendo` |

Ejecutar con perfil prod:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=prod"
```

Variables soportadas en los servicios:

```text
SERVER_PORT
DB_URL
DB_USERNAME
DB_PASSWORD
EUREKA_URL
SPRING_PROFILES_ACTIVE
```

## Orden de ejecucion

1. Levantar MySQL.
2. Levantar Eureka.
3. Levantar los microservicios.
4. Levantar API Gateway.
5. Probar desde Postman por `http://localhost:8080`.

Eureka:

```powershell
cd eureka-server\eureka-server
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Gateway:

```powershell
cd api-gateway\api-gateway
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Microservicio:

```powershell
cd ms-clientes\ms-clientes
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

## Rutas por microservicio

Las rutas se muestran sin el dominio. Para gateway anteponer:

```text
http://localhost:8080/{prefijo-del-servicio}
```

Ejemplo:

```text
http://localhost:8080/ms-clientes/api/v1/clientes
```

### ms-clientes

Prefijo gateway: `/ms-clientes`

```http
GET    /api/v1/clientes
GET    /api/v1/clientes/{id}
POST   /api/v1/clientes
PUT    /api/v1/clientes/{id}
DELETE /api/v1/clientes/{id}
GET    /api/v1/clientes/buscar-email?texto=gmail

GET    /api/v1/direcciones
GET    /api/v1/direcciones/{id}
POST   /api/v1/direcciones
PUT    /api/v1/direcciones/{id}
DELETE /api/v1/direcciones/{id}

GET    /api/v2/clientes
GET    /api/v2/clientes/{id}
POST   /api/v2/clientes
PUT    /api/v2/clientes/{id}
DELETE /api/v2/clientes/{id}
GET    /api/v2/clientes/buscar-email?texto=gmail

GET    /api/v2/direcciones
GET    /api/v2/direcciones/{id}
POST   /api/v2/direcciones
PUT    /api/v2/direcciones/{id}
DELETE /api/v2/direcciones/{id}
```

### ms-vehiculos

Prefijo gateway: `/ms-vehiculos`

```http
GET    /api/v1/vehiculos
GET    /api/v1/vehiculos/{id}
POST   /api/v1/vehiculos
PUT    /api/v1/vehiculos/{id}
DELETE /api/v1/vehiculos/{id}
GET    /api/v1/vehiculos/disponibles/precio-menor/{precio}

GET    /api/v1/categorias
GET    /api/v1/categorias/{id}
POST   /api/v1/categorias
PUT    /api/v1/categorias/{id}
DELETE /api/v1/categorias/{id}

GET    /api/v2/vehiculos
GET    /api/v2/vehiculos/{id}
POST   /api/v2/vehiculos
PUT    /api/v2/vehiculos/{id}
DELETE /api/v2/vehiculos/{id}
GET    /api/v2/vehiculos/disponibles/precio-menor/{precio}

GET    /api/v2/categorias
GET    /api/v2/categorias/{id}
POST   /api/v2/categorias
PUT    /api/v2/categorias/{id}
DELETE /api/v2/categorias/{id}
```

### ms-reservas

Prefijo gateway: `/ms-reservas`

```http
GET    /api/v1/reservas
GET    /api/v1/reservas/{id}
POST   /api/v1/reservas
PUT    /api/v1/reservas/{id}
DELETE /api/v1/reservas/{id}
GET    /api/v1/reservas/desde-fecha?fecha=2026-07-01

GET    /api/v1/estados-reserva
GET    /api/v1/estados-reserva/{id}
POST   /api/v1/estados-reserva
PUT    /api/v1/estados-reserva/{id}
DELETE /api/v1/estados-reserva/{id}

GET    /api/v2/reservas
GET    /api/v2/reservas/{id}
POST   /api/v2/reservas
PUT    /api/v2/reservas/{id}
DELETE /api/v2/reservas/{id}
GET    /api/v2/reservas/desde-fecha?fecha=2026-07-01

GET    /api/v2/estados-reserva
GET    /api/v2/estados-reserva/{id}
POST   /api/v2/estados-reserva
PUT    /api/v2/estados-reserva/{id}
DELETE /api/v2/estados-reserva/{id}
```

### ms-pagos

Prefijo gateway: `/ms-pagos`

```http
GET    /api/v1/pagos
GET    /api/v1/pagos/{id}
POST   /api/v1/pagos
PUT    /api/v1/pagos/{id}
DELETE /api/v1/pagos/{id}
GET    /api/v1/pagos/rango?min=10000&max=200000

GET    /api/v2/pagos
GET    /api/v2/pagos/{id}
POST   /api/v2/pagos
PUT    /api/v2/pagos/{id}
DELETE /api/v2/pagos/{id}
GET    /api/v2/pagos/rango?min=10000&max=200000
```

### ms-reportes

Prefijo gateway: `/ms-reportes`

```http
GET    /api/v1/reportes
GET    /api/v1/reportes/{id}
POST   /api/v1/reportes
PUT    /api/v1/reportes/{id}
DELETE /api/v1/reportes/{id}
GET    /api/v1/reportes/reserva/{reservaId}
GET    /api/v1/reportes/pago-confirmado

GET    /api/v2/reportes
GET    /api/v2/reportes/{id}
POST   /api/v2/reportes
PUT    /api/v2/reportes/{id}
DELETE /api/v2/reportes/{id}
GET    /api/v2/reportes/reserva/{reservaId}
GET    /api/v2/reportes/pago-confirmado
```

### ms-sucursales

Prefijo gateway: `/ms-sucursales`

```http
GET    /api/v1/sucursales
GET    /api/v1/sucursales/{id}
POST   /api/v1/sucursales
PUT    /api/v1/sucursales/{id}
DELETE /api/v1/sucursales/{id}
GET    /api/v1/sucursales/operativas

GET    /api/v1/regiones
GET    /api/v1/regiones/{id}
POST   /api/v1/regiones
PUT    /api/v1/regiones/{id}
DELETE /api/v1/regiones/{id}

GET    /api/v2/sucursales
GET    /api/v2/sucursales/{id}
POST   /api/v2/sucursales
PUT    /api/v2/sucursales/{id}
DELETE /api/v2/sucursales/{id}
GET    /api/v2/sucursales/operativas

GET    /api/v2/regiones
GET    /api/v2/regiones/{id}
POST   /api/v2/regiones
PUT    /api/v2/regiones/{id}
DELETE /api/v2/regiones/{id}
```

### ms-empleados

Prefijo gateway: `/ms-empleados`

```http
GET    /api/v1/empleados
GET    /api/v1/empleados/{id}
POST   /api/v1/empleados
PUT    /api/v1/empleados/{id}
DELETE /api/v1/empleados/{id}
GET    /api/v1/activos/anio/{anio}

GET    /api/v2/empleados
GET    /api/v2/empleados/{id}
POST   /api/v2/empleados
PUT    /api/v2/empleados/{id}
DELETE /api/v2/empleados/{id}
GET    /api/v2/activos/anio/{anio}
```

## Swagger

Swagger UI debe abrirse directo por el puerto de cada microservicio. Por
gateway las APIs funcionan con el prefijo `/ms-*`, pero Swagger UI puede fallar
por redirecciones internas que pierden ese prefijo.

Directo por servicio:

```text
http://localhost:8081/doc/swagger-ui.html
http://localhost:8082/doc/swagger-ui.html
http://localhost:8083/doc/swagger-ui.html
http://localhost:8084/doc/swagger-ui.html
http://localhost:8085/doc/swagger-ui.html
http://localhost:8086/doc/swagger-ui.html
http://localhost:8087/doc/swagger-ui.html
```

OpenAPI JSON directo por servicio:

```text
http://localhost:8081/v3/api-docs
http://localhost:8082/v3/api-docs
http://localhost:8083/v3/api-docs
http://localhost:8084/v3/api-docs
http://localhost:8085/v3/api-docs
http://localhost:8086/v3/api-docs
http://localhost:8087/v3/api-docs
```

## Pruebas de excepciones

Casos basicos para Postman:

```http
GET    /api/v1/{recurso}/999999       -> 404 Not Found
DELETE /api/v1/{recurso}/999999       -> 404 Not Found
POST   /api/v1/{recurso} con {}       -> 400 Bad Request
PUT    /api/v1/{recurso}/999999       -> 404 Not Found
```

Si se prueba por gateway y el servicio no esta registrado en Eureka, el gateway
responde:

```text
503 Service Unavailable
```

## Comandos utiles

Ver Eureka:

```text
http://localhost:8761
```

Ejecutar tests:

```powershell
cd ms-pagos\ms-pagos
.\mvnw.cmd test
```

Ejecutar con otro puerto:

```powershell
$env:SERVER_PORT="8090"
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Ejecutar con otra base de datos:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/prueba1_dev?useSSL=false&serverTimezone=America/Santiago&allowPublicKeyRetrieval=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD=""
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```
