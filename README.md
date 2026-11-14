<div align="center">

<img src="assets/parking-system.gif" alt="Smart Parking System" width="100%"/>

<br/><br/>

# 🚗 Smart Parking System

### A production-grade, containerized REST API that allocates, prices and manages parking — automatically.

<sub>Slot allocation &nbsp;•&nbsp; JWT + RBAC &nbsp;•&nbsp; Surge pricing &nbsp;•&nbsp; Pessimistic locking &nbsp;•&nbsp; Admin analytics &nbsp;•&nbsp; Docker-ready</sub>

<br/>

[![Java](https://img.shields.io/badge/Java-21%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

![API](https://img.shields.io/badge/API-REST-0EA5E9?style=flat-square)
![Auth](https://img.shields.io/badge/Auth-JWT-F59E0B?style=flat-square)
![Security](https://img.shields.io/badge/Security-RBAC-8B5CF6?style=flat-square)
![Concurrency](https://img.shields.io/badge/Concurrency-Pessimistic%20Lock-EF4444?style=flat-square)
![Mapping](https://img.shields.io/badge/Mapping-MapStruct-EAB308?style=flat-square)
![Deploy](https://img.shields.io/badge/Deploy-Docker%20Compose-2496ED?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-22C55E?style=flat-square)

<br/>

[**Overview**](#-overview) &nbsp;·&nbsp;
[**Features**](#-feature-highlights) &nbsp;·&nbsp;
[**Architecture**](#-architecture) &nbsp;·&nbsp;
[**Core Flows**](#-core-flows) &nbsp;·&nbsp;
[**Docker**](#-docker--containerization) &nbsp;·&nbsp;
[**API**](#-api-reference) &nbsp;·&nbsp;
[**Quick Start**](#-quick-start)

</div>

<br/>

---

<table align="center">
<tr>
<td align="center" width="20%"><h2>3</h2><sub>Vehicle types<br/>CAR · BIKE · AUTO</sub></td>
<td align="center" width="20%"><h2>2</h2><sub>Roles<br/>USER · ADMIN</sub></td>
<td align="center" width="20%"><h2>11</h2><sub>REST endpoints</sub></td>
<td align="center" width="20%"><h2>0</h2><sub>Double-booked slots<br/>by design</sub></td>
<td align="center" width="20%"><h2>1</h2><sub>Command to run<br/>the full stack</sub></td>
</tr>
</table>

---

## 📑 Table of Contents

<details>
<summary><b>Click to expand</b></summary>

<br/>

1. [Overview](#-overview)
2. [Feature Highlights](#-feature-highlights)
3. [Architecture](#-architecture)
4. [Core Flows](#-core-flows)
5. [Pricing Engine](#-pricing-engine)
6. [Concurrency Control](#-concurrency-control)
7. [Admin Analytics](#-admin-analytics)
8. [Docker & Containerization](#-docker--containerization)
9. [Database Model](#-database-model)
10. [API Reference](#-api-reference)
11. [Project Structure](#-project-structure)
12. [Tech Stack](#-tech-stack)
13. [Quick Start](#-quick-start)
14. [Roadmap](#-roadmap)
15. [Author](#-author)

</details>

---

## 🎯 Overview

> **Smart Parking System is not a CRUD demo.** It is a backend built around the problems real parking platforms actually face — security, fairness under concurrency, pricing, observability and repeatable deployment.

| | Real-world question | How this project answers it |
|:---:|---|---|
| 🔐 | How do we protect APIs without server sessions? | Stateless **JWT** + Spring Security + **RBAC** |
| 🅿️ | Who picks the slot? | The backend — **vehicle-type based** automatic allocation |
| ⚡ | What if two users grab the last slot at once? | `@Transactional` + **`PESSIMISTIC_WRITE`** locking |
| 💰 | How should price react to demand? | DB-backed rates + **occupancy-based surge** multiplier |
| 🔄 | How do entry and exit affect state? | Synchronized **booking & slot state machines** |
| 📊 | How do admins see utilization and revenue? | Dedicated **analytics services** and APIs |
| 🧩 | How do we keep API and DB models decoupled? | **DTOs + MapStruct** |
| 🐳 | How do we run it identically everywhere? | **Docker + Docker Compose** — one command, same result |

---

## ✨ Feature Highlights

<table>
<tr>
<td width="50%" valign="top">

### 🔐 Security
JWT authentication, BCrypt password hashing, method-level `@PreAuthorize`, role-based access and ownership validation.

### 🅿️ Smart Allocation
Users never choose a slot. The system finds and locks a matching `AVAILABLE` slot for the vehicle type.

### 📋 Booking Lifecycle
Entry, exit and paginated history, with booking and slot states always kept in sync.

### 💰 Dynamic Pricing
Rates live in the database. A surge multiplier is computed from occupancy and **locked at entry**.

</td>
<td width="50%" valign="top">

### ⚡ Concurrency Safe
Database-level pessimistic locking guarantees a slot is never assigned twice.

### 📊 Admin Analytics
Occupancy, booking and revenue insights, including vehicle-type-wise slot stats.

### 🧩 Clean API Design
Entities are never exposed. Request/response DTOs are mapped with MapStruct.

### 🐳 Containerized
Backend and MySQL run as isolated containers, orchestrated with Docker Compose and configured through environment variables.

</td>
</tr>
</table>

---

## 🏗️ Architecture

A clean, layered architecture with clear responsibilities per layer.

```mermaid
flowchart TB
    A["🖥️ Client · Postman · Swagger"]
    B["🎛️ REST Controllers"]
    C["🧠 Service Layer · Business Rules"]
    D["🗃️ Repository Layer · Spring Data JPA"]
    E[("🐬 MySQL")]

    A --> B --> C --> D --> E

    S["🔐 Spring Security<br/>JWT + RBAC"] -.-> B
    M["🧩 MapStruct<br/>DTO Mapping"] -.-> C
    X["🛡️ Global Exception Handler"] -.-> B
```

<details>
<summary><b>📥 Request processing pipeline</b></summary>

<br/>

```text
HTTP Request
   → Spring Security
   → JWT Validation
   → Authentication / Authorization
   → Controller
   → DTO Validation
   → Service (Business Rules)
   → Transaction
   → Repository
   → MySQL
   → Entity
   → MapStruct
   → Response DTO
   → HTTP Response
```

</details>

### 🧩 DTO & Mapping Strategy

Entities are **never** exposed through REST APIs.

```mermaid
flowchart LR
    A["JSON Request"] --> B["Request DTO"] --> C{{"MapStruct"}} --> D["JPA Entity"] --> E[("Database")]
    E --> D --> F{{"MapStruct"}} --> G["Response DTO"] --> H["JSON Response"]
```

| Benefit | Why it matters |
|---|---|
| Clean API contracts | Clients depend on DTOs, not on table structure |
| Decoupled models | DB schema can evolve without breaking the API |
| Less boilerplate | MapStruct generates mappers at compile time |
| Maintainability | One place to change a mapping |

### 🛡️ Validation & Exception Handling

Jakarta Bean Validation with centralized handling through `@RestControllerAdvice`.

| ✅ Validated | 🚨 Handled scenarios |
|---|---|
| Required fields, email, password | Validation errors |
| Phone number, vehicle number | Resource not found · duplicate resources |
| Parking slot data, parking rates | Unauthorized actions · slot unavailable |
| | Authentication failures · access denied · runtime exceptions |

---

## 🔄 Core Flows

### 🔐 Authentication

Stateless JWT authentication with Spring Security.

> **Capabilities:** registration & login · BCrypt hashing · JWT generation & validation · custom `UserDetailsService` · JWT authentication filter · `USER` / `ADMIN` roles · method-level security with `@PreAuthorize` · ownership validation

**Phase 1 — Login (token generation)**

```mermaid
sequenceDiagram
    autonumber
    actor C as Client
    participant AC as AuthController
    participant AM as AuthenticationManager
    participant US as CustomUserDetailsService
    participant DB as MySQL
    participant JU as JwtUtils

    C->>AC: POST /auth/login (email, password)
    AC->>AM: authenticate(credentials)
    AM->>US: loadUserByUsername(email)
    US->>DB: Find user + roles
    DB-->>US: User data
    US-->>AM: UserDetails
    AM->>AM: Match password with BCrypt hash
    AM-->>AC: Authentication success
    AC->>JU: generateToken(user)
    JU-->>AC: Signed JWT
    AC-->>C: 200 OK + JWT
```

**Phase 2 — Accessing a protected API**

```mermaid
sequenceDiagram
    autonumber
    actor C as Client
    participant F as JwtAuthenticationFilter
    participant JU as JwtUtils
    participant SC as SecurityContext
    participant CT as Controller

    C->>F: Request + Authorization: Bearer JWT
    F->>JU: validateToken(jwt)
    alt Token invalid or expired
        JU-->>F: Invalid
        F-->>C: 401 Unauthorized
    else Token valid
        JU-->>F: Valid + username
        F->>SC: Set Authentication (user + roles)
        F->>CT: Forward request
        alt Role not allowed (@PreAuthorize)
            CT-->>C: 403 Forbidden
        else Role allowed
            CT-->>C: 200 OK + Response DTO
        end
    end
```

### 🅿️ Intelligent Slot Allocation

Users **do not select a slot manually**. The backend finds an available slot that matches the vehicle type.

| Vehicle | Enum |
|:---:|:---:|
| 🚗 Car | `CAR` |
| 🏍️ Bike | `BIKE` |
| 🛺 Auto | `AUTO` |

```mermaid
flowchart TD
    A(["🅿️ Parking Request"]) --> B["Authenticate User"]
    B --> C["Validate Vehicle"]
    C --> D["Check Ownership"]
    D --> E["Check Active Booking"]
    E --> F["Find AVAILABLE Matching Slot"]
    F --> G["🔒 Acquire Database Lock"]
    G --> H["Allocate Slot"]
    H --> I["Slot = OCCUPIED"]
    I --> J(["✅ Create ACTIVE Booking"])
```

### 📋 Booking & Slot Lifecycle

Booking state and slot state are always kept synchronized.

<table>
<tr>
<td width="50%" valign="top">

**Booking states**

```mermaid
stateDiagram-v2
    [*] --> ACTIVE: Book Parking
    ACTIVE --> COMPLETED: Vehicle Exit
    COMPLETED --> [*]
```

</td>
<td width="50%" valign="top">

**Slot states**

```mermaid
stateDiagram-v2
    [*] --> AVAILABLE
    AVAILABLE --> OCCUPIED: Booking
    OCCUPIED --> AVAILABLE: Exit
    AVAILABLE --> MAINTENANCE: Admin
    MAINTENANCE --> AVAILABLE: Admin
```

</td>
</tr>
</table>

### 🚪 Exit & Billing

| Step | Action |
|:---:|---|
| 1 | Find the user's `ACTIVE` booking and verify ownership |
| 2 | Record exit time and calculate duration |
| 3 | Apply **minimum one-hour billing** (partial hours round up) |
| 4 | Apply the booking's **locked surge multiplier** |
| 5 | Calculate the final amount |
| 6 | Mark booking `COMPLETED` and release the slot |

```mermaid
flowchart TD
    A(["🚪 Exit Request"]) --> B["Find ACTIVE Booking"]
    B --> C["Verify Ownership"]
    C --> D["Record End Time"]
    D --> E["Calculate Duration"]
    E --> F["Minimum 1 Hour Billing"]
    F --> G["Apply Locked Surge Multiplier"]
    G --> H["Calculate Amount"]
    H --> I["Booking = COMPLETED"]
    I --> J(["Slot = AVAILABLE"])
```

### 📄 Booking History

Users can retrieve their own bookings with **pagination, sorting and DTO-based responses**.

```http
GET /booking/my-bookings?page=0&size=10&sort=startTime,desc
GET /booking/my-bookings?page=0&size=5&sort=amount,desc
```

```json
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3
}
```

---

## 💰 Pricing Engine

Rates are stored in the **database**, not hardcoded in booking logic, and are managed independently through admin APIs.

| Vehicle | Base rate |
|:---|---:|
| 🏍️ Bike | **₹20** / hour |
| 🛺 Auto | **₹30** / hour |
| 🚗 Car | **₹50** / hour |

### ⚡ Surge Pricing

A surge multiplier is calculated from current parking occupancy and **locked into the booking at entry**, so the pricing basis stays consistent until exit.

```mermaid
flowchart LR
    A["Total Capacity"] --> C["Occupancy Ratio"]
    B["Occupied Slots"] --> C
    C --> D["Surge Multiplier"]
    D --> E["Booking Created"]
    E --> F["🔒 Multiplier Locked"]
    F --> G["Final Billing at Exit"]
```

> [!TIP]
> Locking the multiplier at entry means a driver is never charged more because the lot filled up *after* they arrived.

---

## ⚡ Concurrency Control

> **What happens if multiple users request the last available slot at almost the same time?**

Slot allocation is protected with `@Transactional`, `PESSIMISTIC_WRITE`, database-level locking and atomic slot state updates — the same slot is never assigned to concurrent requests.

```mermaid
sequenceDiagram
    participant A as 👤 User A
    participant B as 👤 User B
    participant DB as 🐬 MySQL

    A->>DB: Find available slot + lock
    B->>DB: Find available slot + lock
    DB-->>A: Slot locked
    A->>DB: Slot = OCCUPIED
    DB-->>B: Wait / re-check
    B->>DB: Cannot allocate same occupied slot
```

| Mechanism | Purpose |
|---|---|
| `@Transactional` | Keeps the allocation atomic |
| `PESSIMISTIC_WRITE` | Blocks competing requests on the same row |
| Atomic state update | Slot and booking change together or not at all |

---

## 📊 Admin Analytics

<table>
<tr>
<td width="50%" valign="top">

### 🅿️ Parking analytics
- Total slots
- Available slots
- Occupied slots
- Maintenance slots
- Overall occupancy %
- Vehicle-type-wise slot stats

</td>
<td width="50%" valign="top">

### 📋 Booking analytics
- Total bookings
- Active bookings
- Completed bookings
- Total completed revenue

</td>
</tr>
</table>

```mermaid
flowchart LR
    A[("Parking Slots")] --> C["📊 Analytics Service"]
    B[("Bookings")] --> C
    C --> D["Occupancy Analytics"]
    C --> E["Booking Analytics"]
    C --> F["Revenue Analytics"]
    D --> G(["🛡️ Admin API"])
    E --> G
    F --> G
```

---

## 🐳 Docker & Containerization

The entire backend stack — **Spring Boot API + MySQL** — is containerized and orchestrated with **Docker Compose**. Anyone with Docker installed can run the complete system with a single command, without installing Java, Maven or MySQL locally.

### 🤔 Why Docker?

A backend with a database has more moving parts than just code: a specific Java version, a specific MySQL version, environment variables, ports and networking. Docker packages all of that into reproducible units.

> **"It works on my machine" is not a deployment strategy.** Docker makes the machine part of the project.

### ✅ Key advantages

| | Advantage | What it means for this project |
|:---:|---|---|
| 🔁 | **Reproducibility** | Same Java 21 runtime and MySQL 8 version on every machine — no version mismatch bugs |
| ⚡ | **One-command setup** | `docker compose up --build` starts the database and the API together; no manual install or configuration |
| 🧱 | **Isolation** | The app and the database run in their own containers and never conflict with other software on the host |
| 🌐 | **Service networking** | Containers talk to each other by service name (`mysql`), over a private Docker network |
| 💾 | **Data persistence** | A named volume keeps MySQL data safe across container restarts and rebuilds |
| 🩺 | **Health-aware startup** | The API starts only after MySQL passes its health check — no connection-refused race conditions |
| 🔐 | **Config via environment** | Secrets (DB credentials, `JWT_SECRET`) are injected through `.env`, never baked into the image or committed |
| 🚀 | **Deployment-ready** | The same image that runs locally can run on a VM, a cloud server or any container platform |
| 🧹 | **Clean teardown** | `docker compose down` removes everything cleanly; nothing is left on the host |
| 🤝 | **Easy onboarding** | New contributors and reviewers can run the project in minutes |

### 🧩 Container architecture

```mermaid
flowchart LR
    U["👤 Client<br/>Postman · Swagger"] -->|"HTTP :8183"| B

    subgraph DC["🐳 Docker Compose · private network"]
        direction LR
        B["☕ backend<br/>Spring Boot API"] -->|"JDBC · mysql:3306"| M[("🐬 mysql<br/>MySQL 8")]
    end

    M --- V[("💾 Named volume<br/>mysql-data")]
    E["📄 .env<br/>DB + JWT secrets"] -.-> B
    E -.-> M
```

| Service | Image | Role | Notes |
|---|---|---|---|
| `mysql` | `mysql:8` | Database | Health-checked, data stored in a named volume |
| `backend` | Built from the project `Dockerfile` | REST API | Waits for `mysql` to be **healthy** before starting |

### 📦 Files added

| File | Purpose |
|---|---|
| `Dockerfile` | Builds the Spring Boot backend image |
| `compose.yaml` | Defines and wires the services, network, volume and health check |
| `.dockerignore` | Keeps the build context small and secrets out of the image |
| `.env` | Holds environment variables (**git-ignored** — never commit it) |

### ▶️ Run with Docker

```bash
# Start everything (build images + run containers)
docker compose up --build

# Run in the background
docker compose up --build -d

# Follow backend logs
docker compose logs -f backend

# Check container status and health
docker compose ps

# Stop and remove containers (data volume is preserved)
docker compose down

# Stop and also wipe the database volume
docker compose down -v
```

Once the containers are healthy:

| Resource | URL |
|---|---|
| 🌐 API base URL | `http://localhost:8183` |
| 📘 Swagger UI | `http://localhost:8183/swagger-ui/index.html` |

> [!IMPORTANT]
> Create a `.env` file in the project root **before** running Compose. Never commit it — add `.env` to `.gitignore`.

---

## 🗄️ Database Model

```mermaid
erDiagram
    ROLE ||--o{ USER : has
    USER ||--o{ VEHICLE : owns
    USER ||--o{ BOOKING : creates
    VEHICLE ||--o{ BOOKING : used_in
    PARKING_SLOT ||--o{ BOOKING : assigned_to

    ROLE {
        bigint id
        string name
    }

    USER {
        bigint id
        string name
        string email
        string password
        string phone
    }

    VEHICLE {
        bigint id
        string vehicle_number
        string vehicle_type
        string brand
        string color
    }

    BOOKING {
        bigint id
        datetime start_time
        datetime end_time
        string status
        double amount
        double surge_multiplier
    }

    PARKING_SLOT {
        bigint id
        string slot_number
        string status
        string slot_type
    }

    PARKING_RATE {
        bigint id
        string vehicle_type
        double rate_per_hour
        boolean active
    }
```

---

## 🌐 API Reference

All protected endpoints require:

```http
Authorization: Bearer <JWT_TOKEN>
```

| Module | Method | Endpoint | Access |
|---|:---:|---|:---:|
| 🔐 Auth | ![POST](https://img.shields.io/badge/POST-22C55E?style=flat-square) | `/auth/register` | 🌍 Public |
| 🔐 Auth | ![POST](https://img.shields.io/badge/POST-22C55E?style=flat-square) | `/auth/login` | 🌍 Public |
| 🚘 Vehicle | ![POST](https://img.shields.io/badge/POST-22C55E?style=flat-square) | `/vehicle/saveVehicle` | 🔒 User |
| 🅿️ Slots | ![POST](https://img.shields.io/badge/POST-22C55E?style=flat-square) | `/parkingslot/register` | 🛡️ Admin |
| 📋 Booking | ![POST](https://img.shields.io/badge/POST-22C55E?style=flat-square) | `/booking/book` | 🔒 User |
| 📋 Booking | ![PUT](https://img.shields.io/badge/PUT-F59E0B?style=flat-square) | `/booking/exit` | 🔒 User |
| 📋 Booking | ![GET](https://img.shields.io/badge/GET-0EA5E9?style=flat-square) | `/booking/my-bookings` | 🔒 User |
| 💰 Rates | ![GET](https://img.shields.io/badge/GET-0EA5E9?style=flat-square) | `/parking-rates` | 🔑 Authenticated |
| 💰 Rates | ![PUT](https://img.shields.io/badge/PUT-F59E0B?style=flat-square) | `/parking-rates/{vehicleType}` | 🛡️ Admin |
| 📊 Analytics | ![GET](https://img.shields.io/badge/GET-0EA5E9?style=flat-square) | `/admin/parking/analytics` | 🛡️ Admin |
| 📊 Analytics | ![GET](https://img.shields.io/badge/GET-0EA5E9?style=flat-square) | `/admin/parking/booking-analytics` | 🛡️ Admin |

<sub>🌍 Public &nbsp;·&nbsp; 🔑 Any authenticated user &nbsp;·&nbsp; 🔒 Role `USER` &nbsp;·&nbsp; 🛡️ Role `ADMIN`</sub>

---

## 🗂️ Project Structure

<details>
<summary><b>Click to expand</b></summary>

```text
smart_parking_system
│
├── Dockerfile
├── compose.yaml
├── .dockerignore
├── .env                      (git-ignored)
├── pom.xml
│
└── src/main/java/com/sahil/smart_parking_project
    │
    ├── controller
    │   ├── AuthController
    │   ├── VehicleController
    │   ├── ParkingSlotController
    │   ├── ParkingBookingController
    │   ├── ParkingRateController
    │   ├── AdminAnalyticsController
    │   └── AdminBookingAnalyticsController
    │
    ├── service
    │   ├── AuthService
    │   ├── ParkingSlotBookingService
    │   ├── ParkingSlotService
    │   ├── VehicleService
    │   ├── ParkingRateService
    │   ├── ParkingAnalyticsService
    │   └── ParkingBookingAnalyticsService
    │
    ├── repository
    │   ├── UserRepository
    │   ├── VehicleRepository
    │   ├── ParkingSlotRepository
    │   ├── BookingRepository
    │   ├── ParkingRateRepository
    │   └── RoleRepository
    │
    ├── entity
    │   ├── User · Role · Vehicle
    │   └── ParkingSlot · Booking · ParkingRate
    │
    ├── dto
    │   ├── Auth · Booking · Vehicle DTOs
    │   ├── Parking Slot · Parking Rate DTOs
    │   ├── Analytics DTOs
    │   └── PageResponseDTO
    │
    ├── security
    │   ├── JwtUtils
    │   ├── JwtAuthenticationFilter
    │   ├── CustomUserDetailsService
    │   └── SmartParkingSpringSecurity
    │
    ├── map_struct
    │   ├── UserMapper · VehicleMapper · BookingMapper
    │   └── ParkingSlotMapper · ParkingRateMapper
    │
    ├── globalException
    │   ├── GlobalExceptionHandler · ErrorResponse
    │   ├── ResourceNotFoundException · UnauthorizedActionException
    │   └── SlotUnavailableException · DuplicateResourceException
    │
    ├── enums
    │   └── RoleType · VehicleType · SlotStatus · BookingStatus
    │
    └── util
        └── ParkingBookingSlotUtil
```

</details>

---

## 🛠️ Tech Stack

| Layer | Technologies |
|---|---|
| ⚙️ **Backend** | Java · Spring Boot · Spring Security · Spring Data JPA · Hibernate |
| 🔌 **API & Auth** | REST · JWT · BCrypt · Jakarta Validation |
| 🧩 **Mapping** | MapStruct |
| 🗄️ **Database** | MySQL |
| 🐳 **DevOps** | Docker · Docker Compose |
| 🧰 **Tooling** | Maven · Git & GitHub · Postman · Eclipse / Spring Tool Suite |

---

## 🚀 Quick Start

Choose either path — **Docker** (recommended) or **Local**.

### 🐳 Option A — Run with Docker (recommended)

**Prerequisites:** Docker Desktop (or Docker Engine + Compose plugin)

**1. Clone the repository**

```bash
git clone https://github.com/Sahil2u47/smart_parking_system.git
cd smart_parking_system
```

**2. Create the `.env` file** in the project root

```env
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_long_secure_jwt_secret
```

> [!WARNING]
> Never commit real credentials or your `JWT_SECRET`. Use a long, random secret in every environment.

**3. Start the stack**

```bash
docker compose up --build
```

🎉 The backend is now live at **http://localhost:8183**

---

### 💻 Option B — Run locally

**Prerequisites**

| Requirement | Version |
|---|---|
| ☕ Java | 21+ |
| 🐬 MySQL | 8.x |
| 📦 Maven | Wrapper included (`mvnw`) |

**1. Create the database**

```sql
CREATE DATABASE smart_parkingdb;
```

**2. Set the same environment variables** (`DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`)

**3. Run the application**

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

🎉 The backend is now live at **http://localhost:8182**

---

### 🔑 First-time setup

The `USER` and `ADMIN` roles must exist in the `role` table before registering users. Seed them once on a fresh database:

```sql
INSERT INTO role (name) VALUES ('USER'), ('ADMIN');
```

### 🔒 Security Model

| Rule | Detail |
|---|---|
| Session policy | Stateless via `SessionCreationPolicy.STATELESS` |
| Public routes | `/auth/**` |
| Everything else | Requires a valid JWT and, where applicable, the correct role |

---

## 🔮 Roadmap

- [x] 🐳 Docker & Docker Compose support
- [ ] 💳 Payment gateway integration
- [ ] 🔔 Email / SMS notifications
- [ ] ⚙️ CI/CD pipeline with GitHub Actions
- [ ] ☁️ Cloud deployment

---

## 🎓 What This Project Demonstrates

Beyond basic CRUD, this backend covers:

`RESTful API design` · `Layered architecture` · `Spring Security with JWT & RBAC` · `JPA/Hibernate relationships` · `Transaction management` · `Pessimistic locking` · `DTO design with MapStruct` · `Bean validation` · `Global exception handling` · `Dynamic pricing` · `Booking lifecycle management` · `Pagination & sorting` · `Business analytics` · `Docker & Docker Compose` · `Containerized deployment`

The focus is on understanding the **real backend request flow**: business rules, security, persistence, transactions, concurrency and reproducible deployment.

---

## 👨‍💻 Author

<div align="center">

### Sahid Anwar
**Java Backend Developer · Spring Boot Developer**

[![GitHub](https://img.shields.io/badge/GitHub-Sahil2u47-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Sahil2u47)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Sahid%20Anwar-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/sahid-anwar-955898280/)

<br/>

*Built with ☕ Java + Spring Boot + persistence + security + containers + a lot of debugging.*

### 🚗 Smart Parking System — **Find. Book. Park.**

⭐ If you find this project useful, consider starring the repository.

</div>
