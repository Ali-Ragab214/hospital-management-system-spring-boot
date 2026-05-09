# Hospital Management System

A comprehensive Hospital Management System built with Spring Boot, following Clean/Lean Architecture principles.

## Features

- **Patient Management**: CRUD operations for patient records
- **Doctor Management**: CRUD operations for doctor profiles
- **Appointment Scheduling**: Book, manage, and track appointments
- **Authentication & Authorization**: Secure JWT-based authentication with role-based access control
- **Database Auditing**: Automatic tracking of created and updated timestamps

## Technology Stack

- **Framework**: Spring Boot 4.0.6
- **Language**: Java 17
- **Database**: MySQL
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT (JJWT 0.11.5)
- **Mapping**: MapStruct
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven

## Project Structure

```
com.ali.hospitalsystem
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── PatientController.java
│   ├── DoctorController.java
│   └── AppointmentController.java
├── dto/
│   ├── request/
│   │   ├── LoginRequestDto.java
│   │   ├── RegisterRequestDto.java
│   │   ├── PatientRequestDto.java
│   │   ├── DoctorRequestDto.java
│   │   └── AppointmentRequestDto.java
│   └── response/
│       ├── AuthResponseDto.java
│       ├── PatientResponseDto.java
│       ├── DoctorResponseDto.java
│       ├── AppointmentResponseDto.java
│       └── ApiResponse.java
├── entity/
│   ├── BaseEntity.java
│   ├── User.java
│   ├── Patient.java
│   ├── Doctor.java
│   ├── Appointment.java
│   ├── Gender.java
│   ├── BloodType.java
│   ├── AppointmentStatus.java
│   └── Role.java
├── mapper/
│   ├── PatientMapper.java
│   ├── DoctorMapper.java
│   └── AppointmentMapper.java
├── repository/
│   ├── UserRepository.java
│   ├── PatientRepository.java
│   ├── DoctorRepository.java
│   └── AppointmentRepository.java
├── security/
│   ├── jwt/
│   │   ├── JwtUtil.java
│   │   └── JwtAuthenticationFilter.java
│   └── service/
│       └── UserDetailsServiceImpl.java
└── service/
    ├── auth/
    │   ├── AuthService.java
    │   └── AuthServiceImpl.java
    ├── patient/
    │   ├── PatientService.java
    │   └── PatientServiceImpl.java
    ├── doctor/
    │   ├── DoctorService.java
    │   └── DoctorServiceImpl.java
    └── appointment/
        ├── AppointmentService.java
        └── AppointmentServiceImpl.java
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup

Create a MySQL database:
```sql
CREATE DATABASE hospital_system;
```

The application uses Hibernate's `ddl-auto=update` to automatically create/update tables.

### Configuration

Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital-system
spring.datasource.username=root
spring.datasource.password=
```

### Build and Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Authentication Endpoints

#### Register
```
POST /api/auth/register
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123",
    "email": "john@example.com",
    "role": "ROLE_RECEPTIONIST"
}

Response: 201 CREATED
{
    "success": true,
    "message": "User registered successfully",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "username": "john_doe",
        "role": "ROLE_RECEPTIONIST"
    },
    "timestamp": "2026-05-01T10:00:00"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "john_doe",
    "password": "password123"
}

Response: 200 OK
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "username": "john_doe",
        "role": "ROLE_RECEPTIONIST"
    },
    "timestamp": "2026-05-01T10:00:00"
}
```

### Patient Endpoints

#### Create Patient
```
POST /api/patients
Authorization: Bearer {token}
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "dateOfBirth": "1990-05-15",
    "gender": "MALE",
    "address": "123 Main St",
    "bloodType": "O_POSITIVE"
}

Response: 201 CREATED
```

#### Get All Patients
```
GET /api/patients
Authorization: Bearer {token}

Response: 200 OK
{
    "success": true,
    "message": "Patients retrieved successfully",
    "data": [
        {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john@example.com",
            "phone": "1234567890",
            "dateOfBirth": "1990-05-15",
            "gender": "MALE",
            "address": "123 Main St",
            "bloodType": "O_POSITIVE",
            "createdAt": "2026-05-01T10:00:00",
            "updatedAt": "2026-05-01T10:00:00"
        }
    ],
    "timestamp": "2026-05-01T10:00:00"
}
```

