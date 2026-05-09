# Hospital Management System - Quick Start Guide

## ⚡ 5-Minute Setup

### Step 1: Prerequisites Check
```bash
# Check Java version
java -version
# Expected: Java 17+

# Check Maven
mvn -version
# Expected: Maven 3.6+

# Check MySQL
mysql --version
# Expected: MySQL 8.0+
```

### Step 2: Database Setup
```bash
# Login to MySQL
mysql -u root

# Create database
CREATE DATABASE hospital_system;

# Exit
EXIT;
```

### Step 3: Build & Run
```bash
# Navigate to project
cd F:\Downloads\dream-shops\hospital-system

# Build
./mvnw clean package -DskipTests

# Run
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`

---

## 🧪 Quick Test (Using cURL)

### Step 1: Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@hospital.com",
    "role": "ROLE_ADMIN"
  }'
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "admin",
    "role": "ROLE_ADMIN"
  },
  "timestamp": "2026-05-01T15:42:00"
}
```

**Save the token:**
```bash
TOKEN="eyJhbGciOiJIUzI1NiJ9..."
```

### Step 2: Create Patient
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@hospital.com",
    "phone": "1234567890",
    "dateOfBirth": "1990-05-15",
    "gender": "MALE",
    "address": "123 Main St",
    "bloodType": "O_POSITIVE"
  }'
```

### Step 3: Create Doctor
```bash
curl -X POST http://localhost:8080/api/doctors \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Sarah",
    "lastName": "Smith",
    "email": "sarah.smith@hospital.com",
    "phone": "9876543210",
    "specialization": "Cardiology",
    "licenseNumber": "MD001",
    "yearsOfExperience": 10
  }'
```

### Step 4: Create Appointment
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-06-15T14:30:00",
    "status": "SCHEDULED",
    "notes": "Regular checkup"
  }'
```

### Step 5: Get All Patients
```bash
curl -X GET http://localhost:8080/api/patients \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📋 API Endpoints Quick Reference

| Method | Endpoint | Purpose |
|--------|----------|---------|
| **Auth** |
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login user |
| **Patients** |
| GET | `/api/patients` | List all patients |
| POST | `/api/patients` | Create patient |
| GET | `/api/patients/{id}` | Get patient by ID |
| PUT | `/api/patients/{id}` | Update patient |
| DELETE | `/api/patients/{id}` | Delete patient |
| **Doctors** |
| GET | `/api/doctors` | List all doctors |
| POST | `/api/doctors` | Create doctor |
| GET | `/api/doctors/{id}` | Get doctor by ID |
| PUT | `/api/doctors/{id}` | Update doctor |
| DELETE | `/api/doctors/{id}` | Delete doctor |
| **Appointments** |
| GET | `/api/appointments` | List all appointments |
| POST | `/api/appointments` | Create appointment |
| GET | `/api/appointments/{id}` | Get appointment by ID |
| GET | `/api/appointments/status/{status}` | Get by status |
| PUT | `/api/appointments/{id}` | Update appointment |
| DELETE | `/api/appointments/{id}` | Delete appointment |

---

## 🔑 Authentication

All endpoints except `/api/auth/**` require authentication.

**Header format:**
```
Authorization: Bearer {token}
```

**Token expires in:** 24 hours

---

## 📊 Key Enumerations

### Gender
```
MALE, FEMALE
```

### Blood Type
```
A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE,
AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, O_NEGATIVE
```

### Appointment Status
```
SCHEDULED, COMPLETED, CANCELLED
```

### User Role
```
ROLE_ADMIN, ROLE_DOCTOR, ROLE_RECEPTIONIST
```

---

## 🛠️ Development

### Run in IDE
1. Open project in IntelliJ IDEA or VS Code
2. Run `HospitalSystemApplication.java`
3. Application starts on `http://localhost:8080`

### Hot Reload
```bash
./mvnw spring-boot:run -Dspring-boot.run.fork=false
```

### Run Tests
```bash
./mvnw test
```

### Generate JAR
```bash
./mvnw clean package -DskipTests
# Output: target/hospital-system-0.0.1-SNAPSHOT.jar
```

### Run JAR
```bash
java -jar target/hospital-system-0.0.1-SNAPSHOT.jar
```

---

## 🔧 Common Tasks

### Change Database Credentials
Edit `application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Change Server Port
Edit `application.properties`:
```properties
server.port=8081
```

### Enable Debug Logging
Edit `application.properties`:
```properties
logging.level.root=DEBUG
logging.level.com.ali.hospitalsystem=DEBUG
```

### Disable SQL Logging
Edit `application.properties`:
```properties
spring.jpa.show-sql=false
```

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| `README.md` | Full project documentation |
| `TESTING.md` | Detailed API testing guide |
| `ARCHITECTURE.md` | Architecture & design patterns |
| `PROJECT_SUMMARY.md` | Complete project statistics |
| `QUICK_START.md` | This file |

---

## ✅ Checklist

- [ ] Java 17+ installed
- [ ] MySQL running
- [ ] Database `hospital_system` created
- [ ] Project compiled successfully
- [ ] Application running on port 8080
- [ ] Register a user
- [ ] Create a patient
- [ ] Create a doctor
- [ ] Create an appointment
- [ ] List all patients/doctors/appointments

---

## 🆘 Troubleshooting

### "Cannot connect to database"
```bash
# Check MySQL is running
mysql -u root
# Verify credentials in application.properties
```

### "Port 8080 already in use"
```bash
# Change port in application.properties
# Or kill process on port 8080
```

### "Invalid JWT token"
```bash
# Get a new token by logging in
# Token expires after 24 hours
```

### "Email already exists"
```bash
# Use a different email address
```

### "Build fails with 'symbol cannot be found'"
```bash
# Clean and rebuild
./mvnw clean compile
```

---

## 📞 Support

Refer to the comprehensive documentation:
- **API Details**: See `README.md`
- **Testing Examples**: See `TESTING.md`
- **Architecture**: See `ARCHITECTURE.md`

---

## 🎯 Next Steps

1. ✅ Set up database and run application
2. ✅ Register user and get authentication token
3. ✅ Test API endpoints with provided examples
4. ✅ Create some sample data
5. ✅ Read ARCHITECTURE.md for design details
6. ✅ Customize for your needs

**Happy coding! 🚀**

