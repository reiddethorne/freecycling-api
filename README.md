# FreeCycling API

FreeCycling is a Spring Boot REST API for a community reuse platform. Users can list items they no longer need, express interest in available items, and coordinate handoffs through shared transfer locations and meeting times.

This repository contains the backend service. It was developed collaboratively as the final project for CMSC 455 Backend Programming

## Features

- Register users and authenticate requests with JSON Web Tokens (JWTs)
- Hash passwords with BCrypt and an environment-provided pepper
- Create, browse, and update donated item listings
- Record user interest in available items
- Manage transfer sites and available meeting times
- Track a handoff from booking through completion
- Document endpoints with OpenAPI and Swagger UI
- Exercise service and API behavior with JUnit, Mockito, and REST Assured

## Technology

- Java 22
- Spring Boot 4
- Spring Web MVC and Spring Security
- Spring Data JPA and MySQL
- JWT authentication using JJWT
- Maven
- JUnit 5, Mockito, and REST Assured

## Project structure

```text
src/main/java/freecycling/example/freecycling/
├── Core/          Domain entities, repositories, and business services
├── Interface/     REST controllers and data-transfer objects
├── security/      JWT authentication and Spring Security configuration
└── services/      Shared application services
```

## Getting started

### Prerequisites

- Java 22 or newer
- MySQL 8 or newer

### 1. Create the database

```sql
CREATE DATABASE freecycling;
CREATE USER 'freecycling'@'localhost' IDENTIFIED BY 'choose-a-strong-password';
GRANT ALL PRIVILEGES ON freecycling.* TO 'freecycling'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Configure environment variables

Copy the example configuration and replace its placeholder values:

```bash
cp .env.example .env
```

Load those values into your current shell:

```bash
set -a
source .env
set +a
```

The application reads these variables:

| Variable | Purpose |
| --- | --- |
| `DATABASE_URL` | JDBC connection URL |
| `DATABASE_USERNAME` | MySQL username |
| `DATABASE_PASSWORD` | MySQL password |
| `PASSWORD_PEPPER` | Additional secret used when hashing passwords |
| `JWT_SECRET` | Secret of at least 32 characters used to sign authentication tokens |

The `.env` file is ignored by Git. Do not commit real credentials or reuse the example values in a deployed environment.

### 3. Run the API

```bash
./mvnw spring-boot:run
```

The API starts at `http://localhost:8080`. Interactive OpenAPI documentation is available at `http://localhost:8080/swagger-ui/index.html`.

## Authentication

Register or log in to receive a JWT. Include it with protected requests:

```http
Authorization: Bearer <token>
```

Tokens currently expire after 30 minutes.

## API overview

| Resource | Representative endpoints | Access |
| --- | --- | --- |
| Users | `POST /users`, `POST /users/login`, `GET /users/me` | Registration and login are public |
| Items | `GET /items`, `GET /items/getAvailable`, `POST /items` | Reading is public; writing is protected |
| Interests | `POST /interests`, `GET /interests/item/{itemId}` | Protected |
| Transfer sites | `GET /transfersites`, `POST /transfersites` | Reading is public; writing is protected |
| Meetings | `GET /meetings/available`, `POST /meetings/book/{id}` | Availability is public; booking is protected |

Swagger UI contains the complete endpoint list and request formats.

## Tests

Run the full test suite with:

```bash
./mvnw test
```

The integration tests use the `test` Spring profile and require a running MySQL database. Set `TEST_DATABASE_URL`, `TEST_DATABASE_USERNAME`, and `TEST_DATABASE_PASSWORD` if they differ from the defaults in `.env.example`.

## Contributors

- [Reid DeThorne](https://github.com/reiddethorne)
- [Liam Shackleton](https://github.com/LiamShackleton)
