# Hospital Management System - Architecture & Deployment Guide

## Architecture Overview

### Clean/Lean Architecture Layers

The application follows a strict layered architecture:

```
Request
  ↓
Controller Layer (REST Endpoints)
  ↓
Service Layer (Business Logic)
  ↓
Repository Layer (Data Access)
  ↓
Entity Layer (Domain Models)
  ↓
Database (MySQL)
```

### Layer Responsibilities

#### 1. **Controller Layer** (`controller/`)
- **Responsibility**: Handle HTTP requests and responses
- **Pattern**: REST API endpoints with proper HTTP methods
- **Dependencies**: Services, DTOs
- **Key Classes**: `PatientController`, `DoctorController`, `AppointmentController`, `AuthController`

```
GET /api/patients → List all patients
POST /api/patients → Create new patient
GET /api/patients/{id} → Get patient by ID
PUT /api/patients/{id} → Update patient
DELETE /api/patients/{id} → Delete patient
```

#### 2. **Service Layer** (`service/`)
- **Responsibility**: Implement business logic, validation, orchestration
- **Pattern**: Interface + Implementation (Dependency Inversion)
- **Dependencies**: Repositories, Mappers
- **Key Classes**:
  - `PatientService` & `PatientServiceImpl`
  - `DoctorService` & `DoctorServiceImpl`
  - `AppointmentService` & `AppointmentServiceImpl`
  - `AuthService` & `AuthServiceImpl`

**Key Operations:**
- Data validation before persistence
- Duplicate checks (email, license number, username)
- Transaction management with `@Transactional`
- Business rule enforcement

#### 3. **Repository Layer** (`repository/`)
- **Responsibility**: Database access and query operations
- **Pattern**: Spring Data JPA extending `JpaRepository`
- **Dependencies**: Entities
- **Key Classes**: `UserRepository`, `PatientRepository`, `DoctorRepository`, `AppointmentRepository`

**Custom Methods:**
- `findByEmail(String email)`
- `findByStatus(AppointmentStatus status)`
- `existsByUsername(String username)`

#### 4. **Entity Layer** (`entity/`)
- **Responsibility**: Domain models with JPA annotations
- **Pattern**: Entity inheritance with Base class
- **All entities extend**: `BaseEntity`

**BaseEntity provides:**
- `id` (auto-generated Long)
- `createdAt` (automatic timestamp)
- `updatedAt` (automatic timestamp)

#### 5. **DTO Layer** (`dto/`)
- **Responsibility**: Data transfer between layers
- **Pattern**: Separate request and response DTOs

**Request DTOs:**
- `PatientRequestDto` - Create/Update patient
- `DoctorRequestDto` - Create/Update doctor
- `AppointmentRequestDto` - Create/Update appointment
- `LoginRequestDto` - User login
- `RegisterRequestDto` - User registration

**Response DTOs:**
- `PatientResponseDto` - Return patient data
- `DoctorResponseDto` - Return doctor data
- `AppointmentResponseDto` - Return appointment data
- `AuthResponseDto` - Return auth token and user info
- `ApiResponse<T>` - Generic wrapper for all responses

#### 6. **Mapper Layer** (`mapper/`)
- **Responsibility**: Convert between DTOs and Entities
- **Pattern**: MapStruct interfaces with automatic implementation
- **Key Classes**: `PatientMapper`, `DoctorMapper`, `AppointmentMapper`

#### 7. **Security Layer** (`security/`)

##### JWT (`security/jwt/`)
- **JwtUtil**: Token generation and validation
- **JwtAuthenticationFilter**: Intercept requests and validate tokens

##### Service (`security/service/`)
- **UserDetailsServiceImpl**: Load user by username for authentication

#### 8. **Configuration Layer** (`config/`)
- **SecurityConfig**: Spring Security configuration with JWT

## Data Flow

### Create Patient Flow

```
1. POST /api/patients with PatientRequestDto
   ↓
2. PatientController.createPatient()
   - Validate @Valid annotation
   - Call patientService.createPatient()
   ↓
3. PatientServiceImpl.createPatient()
   - Check email uniqueness
   - Convert DTO to Entity using PatientMapper
   - Call patientRepository.save()
   ↓
4. PatientRepository.save()
   - Persist Patient entity to database
   ↓
5. PatientService converts Entity back to ResponseDto
   ↓
6. PatientController wraps in ApiResponse<T>
   ↓
7. Return ResponseEntity with 201 CREATED status
```

