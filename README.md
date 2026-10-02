<div align="center">

<img src="assets/parking-system.gif" alt="Smart Parking System" width="100%"/>

🚗 Smart Parking System — Backend

Production-oriented REST API for intelligent parking management

<p>
  <b>Automatic Slot Allocation</b> •
  <b>JWT Security</b> •
  <b>RBAC</b> •
  <b>Dynamic Pricing</b> •
  <b>Concurrency Control</b> •
  <b>Analytics</b>
</p>

<p>
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-21%2B-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/></a>
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/></a>
  <a href="https://spring.io/projects/spring-security"><img src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security"/></a>
  <img src="https://img.shields.io/badge/Spring%20Data-JPA-59666C?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Data JPA"/>
  <img src="https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
</p>

<p>
  <img src="https://img.shields.io/badge/API-REST-0EA5E9?style=flat-square" alt="REST"/>
  <img src="https://img.shields.io/badge/Auth-JWT-F59E0B?style=flat-square" alt="JWT"/>
  <img src="https://img.shields.io/badge/Security-RBAC-8B5CF6?style=flat-square" alt="RBAC"/>
  <img src="https://img.shields.io/badge/Mapping-MapStruct-EAB308?style=flat-square" alt="MapStruct"/>
  <img src="https://img.shields.io/badge/Status-Active-22C55E?style=flat-square" alt="Status"/>
</p>

<p>
  <a href="#-overview">Overview</a> •
  <a href="#-features">Features</a> •
  <a href="#-architecture">Architecture</a> •
  <a href="#-api-overview">API</a> •
  <a href="#-run-locally">Run Locally</a>
</p>

</div>

🎯 Why this project?

Smart Parking System is not just a CRUD application.

It is a backend-focused system built around real-world engineering problems:

🔐 How should authenticated users access protected APIs?

🅿️ How can a parking slot be allocated automatically?

⚡ What happens when multiple users try to book at the same time?

💰 How can parking prices change with occupancy?

🔄 How should booking entry, exit, and cancellation change slot state?

📊 How can administrators monitor parking utilization and revenue?

🧩 How can API contracts remain separate from database entities?

The backend addresses these problems using Spring Boot, Spring Security, JWT, JPA/Hibernate, transactions, pessimistic locking, MapStruct, validation, and centralized exception handling.

Scope: This repository contains the backend only. The frontend is maintained separately.

✨ Feature Highlights

Area

What is implemented

🔐 Security

JWT authentication + BCrypt + RBAC

👤 Users

Registration, login, role-based access

🚘 Vehicles

Registration + ownership validation

🅿️ Parking

Automatic vehicle-type based slot allocation

📋 Booking

Entry, exit, cancellation, history

💰 Pricing

Database-backed rates + surge pricing

⚡ Concurrency

@Transactional + PESSIMISTIC_WRITE

📊 Analytics

Parking occupancy + booking/revenue analytics

📄 History

Pagination + sorting

🧩 API Design

DTOs + MapStruct

🛡️ Errors

Global exception handling + validation

🔐 Authentication & Authorization

The system uses stateless JWT authentication with Spring Security.

Security capabilities

User registration and login

BCrypt password hashing

JWT token generation and validation

Custom UserDetailsService

JWT authentication filter

USER and ADMIN roles

Method-level authorization with @PreAuthorize

Protected REST endpoints

User ownership validation

🔄 Login → Request Flow

flowchart LR
    A[Client] --> B[Login]
    B --> C[AuthenticationManager]
    C --> D[UserDetailsService]
    D --> E[Verify BCrypt Password]
    E --> F[Generate JWT]
    F --> G[Client Stores Token]
    G --> H[Authorization Bearer Token]
    H --> I[JwtAuthenticationFilter]
    I --> J[Validate JWT]
    J --> K[SecurityContext]
    K --> L[Role-Based Authorization]
    L --> M[Controller]

🅿️ Intelligent Parking Allocation

Users do not manually select a parking slot during booking.

The backend finds an available slot matching the vehicle type.

flowchart TD
    A[Parking Request] --> B[Authenticate User]
    B --> C[Validate Vehicle]
    C --> D[Check Ownership]
    D --> E[Check Active Booking]
    E --> F[Find AVAILABLE Matching Slot]
    F --> G[Acquire Database Lock]
    G --> H[Allocate Slot]
    H --> I[Slot = OCCUPIED]
    I --> J[Create ACTIVE Booking]

