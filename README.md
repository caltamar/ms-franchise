# Franchise Management API

## Descripción

Microservicio reactivo desarrollado con Java y Spring Boot para la gestión de franquicias, sucursales y productos.

La solución fue implementada siguiendo los principios de Clean Architecture utilizando el Scaffold de Bancolombia y programación reactiva con Spring WebFlux.

## Arquitectura

La aplicación está estructurada siguiendo Clean Architecture:

- Model: Entidades y contratos del dominio.
- Use Cases: Casos de uso de negocio.
- Entry Points: Exposición de APIs REST mediante WebFlux.
- Driven Adapters: Persistencia MongoDB.

### Modelo de Dominio

```text
Franchise
 └── Branch
      └── Product
```

## Tecnologías

- Java 23
- Spring Boot 3
- Spring WebFlux
- MongoDB
- MongoDB Atlas
- Docker
- Gradle
- JUnit 5
- Mockito
- Reactor Test

## Persistencia

La aplicación utiliza MongoDB Atlas como servicio administrado de base de datos en la nube.

Razones de la elección:

- Modelo documental flexible.
- Adecuado para estructuras jerárquicas.
- Integración reactiva mediante Spring Data MongoDB Reactive.
- Fácil despliegue y administración.

## Ejecución Local

### Prerrequisitos

- Java 23
- Gradle 9+
- Docker Desktop
- Variable de entorno `MONGODB_URI` configurada

## Variables de Entorno

La aplicación requiere la siguiente variable de entorno para conectarse a MongoDB Atlas:

| Variable | Descripción |
|-----------|-------------|
| MONGODB_URI | URI de conexión a MongoDB Atlas |

### Ejemplo PowerShell

```powershell
$env:MONGODB_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/franchise_db"
gradle bootRun
```

### Ejemplo Docker

```bash
docker run -d \
-p 8080:8080 \
-e MONGODB_URI="mongodb+srv://<user>:<password>@<cluster>.mongodb.net/franchise_db" \
--name franchise-api \
ms-franchise
```

### Nota

Por motivos de seguridad, las credenciales de MongoDB Atlas no se encuentran versionadas en el repositorio.

La variable `MONGODB_URI` debe configurarse antes de ejecutar la aplicación.

### Compilar

```bash
gradle clean build
```

### Ejecutar

```bash
gradle bootRun
```

La aplicación quedará disponible en:

```text
http://localhost:8080
```

## Endpoints Implementados

### Franquicias

Crear franquicia

```http
POST /api/franchises
```

Actualizar nombre de franquicia

```http
PATCH /api/franchises/{franchiseId}
```

### Sucursales

Agregar sucursal

```http
POST /api/franchises/{franchiseId}/branches
```

Actualizar nombre de sucursal

```http
PATCH /api/franchises/{franchiseId}/branches/{branchId}
```

### Productos

Agregar producto

```http
POST /api/franchises/{franchiseId}/branches/{branchId}/products
```

Actualizar stock

```http
PATCH /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock
```

Actualizar nombre

```http
PATCH /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}
```

Eliminar producto

```http
DELETE /api/franchises/{franchiseId}/branches/{branchId}/products/{productId}
```

### Consultas

```http
GET /api/franchises/{franchiseId}/top-stock-products
```

## Pruebas

Se implementaron pruebas unitarias utilizando:

- JUnit 5
- Mockito
- StepVerifier

## Infraestructura como Código

Se implementó Infraestructura como Código (IaC) utilizando Terraform con el objetivo de automatizar la provisión de recursos en AWS.

Actualmente se incluyen las siguientes definiciones:

- Provider de AWS.
- Repositorio ECR para almacenamiento de imágenes Docker.
- Variables parametrizables.
- Outputs para exponer información de los recursos creados.

La configuración Terraform se encuentra en:

```text
terraform/
```

## Funcionalidades Adicionales

- Actualización de nombre de franquicia.
- Actualización de nombre de sucursal.
- Actualización de nombre de producto.
- Validación de nombres únicos para franquicias.
- Persistencia en MongoDB Atlas.
- Empaquetado usando Docker.