### Authentication Flow

```
1. POST /api/auth/register with RegisterRequestDto
   ↓
2. AuthController.register()
   ↓
3. AuthServiceImpl.register()
   - Validate username and email uniqueness
   - Encode password with BCryptPasswordEncoder
   - Create and save User entity
   - Generate JWT token using JwtUtil
   ↓
4. Return AuthResponseDto with token
```

### Request Validation Flow

```
1. @Valid annotation on @RequestBody triggers validation
   ↓
2. Jakarta Bean Validation checks annotations:
   - @NotBlank, @Email, @NotNull, @PastOrPresent, etc.
   ↓
3. If validation fails: 400 BAD REQUEST with error details
   ↓
4. If validation passes: proceed to business logic
```

### JWT Authentication Flow

```
1. Request with: Authorization: Bearer {token}
   ↓
2. JwtAuthenticationFilter intercepts request
   ↓
3. Extract token from Authorization header
   ↓
4. JwtUtil.extractUsername() and validateToken()
   ↓
5. UserDetailsServiceImpl loads user from database
   ↓
6. Create UsernamePasswordAuthenticationToken
   ↓
7. Set in SecurityContext
   ↓
8. Request proceeds to controller if valid
   ↓
9. If invalid: 401 UNAUTHORIZED
```

## Entity Relationships

### User (Authentication)
```
User
├── id (Long)
├── username (unique)
├── password (encrypted)
├── email (unique)
├── role (enum)
└── timestamps (created/updated)
```

### Patient (Patient Information)
```
Patient (extends BaseEntity)
├── id (Long)
├── firstName
├── lastName
├── email (unique)
├── phone
├── dateOfBirth
├── gender (enum: MALE/FEMALE)
├── address
├── bloodType (enum)
└── timestamps
```

### Doctor (Doctor Information)
```
Doctor (extends BaseEntity)
├── id (Long)
├── firstName
├── lastName
├── email (unique)
├── phone
├── specialization
├── licenseNumber (unique)
├── yearsOfExperience
└── timestamps
```

### Appointment (Appointment Scheduling)
```
Appointment (extends BaseEntity)
├── id (Long)
├── patient (ManyToOne → Patient)
├── doctor (ManyToOne → Doctor)
├── appointmentDate (LocalDateTime)
├── status (enum: SCHEDULED/COMPLETED/CANCELLED)
├── notes
└── timestamps
```

## Database Schema

### users table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

### patients table
```sql
CREATE TABLE patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(50) NOT NULL,
    address TEXT NOT NULL,
    blood_type VARCHAR(50) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

### doctors table
```sql
CREATE TABLE doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) UNIQUE NOT NULL,
    years_of_experience INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
```

### appointments table
```sql
CREATE TABLE appointments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
```

## Key Design Patterns Used

### 1. **Dependency Injection**
```java
@Service
@RequiredArgsConstructor  // Lombok generates constructor
public class PatientServiceImpl {
    private final PatientRepository repository;
    private final PatientMapper mapper;
}
```

### 2. **Repository Pattern**
```java
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
}
```

### 3. **Service Layer Pattern**
```java
public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto dto);
}

@Service
public class PatientServiceImpl implements PatientService {
    // Implementation
}
```

### 4. **Mapper Pattern (MapStruct)**
```java
@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientRequestDto dto);
    PatientResponseDto toDto(Patient entity);
}
```

### 5. **Strategy Pattern (Security)**
- Different authentication providers (JWT, basic auth)
- Roles and permissions

### 6. **Builder Pattern (Lombok)**
```java
Patient patient = Patient.builder()
    .firstName("John")
    .lastName("Doe")
    .email("john@example.com")
    .build();
```

### 7. **Template Method Pattern**
- `BaseEntity` provides common functionality
- All entities inherit timestamps and id

## Configuration Properties

### Database
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital-system
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### JPA/Hibernate
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### JWT
```properties
jwt.secret=your-very-long-secret-key-here-make-it-at-least-256-bits
jwt.expiration=86400000  # 24 hours in milliseconds
```

### Server
```properties
server.port=8080
spring.application.name=hospital-system
```

## Deployment Guide

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+

### Build

```bash
# Build the project
./mvnw clean package -DskipTests

