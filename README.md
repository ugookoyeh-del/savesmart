# SaveSmart

SaveSmart is an educational fintech-style savings-goal REST API built with Java 17, Spring Boot, Spring Data JPA, Spring Security and MySQL.

> **Project status:** initial backend MVP. This is a learning portfolio project, not a real banking or payment service. Deposits and withdrawals update an internal ledger only; no money is moved.

## Features

- Register an account with a BCrypt-hashed password
- HTTP Basic authentication for protected endpoints
- Create and list personal savings goals
- Record deposits and withdrawals against a goal
- Prevent withdrawals that exceed the goal's saved balance
- View goal-specific and account-wide transaction history
- Validate request payloads and return readable API errors
- Docker Compose MySQL development database

## Tech stack

- Java 17
- Spring Boot 3.4
- Spring Web, Spring Data JPA, Spring Security, Bean Validation
- MySQL 8.4
- Maven, JUnit 5

## Run locally

Requirements: JDK 17+, Maven 3.9+, Docker Desktop.

1. Start MySQL:

   ```bash
   docker compose up -d
   ```

2. Start the API:

   ```bash
   mvn spring-boot:run
   ```

The API listens on `http://localhost:8080`.

Database defaults are for local development only. Override `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` in other environments. Do not commit production credentials.

## API quick start

Register (no authentication required):

```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "Ada Example",
  "email": "ada@example.com",
  "password": "change-this-password"
}
```

Use the registered email and password as HTTP Basic credentials for the remaining endpoints.

Create a goal:

```http
POST /api/goals
Authorization: Basic <credentials>
Content-Type: application/json

{
  "name": "Emergency Fund",
  "description": "Build a three-month safety net",
  "targetAmount": 500000.00,
  "targetDate": "2027-12-31"
}
```

Deposit:

```http
POST /api/goals/1/deposits
Content-Type: application/json

{ "amount": 25000.00, "note": "Monthly contribution" }
```

Withdraw:

```http
POST /api/goals/1/withdrawals
Content-Type: application/json

{ "amount": 5000.00, "note": "Unexpected expense" }
```

Other endpoints:

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register |
| POST | `/api/goals` | Create a savings goal |
| GET | `/api/goals` | List your goals |
| POST | `/api/goals/{id}/deposits` | Add a deposit |
| POST | `/api/goals/{id}/withdrawals` | Record a withdrawal |
| GET | `/api/goals/{id}/transactions` | View one goal's history |
| GET | `/api/transactions` | View all your transactions |

## Important engineering notes

- Every goal query is scoped to the authenticated account, preventing users from accessing another account's goal by changing an ID.
- Passwords are stored as BCrypt hashes, never as plaintext.
- Amounts use `BigDecimal`, not floating-point arithmetic.
- The current MVP uses Hibernate schema auto-update for local development. Add versioned Flyway migrations before production.
- HTTP Basic is intentionally simple for this learning milestone. A production deployment needs HTTPS, a carefully designed token/session strategy, rate limiting, stronger operational controls, and security review.
- A real financial product needs a properly designed immutable double-entry ledger, idempotency keys, reconciliation, audit controls and integration with a regulated payment provider. This MVP is not a substitute.

## Tests

Run:

```bash
mvn test
```

## Roadmap

- [ ] Add integration tests for registration, goal ownership, deposits and withdrawals
- [ ] Add account summary and savings analytics
- [ ] Add Flyway migrations
- [ ] Add pagination and filtering for transaction history
- [ ] Add frontend client
- [ ] Add CI workflow and API documentation