Supported vehicles

🚗 CAR

🏍️ BIKE

🛺 AUTO

🔄 Booking Lifecycle

The booking model supports entry, exit, and cancellation.

stateDiagram-v2
    [*] --> ACTIVE: Book Parking

    ACTIVE --> COMPLETED: Vehicle Exit
    ACTIVE --> CANCELLED: User Cancels

    COMPLETED --> [*]
    CANCELLED --> [*]

Slot lifecycle

stateDiagram-v2
    [*] --> AVAILABLE

    AVAILABLE --> OCCUPIED: Booking
    OCCUPIED --> AVAILABLE: Exit
    OCCUPIED --> AVAILABLE: Cancellation

    AVAILABLE --> MAINTENANCE: Admin
    MAINTENANCE --> AVAILABLE: Admin

This keeps the booking state and parking-slot state synchronized.

❌ Booking Cancellation

Cancellation is available for an authenticated user's ACTIVE booking.

Flow

flowchart LR
    A[PUT /booking/cancel] --> B[Find ACTIVE Booking]
    B --> C{Booking Belongs to User?}
    C -- No --> D[Unauthorized Action]
    C -- Yes --> E[Status = CANCELLED]
    E --> F[Slot = AVAILABLE]
    F --> G[Transaction Commit]

Rules

Only ACTIVE bookings can be cancelled.

Users can cancel only their own booking.

Cancellation releases the allocated parking slot.

A cancelled booking cannot be exited.

Cancelled bookings remain visible in booking history.

The operation is transactional.

Request

PUT /booking/cancel
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json

{
  "vehicleNumber": "DL01AB1234"
}

🚪 Parking Exit & Billing

When a vehicle exits:

Find the user's active booking.

Verify booking ownership.

Record exit time.

Calculate parking duration.

Apply minimum one-hour billing.

Use the booking's locked surge multiplier.

Calculate final amount.

Mark booking as COMPLETED.

Release the parking slot.

flowchart TD
    A[Exit Request] --> B[Find ACTIVE Booking]
    B --> C[Verify Ownership]
    C --> D[Record End Time]
    D --> E[Calculate Duration]
    E --> F[Minimum 1 Hour Billing]
    F --> G[Apply Locked Surge Multiplier]
    G --> H[Calculate Amount]
    H --> I[Booking = COMPLETED]
    I --> J[Slot = AVAILABLE]

💰 Dynamic Pricing Engine

Parking rates are stored in the database rather than hardcoded inside booking logic.

Current configured rates:

Vehicle

Rate

🏍️ Bike

₹20/hour

🛺 Auto

₹40/hour

🚗 Car

₹50/hour

Rates can be managed independently through admin APIs.

⚡ Surge Pricing

The system calculates a surge multiplier based on current parking occupancy.

flowchart LR
    A[Total Capacity] --> C[Occupancy Ratio]
    B[Occupied Slots] --> C
    C --> D[Surge Multiplier]
    D --> E[Booking]
    E --> F[Multiplier Locked]
    F --> G[Final Billing at Exit]

The multiplier is stored with the booking so that the pricing basis remains consistent between entry and exit.

⚡ Concurrency Control

A real parking system has a concurrency problem:

What happens if multiple users request the last available slot at almost the same time?

This project handles slot allocation using:

@Transactional

PESSIMISTIC_WRITE

Database-level locking

Atomic slot state updates

sequenceDiagram
    participant A as User A
    participant B as User B
    participant DB as MySQL

    A->>DB: Find available slot + lock
    B->>DB: Find available slot + lock
    DB-->>A: Slot locked
    A->>DB: Slot = OCCUPIED
    DB-->>B: Wait / re-check
    B->>DB: Cannot allocate same occupied slot

This protects the booking process from assigning the same slot to concurrent requests.

🚘 Vehicle Management

Each registered vehicle contains:

Vehicle number

Vehicle type

Brand

Color

Owner relationship

The backend also validates ownership before performing operations on user-owned vehicles.

This prevents one authenticated user from manipulating another user's vehicle or booking.

📊 Admin Analytics

Administrators can monitor the current parking operation.

Parking analytics

Total slots

Available slots

Occupied slots

Maintenance slots

Overall occupancy percentage

Vehicle-type-wise slot statistics