# Output: target/hospital-system-0.0.1-SNAPSHOT.jar
```

### Run

```bash
# Option 1: Using Maven
./mvnw spring-boot:run

# Option 2: Using Java JAR
java -jar target/hospital-system-0.0.1-SNAPSHOT.jar

# Option 3: With custom properties
java -jar target/hospital-system-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:mysql://prod-db:3306/hospital \
  --spring.datasource.username=prod_user \
  --spring.datasource.password=prod_password \
  --jwt.secret=your-production-secret-key
```

### Production Considerations

1. **Change default credentials**
   - Update `jwt.secret` to a long, random string
   - Update database username and password

2. **Enable HTTPS**
   ```properties
   server.ssl.key-store=classpath:keystore.p12
   server.ssl.key-store-password=password
   server.ssl.key-store-type=PKCS12
   ```

3. **Set appropriate logging level**
   ```properties
   logging.level.root=WARN
   logging.level.com.ali.hospitalsystem=INFO
   ```

4. **Enable SQL statement logging for debugging**
   ```properties
   spring.jpa.properties.hibernate.format_sql=false
   spring.jpa.show-sql=false
   ```

5. **Connection pooling**
   ```properties
   spring.datasource.hikari.maximum-pool-size=20
   spring.datasource.hikari.minimum-idle=5
   ```

## Monitoring & Logging

### Logging Configuration
```properties
logging.file.name=logs/hospital-system.log
logging.file.max-size=10MB
logging.file.max-history=30
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Health Check Endpoint
Add Spring Boot Actuator for monitoring:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=when-authorized
```

## Performance Considerations

1. **Lazy Loading**
   - Appointment entity uses `FetchType.LAZY` for Patient and Doctor

2. **Indexing**
   - Email fields are unique (indexed)
   - License number is unique (indexed)
   - Username is unique (indexed)

3. **Pagination**
   - Can be added to repository methods:
   ```java
   Page<Patient> findAll(Pageable pageable);
   ```

4. **Caching**
   - Can be added with Spring Cache abstraction:
   ```java
   @Cacheable("patients")
   public PatientResponseDto getPatientById(Long id)
   ```

## Error Handling Strategy

All exceptions are wrapped in ApiResponse:
```json
{
    "success": false,
    "message": "Error description",
    "data": null,
    "timestamp": "2026-05-01T15:42:00"
}
```

Common HTTP Status Codes:
- `200 OK` - Successful GET/PUT
- `201 CREATED` - Successful POST
- `400 BAD REQUEST` - Validation error
- `401 UNAUTHORIZED` - Missing/Invalid token
- `403 FORBIDDEN` - Insufficient permissions
- `404 NOT FOUND` - Resource not found
- `500 INTERNAL SERVER ERROR` - Server error

## Testing

### Unit Testing
```bash
./mvnw test
```

### Integration Testing
Use Spring Boot Test with `@SpringBootTest` and `@DataJpaTest`

### API Testing
Use the provided `TESTING.md` file with cURL or Postman

## Troubleshooting

### Database Connection Failed
```
Error: Unable to connect to database
Solution: Ensure MySQL is running and credentials are correct
```

### JWT Token Expired
```
Error: Invalid token signature or expired token
Solution: Re-login to get a new token
```

### Unique Constraint Violation
```
Error: Email already exists
Solution: Use a different email address
```

### Port Already in Use
```
Error: Address already in use: 8080
Solution: Change port in application.properties or kill process on port 8080
```

## Security Best Practices

1. ✅ Passwords are encrypted with BCryptPasswordEncoder
2. ✅ JWT tokens have expiration time
3. ✅ Authentication required for all endpoints except /api/auth/**
4. ✅ CSRF protection disabled (stateless API)
5. ✅ Request validation on all inputs
6. ✅ Unique constraints on sensitive fields

### To Improve Security Further:

1. Add rate limiting on login endpoint
2. Implement refresh tokens
3. Add CORS configuration
4. Use HTTPS in production
5. Add API key authentication option
6. Implement audit logging
7. Add password strength requirements

