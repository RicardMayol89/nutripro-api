# PROJECT_PLAN.md - Plan de desarrollo de Nutripro API

## Objetivo
Construir una API REST en Java con Spring Boot que permita gestionar usuarios y registros diarios de seguimiento nutricional.

El proyecto está inspirado en NutriPro, una aplicación móvil de hábitos saludables. Esta API representa una posible evolución backend en Java del sistema.

## Módulos principales

### 1. Usuarios
Permite gestionar usuarios de la aplicación.

Campos iniciales:

- id
- name
- email
- heightCm
- weightKg
- dailyWaterGoalLiters
- dailyCaloriesGoal
- mealsPerDay

Endpoints previstos:

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

### 2. Seguimiento diario
Permite registrar datos diarios de hábitos saludables.

Campos iniciales:

- id
- userId
- date
- waterLiters
- calories
- meals
- notes

Endpoints previstos:

- `GET /api/tracking`
- `GET /api/tracking/{id}`
- `GET /api/tracking/user/{userId}`
- `POST /api/tracking`
- `PUT /api/tracking/{id}`
- `DELETE /api/tracking/{id}`

## Orden de implementación

### Fase 1 - Base del proyecto

- Revisar estructura generada por Spring Initializr.
- Configurar `application.properties`.
- Ejecutar proyecto localmente.
- Confirmar que arranca en `localhost:8080`.

### Fase 2 - Dominio de usuarios

- Crear entidad `User`.
- Crear DTOs `UserRequest` y `UserResponse`.
- Crear `UserRepository`.
- Crear `UserMapper`.
- Crear `UserService`.
- Crear `UserController`.
- Probar endpoints de usuario.

### Fase 3 - Gestión de errores

- Crear `ResourceNotFoundException`.
- Crear `GlobalExceptionHandler`.
- Devolver respuestas de error limpias y entendibles.

### Fase 4 - Seguimiento diario

- Crear entidad `TrackingEntry`.
- Relacionarla con `User`.
- Crear DTOs.
- Crear repository, mapper, service y controller.
- Probar endpoints.

### Fase 5 - Pruebas

- Tests unitarios de servicios.
- Tests de controladores con MockMvc.
- Tests de repositorios con H2.

### Fase 6 - Preparación entrevista

- Revisar cada clase.
- Añadir comentarios útiles.
- Preparar explicación de arquitectura.
- Preparar modificaciones típicas: añadir un campo nuevo.
- añadir filtro por fecha.
- añadir validaciones.
- añadir endpoint nuevo.
- cambiar H2 por MySQL.
- añadir autenticación JWT en una versión futura.