Booking analytics

Total bookings

Active bookings

Completed bookings

Cancelled bookings

Total completed revenue

flowchart LR
    A[Parking Slots] --> C[Analytics Service]
    B[Bookings] --> C
    C --> D[Occupancy Analytics]
    C --> E[Booking Analytics]
    C --> F[Revenue Analytics]
    D --> G[Admin API]
    E --> G
    F --> G

📄 Booking History

Users can retrieve their own booking history with:

Pagination

Sorting

User-specific filtering

DTO-based responses

Booking status including CANCELLED

Example:

GET /booking/my-bookings?page=0&size=10&sort=startTime,desc

Custom sorting:

GET /booking/my-bookings?page=0&size=5&sort=amount,desc

Example response:

{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 25,
  "totalPages": 3
}

🏗️ Architecture

The project follows a clean layered architecture.

flowchart TB
    A[Client / Postman / Frontend]
    B[REST Controllers]
    C[Service Layer]
    D[Repository Layer]
    E[(MySQL Database)]

    A --> B
    B --> C
    C --> D
    D --> E

    S[Spring Security<br/>JWT + RBAC] --> B
    M[MapStruct<br/>DTO Mapping] --> C
    X[Global Exception Handler] --> B

Request processing

HTTP Request
     ↓
Spring Security
     ↓
JWT Validation
     ↓
Authentication / Authorization
     ↓
Controller
     ↓
DTO Validation
     ↓
Service
     ↓
Business Rules
     ↓
Transaction
     ↓
Repository
     ↓
MySQL
     ↓
Entity
     ↓
MapStruct
     ↓
Response DTO
     ↓
HTTP Response

🧩 DTO & Mapping Strategy

Entities are not directly exposed through REST APIs.

flowchart LR
    A[JSON Request] --> B[Request DTO]
    B --> C[MapStruct]
    C --> D[JPA Entity]
    D --> E[(Database)]
    E --> D
    D --> F[MapStruct]
    F --> G[Response DTO]
    G --> H[JSON Response]

Benefits:

Cleaner API contracts

Separation of API and persistence models

Reduced manual mapping code

Better maintainability

🛡️ Validation & Exception Handling

The application uses Jakarta Bean Validation and centralized exception handling.

Validation covers:

Required fields

Email

Password

Phone number

Vehicle number

Parking slot data

Parking rates

Centralized handling is implemented using:

@RestControllerAdvice

Handled scenarios include:

Validation errors

Resource not found

Duplicate resources

Unauthorized actions

Slot unavailable

Authentication failures

Access denied

Runtime exceptions

🗄️ Database Model

