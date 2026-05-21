# Flareye API

> **This repository is archived.** Development has stopped. The code is available for reference and learning.

A microservices backend built with **Spring Boot 3** and **Spring Cloud**. This project was an early-stage attempt to build a multi-service SaaS platform. Only the authentication layer was implemented before development stopped.

---

## What Was Built

The project follows a microservices architecture. Each service runs independently and communicates through an API Gateway.

```
Client → API Gateway (port 8080) → Authentication Service (port 8081)
                                  → [Other services — not implemented]

Eureka Server (port 8761) — keeps track of all running services
```

### Services

| Service | Status | Description |
|---|---|---|
| `eureka-server` | Working | Service registry — services register here so they can find each other |
| `api-gateway` | Working | Single entry point — routes requests to the right service |
| `authentication-service` | Working | User registration, login, and JWT token generation |
| `user-service` | Not built | Planned for user profile management |
| `product-service` | Not built | Planned for product catalog |
| `payment-service` | Not built | Planned for payment processing |
| `notification-service` | Not built | Planned for emails and push notifications |
| `admin-service` | Not built | Planned for admin dashboard backend |
| `api-key-service` | Not built | Planned for API key management |
| `support-service` | Not built | Planned for support tickets |

---

## API Endpoints

Only the authentication service has working endpoints.

**Base URL:** `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Create a new user account |
| `POST` | `/auth/login` | Log in and receive a JWT token |

### Register

```json
POST /auth/register
{
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "password": "your-password"
}
```

### Login

```json
POST /auth/login
{
  "email": "user@example.com",
  "password": "your-password"
}
```

Returns a JWT token you can use for authenticated requests.

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.4.3 |
| Service Discovery | Spring Cloud Eureka |
| API Gateway | Spring Cloud Gateway |
| Security | Spring Security, JWT (JJWT) |
| Database | PostgreSQL |
| Cache | Redis |
| Message Broker | RabbitMQ, Apache Kafka (configured, not used) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| Containers | Docker, Docker Compose |

---

## Running the Project

**Requirements:** Docker, Docker Compose, Java 21, Maven

```bash
# Start infrastructure (PostgreSQL, Redis)
docker-compose up -d

# Start Eureka Server first
cd backend/services/eureka-server
mvn spring-boot:run

# Start API Gateway
cd backend/services/api-gateway
mvn spring-boot:run

# Start Authentication Service
cd backend/services/authentication-service
mvn spring-boot:run
```

> **Note:** The JWT secret key in the config is an example key. Do not use it in production.

---

## Project Structure

```
flareye-api/
├── backend/
│   └── services/
│       ├── eureka-server/
│       ├── api-gateway/
│       ├── authentication-service/
│       └── [stub services]/
├── docker-compose.yaml
└── LICENSE
```

---

## License

MIT License — see [LICENSE](LICENSE) for details.