#### Get Patient by ID
```
GET /api/patients/{id}
Authorization: Bearer {token}
```

#### Update Patient
```
PUT /api/patients/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
    "firstName": "Jane",
    "lastName": "Doe",
    ...
}
```

#### Delete Patient
```
DELETE /api/patients/{id}
Authorization: Bearer {token}
```

### Doctor Endpoints

#### Create Doctor
```
POST /api/doctors
Authorization: Bearer {token}
Content-Type: application/json

{
    "firstName": "Dr.",
    "lastName": "Smith",
    "email": "smith@hospital.com",
    "phone": "9876543210",
    "specialization": "Cardiology",
    "licenseNumber": "LIC123456",
    "yearsOfExperience": 10
}

Response: 201 CREATED
```

#### Get All Doctors
```
GET /api/doctors
Authorization: Bearer {token}
```

#### Get Doctor by ID
```
GET /api/doctors/{id}
Authorization: Bearer {token}
```

#### Update Doctor
```
PUT /api/doctors/{id}
Authorization: Bearer {token}
Content-Type: application/json
```

#### Delete Doctor
```
DELETE /api/doctors/{id}
Authorization: Bearer {token}
```

### Appointment Endpoints

#### Create Appointment
```
POST /api/appointments
Authorization: Bearer {token}
Content-Type: application/json

{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-06-01T14:30:00",
    "status": "SCHEDULED",
    "notes": "Regular checkup"
}

Response: 201 CREATED
```

#### Get All Appointments
```
GET /api/appointments
Authorization: Bearer {token}
```

#### Get Appointment by ID
```
GET /api/appointments/{id}
Authorization: Bearer {token}
```

#### Get Appointments by Status
```
GET /api/appointments/status/{status}
Authorization: Bearer {token}

Status values: SCHEDULED, COMPLETED, CANCELLED
```

#### Update Appointment
```
PUT /api/appointments/{id}
Authorization: Bearer {token}
Content-Type: application/json
```

#### Delete Appointment
```
DELETE /api/appointments/{id}
Authorization: Bearer {token}
```

## Security

- All endpoints except `/api/auth/**` require JWT authentication
- Passwords are encrypted using BCryptPasswordEncoder
- JWT tokens expire after 24 hours (86400000 ms)
- Include token in Authorization header: `Authorization: Bearer {token}`

## Entity Relationships

- **User**: Stores authentication credentials and roles
- **Patient**: Contains patient information and medical history
- **Doctor**: Contains doctor information and qualifications
- **Appointment**: Links Patient and Doctor for scheduled appointments

## Enumerations

### Gender
- MALE
- FEMALE

### BloodType
- A_POSITIVE (A+)
- A_NEGATIVE (A-)
- B_POSITIVE (B+)
- B_NEGATIVE (B-)
- AB_POSITIVE (AB+)
- AB_NEGATIVE (AB-)
- O_POSITIVE (O+)
- O_NEGATIVE (O-)

### AppointmentStatus
- SCHEDULED
- COMPLETED
- CANCELLED

### Role
- ROLE_ADMIN
- ROLE_DOCTOR
- ROLE_RECEPTIONIST

## Error Handling

All API responses follow a consistent format:

**Success Response:**
```json
{
    "success": true,
    "message": "Operation successful",
    "data": { ... },
    "timestamp": "2026-05-01T10:00:00"
}
```

**Error Response:**
```json
{
    "success": false,
    "message": "Error description",
    "data": null,
    "timestamp": "2026-05-01T10:00:00"
}
```

## Architecture Highlights

- **Clean Architecture**: Clear separation of concerns with distinct layers
- **Dependency Injection**: Constructor injection using Lombok @RequiredArgsConstructor
- **MapStruct Mapping**: Type-safe DTO to Entity mapping
- **JPA Auditing**: Automatic tracking of creation and modification timestamps
- **Transaction Management**: Service layer with @Transactional annotations
- **Validation**: Input validation using Jakarta Bean Validation annotations