erDiagram
    ROLE ||--o{ USER : has
    USER ||--o{ VEHICLE : owns
    USER ||--o{ BOOKING : creates
    VEHICLE ||--o{ BOOKING : used_in
    PARKING_SLOT ||--o{ BOOKING : assigned_to
    PARKING_RATE }o--|| VEHICLE : configured_for

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

🗂️ Project Structure

src/main/java/com/sahil/smart_parking_project
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
│   ├── User
│   ├── Role
│   ├── Vehicle
│   ├── ParkingSlot
│   ├── Booking
│   └── ParkingRate
│
├── dto
│   ├── Auth DTOs
│   ├── Booking DTOs
│   ├── Vehicle DTOs
│   ├── Parking Slot DTOs
│   ├── Parking Rate DTOs
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
│   ├── UserMapper
│   ├── VehicleMapper
│   ├── BookingMapper
│   ├── ParkingSlotMapper
│   └── ParkingRateMapper
│
├── globalException
│   ├── GlobalExceptionHandler
│   ├── ErrorResponse
│   ├── ResourceNotFoundException
│   ├── UnauthorizedActionException
│   ├── SlotUnavailableException
│   └── DuplicateResourceException
│
├── enums
│   ├── RoleType
│   ├── VehicleType
│   ├── SlotStatus
│   └── BookingStatus
│
└── util
    └── ParkingBookingSlotUtil

🌐 API Overview

<details>
<summary><b>🔐 Authentication</b></summary>

POST /auth/register
POST /auth/login

</details>

<details>
<summary><b>🚘 Vehicle</b></summary>

POST /vehicle/saveVehicle

</details>

<details>
<summary><b>🅿️ Parking Slots</b></summary>

POST /parkingslot/register

</details>

<details>
<summary><b>📋 Booking</b></summary>

POST /booking/book
PUT  /booking/exit
PUT  /booking/cancel
GET  /booking/my-bookings

</details>

<details>
<summary><b>💰 Parking Rates</b></summary>

GET /parking-rates
PUT /parking-rates/{vehicleType}

</details>

<details>
<summary><b>📊 Admin Analytics</b></summary>

GET /admin/parking/analytics
GET /admin/parking/booking-analytics

</details>

Admin operations are protected through Spring Security role-based authorization.

🛠️ Technology Stack

Backend

Java

Spring Boot

Spring Security

Spring Data JPA

Hibernate

REST APIs

JWT

MapStruct

Jakarta Validation

Database

MySQL

Development Tools

Maven

Git & GitHub

Postman

Eclipse / Spring Tool Suite

▶️ Run Locally

1. Clone

git clone https://github.com/Sahil2u47/smart_parking_system.git
cd smart_parking_system

2. Create Database

CREATE DATABASE smart_parkingdb;

3. Configure Environment

Use environment variables for sensitive configuration:

DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_long_secure_jwt_secret

Never commit real database passwords or JWT secrets to GitHub.

4. Run

Windows

mvnw.cmd spring-boot:run

Linux / macOS

./mvnw spring-boot:run

Backend:

http://localhost:8182

🔒 Security Model

The application uses stateless authentication:

SessionCreationPolicy.STATELESS

Authenticated requests send:

Authorization: Bearer <JWT_TOKEN>

Public:

/auth/**

Protected APIs require authentication and, where applicable, the correct role.

🧠 Engineering Highlights

🔐 Security

JWT + Spring Security + RBAC

⚡ Concurrency

Pessimistic database locking for slot allocation

🔄 Transactions

Transactional booking, exit, and cancellation operations

🅿️ Smart Allocation

Automatic slot selection based on vehicle type

💰 Pricing

Database-backed rates + occupancy-based surge pricing

🛡️ Data Integrity

Ownership validation + active-booking prevention

🧩 Maintainability

DTOs + MapStruct

📄 API Design

Pagination + sorting + structured responses

📊 Analytics

Parking utilization + booking + revenue analytics

❌ Cancellation

Ownership-safe cancellation with slot release

🧯 Error Handling

Centralized @RestControllerAdvice

📈 What this project demonstrates

This backend goes beyond basic CRUD by implementing:

Java
  ↓
Spring Boot
  ↓
REST API Design
  ↓
JPA / Hibernate
  ↓
MySQL
  ↓
Spring Security
  ↓
JWT
  ↓
RBAC
  ↓
Transactions
  ↓
Concurrency Control
  ↓
Dynamic Pricing
  ↓
Booking Lifecycle
  ↓
Analytics

The focus is on understanding real backend request flow, business rules, security, persistence, transactions, and concurrency.

🔮 Future Enhancements

The current backend intentionally keeps the scope focused.

Possible future additions:

💳 Payment gateway integration

🔔 Email/SMS notifications

📝 Audit logging

📚 OpenAPI / Swagger documentation

🧪 Comprehensive unit and integration testing

📊 Advanced reporting

⚙️ Production monitoring and observability

🎓 Learning Outcomes

Through this project, I gained practical experience with:

RESTful API design

Spring Boot layered architecture

Spring Security

JWT authentication

Role-based authorization

Spring Data JPA

Hibernate

Entity relationships

Transaction management

Pessimistic locking

Concurrency handling

DTO design

MapStruct

Bean validation

Global exception handling

Dynamic pricing

Booking lifecycle management

Cancellation workflows

Pagination and sorting

Business analytics

API testing with Postman

Git and GitHub

👨‍💻 Author

<div align="center">

Sahid Anwar

Java Backend Developer | Spring Boot Developer

<a href="https://github.com/Sahil2u47">
  <img src="https://img.shields.io/badge/GitHub-Sahil2u47-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
</a>

<a href="https://linkedin.com/in/sahid-anwar-955898280/">
  <img src="https://img.shields.io/badge/LinkedIn-Sahid%20Anwar-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
</a>

<br><br>

Built with ☕ Java + Spring Boot + persistence + security + a lot of debugging.

🚗 Smart Parking System

Find. Book. Park.

</div>

<div align="center">

⭐ If you find this project useful, consider starring the repository.

</div>
