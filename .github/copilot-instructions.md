# Copilot Instructions - Nutripro API

## Contexto del proyecto
Este proyecto es una API REST desarrollada en Java 17 con Spring Boot 3.5.x.

El objetivo es construir un backend sencillo, limpio y defendible para una entrevista técnica Java. La API está inspirada en NutriPro, una aplicación de seguimiento nutricional y hábitos saludables.

La prioridad del proyecto es que el código sea claro, mantenible, probado y fácil de explicar durante una entrevista.

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

## Arquitectura obligatoria
El proyecto debe organizarse por capas:

- `controller`: expone endpoints REST.
- `service`: contiene la lógica de negocio.
- `repository`: accede a base de datos mediante Spring Data JPA.
- `model`: contiene entidades JPA.
- `dto.request`: contiene DTOs de entrada.
- `dto.response`: contiene DTOs de salida.
- `mapper`: transforma entidades en DTOs y DTOs en entidades.
- `exception`: contiene excepciones personalizadas y gestión global de errores.
- `config`: configuración técnica del proyecto.

## Reglas generales

1. No incluir lógica de negocio en los controladores.
2. No devolver entidades JPA directamente desde los controladores.
3. Usar DTOs para entrada y salida de datos.
4. Usar Bean Validation en los DTOs de entrada.
5. Usar inyección por constructor.
6. Evitar código duplicado.
7. Usar nombres descriptivos.
8. Mantener métodos cortos y con una única responsabilidad.
9. No generar funcionalidades innecesarias.
10. No avanzar con muchos cambios de golpe.

## Calidad de código
El código debe tener un nivel razonable para SonarLint/SonarQube:

- Sin imports no usados.
- Sin variables no usadas.
- Sin métodos excesivamente largos.
- Sin duplicidad innecesaria.
- Sin capturas genéricas de excepciones salvo justificación.
- Sin valores mágicos cuando puedan extraerse a constantes.
- Sin mezclar responsabilidades entre capas.
- Sin comentarios obvios.

## Comentarios
Usar comentarios solo cuando aporten valor.

Preferencia:

- JavaDoc en clases importantes.
- JavaDoc en métodos públicos relevantes.
- Comentarios para explicar decisiones de diseño.
- No comentar líneas obvias.

## Forma de trabajo
Antes de proponer cambios, explicar:

1. Qué archivo se va a crear o modificar.
2. Para qué sirve.
3. Por qué es necesario.
4. Qué código se va a añadir.
5. Cómo se puede probar.

No continuar al siguiente bloque hasta que el usuario responda:

`allow`

## Objetivo para entrevista
El proyecto debe poder explicarse claramente en una entrevista técnica:

- Qué problema resuelve.
- Qué arquitectura usa.
- Qué hace cada capa.
- Cómo se validan datos.
- Cómo se gestionan errores.
- Cómo se prueban servicios, controladores y repositorios.
- Cómo se ampliaría con nuevas funcionalidades.
