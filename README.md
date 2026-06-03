# Nutripro API

API REST en Java 17 con Spring Boot 3.5.x para gestionar usuarios y registros diarios de seguimiento nutricional.

## Objetivo
Esta API está diseñada como un proyecto demostrable para una entrevista técnica de Java. Su prioridad es la claridad, la separación de responsabilidades y la calidad del código.

## Stack técnico

- Java 17
- Spring Boot 3.5.x
- Maven
- Spring Web
- Spring Data JPA
- H2 Database
- Bean Validation
- Lombok
- JUnit 5
- Mockito
- MockMvc

## Arquitectura

El proyecto sigue una arquitectura por capas:

- `controller`
- `service`
- `repository`
- `model`
- `dto.request`
- `dto.response`
- `mapper`
- `exception`
- `config`

## Endpoints principales

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

- `GET /api/tracking`
- `GET /api/tracking/{id}`
- `GET /api/tracking/user/{userId}`
- `POST /api/tracking`
- `PUT /api/tracking/{id}`
- `DELETE /api/tracking/{id}`

## Ejecución

Desde la carpeta del proyecto:

```bash
./mvnw spring-boot:run
```

La aplicación se ejecuta en `http://localhost:8080`.

## Enlace GitHub

https://github.com/RicardMayol89
