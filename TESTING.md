# Hospital Management System - Testing Guide

## Prerequisites
- MySQL running on localhost:3306 with database `hospital-system`
- Application running on `http://localhost:8080`
- A REST client (Postman, Thunder Client, or cURL)

## Setting Up the Database

```sql
CREATE DATABASE IF NOT EXISTS hospital_system;
USE hospital_system;
```

The Hibernate DDL-auto will automatically create all tables on application startup.

## Authentication Flow

### 1. Register a User

**Request:**
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "username": "admin123",
    "password": "admin@password",
    "email": "admin@hospital.com",
    "role": "ROLE_ADMIN"
}
```

**Response (201 CREATED):**
```json
{
    "success": true,
    "message": "User registered successfully",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEyMyIsImlhdCI6MTYyNTEwMDAwMCwiZXhwIjoxNjI1MTg2NDAwfQ.XXXXXX",
        "username": "admin123",
        "role": "ROLE_ADMIN"
    },
    "timestamp": "2026-05-01T15:42:00"
}
```

Save the token for subsequent requests.

### 2. Login

**Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "admin123",
    "password": "admin@password"
}
```

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "username": "admin123",
        "role": "ROLE_ADMIN"
    },
    "timestamp": "2026-05-01T15:42:00"
}
```

## Patient Management

### Create Patient

**Request:**
```
POST http://localhost:8080/api/patients
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "phone": "1234567890",
    "dateOfBirth": "1990-05-15",
    "gender": "MALE",
    "address": "123 Main Street, New York",
    "bloodType": "O_POSITIVE"
}
```

**Response (201 CREATED):**
```json
{
    "success": true,
    "message": "Patient created successfully",
    "data": {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@email.com",
        "phone": "1234567890",
        "dateOfBirth": "1990-05-15",
        "gender": "MALE",
        "address": "123 Main Street, New York",
        "bloodType": "O_POSITIVE",
        "createdAt": "2026-05-01T15:42:00",
        "updatedAt": "2026-05-01T15:42:00"
    },
    "timestamp": "2026-05-01T15:42:00"
}
```

### Get All Patients

**Request:**
```
GET http://localhost:8080/api/patients
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Response (200 OK):**
```json
{
    "success": true,
    "message": "Patients retrieved successfully",
    "data": [
        {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@email.com",
            "phone": "1234567890",
            "dateOfBirth": "1990-05-15",
            "gender": "MALE",
            "address": "123 Main Street, New York",
            "bloodType": "O_POSITIVE",
            "createdAt": "2026-05-01T15:42:00",
            "updatedAt": "2026-05-01T15:42:00"
        }
    ],
    "timestamp": "2026-05-01T15:42:00"
}
```

### Get Patient by ID

**Request:**
```
GET http://localhost:8080/api/patients/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Update Patient

**Request:**
```
PUT http://localhost:8080/api/patients/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe@email.com",
    "phone": "0987654321",
    "dateOfBirth": "1990-05-15",
    "gender": "FEMALE",
    "address": "456 Oak Avenue, Boston",
    "bloodType": "A_NEGATIVE"
}
```

### Delete Patient

**Request:**
```
DELETE http://localhost:8080/api/patients/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Doctor Management

### Create Doctor

**Request:**
```
POST http://localhost:8080/api/doctors
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "David",
    "lastName": "Smith",
    "email": "david.smith@hospital.com",
    "phone": "5551234567",
    "specialization": "Cardiology",
    "licenseNumber": "MD-2024-001",
    "yearsOfExperience": 12
}
```

**Response (201 CREATED):**
```json
{
    "success": true,
    "message": "Doctor created successfully",
    "data": {
        "id": 1,
        "firstName": "David",
        "lastName": "Smith",
        "email": "david.smith@hospital.com",
        "phone": "5551234567",
        "specialization": "Cardiology",
        "licenseNumber": "MD-2024-001",
        "yearsOfExperience": 12,
        "createdAt": "2026-05-01T15:42:00",
        "updatedAt": "2026-05-01T15:42:00"
    },
    "timestamp": "2026-05-01T15:42:00"
}
```

### Get All Doctors

**Request:**
```
GET http://localhost:8080/api/doctors
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Get Doctor by ID

**Request:**
```
GET http://localhost:8080/api/doctors/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Update Doctor

**Request:**
```
PUT http://localhost:8080/api/doctors/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "David",
    "lastName": "Johnson",
    "email": "david.johnson@hospital.com",
    "phone": "5559876543",
    "specialization": "Neurology",
    "licenseNumber": "MD-2024-001",
    "yearsOfExperience": 15
}
```

### Delete Doctor

**Request:**
```
DELETE http://localhost:8080/api/doctors/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Appointment Management

### Create Appointment

**Request:**
```
POST http://localhost:8080/api/appointments
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-06-15T14:30:00",
    "status": "SCHEDULED",
    "notes": "Annual checkup and consultation"
}
```

**Response (201 CREATED):**
```json
{
    "success": true,
    "message": "Appointment created successfully",
    "data": {
        "id": 1,
        "patient": {
            "id": 1,
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@email.com",
            "phone": "1234567890",
            "dateOfBirth": "1990-05-15",
            "gender": "MALE",
            "address": "123 Main Street, New York",
            "bloodType": "O_POSITIVE",
            "createdAt": "2026-05-01T15:42:00",
            "updatedAt": "2026-05-01T15:42:00"
        },
        "doctor": {
            "id": 1,
            "firstName": "David",
            "lastName": "Smith",
            "email": "david.smith@hospital.com",
            "phone": "5551234567",
            "specialization": "Cardiology",
            "licenseNumber": "MD-2024-001",
            "yearsOfExperience": 12,
            "createdAt": "2026-05-01T15:42:00",
            "updatedAt": "2026-05-01T15:42:00"
        },
        "appointmentDate": "2026-06-15T14:30:00",
        "status": "SCHEDULED",
        "notes": "Annual checkup and consultation",
        "createdAt": "2026-05-01T15:42:00",
        "updatedAt": "2026-05-01T15:42:00"
    },
    "timestamp": "2026-05-01T15:42:00"
}
```

### Get All Appointments

**Request:**
```
GET http://localhost:8080/api/appointments
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Get Appointment by ID

**Request:**
```
GET http://localhost:8080/api/appointments/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Get Appointments by Status

**Request:**
```
GET http://localhost:8080/api/appointments/status/SCHEDULED
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

Status values: `SCHEDULED`, `COMPLETED`, `CANCELLED`

### Update Appointment

**Request:**
```
PUT http://localhost:8080/api/appointments/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2026-06-20T16:00:00",
    "status": "COMPLETED",
    "notes": "Checkup completed successfully"
}
```

### Delete Appointment

**Request:**
```
DELETE http://localhost:8080/api/appointments/1
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## Error Handling Examples

### Validation Error

**Request with invalid data:**
```
POST http://localhost:8080/api/patients
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "",
    "lastName": "Doe",
    "email": "invalid-email",
    "phone": "123",
    "dateOfBirth": "2025-05-15",
    "gender": "INVALID",
    "address": "",
    "bloodType": "INVALID"
}
```

**Response (400 BAD REQUEST):**
```json
{
    "success": false,
    "message": "Validation failed: First name is required, Email should be valid, Date of birth must be in the past, etc.",
    "data": null,
    "timestamp": "2026-05-01T15:42:00"
}
```

### Resource Not Found

**Request:**
```
GET http://localhost:8080/api/patients/999
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

**Response (404 NOT FOUND):**
```json
{
    "success": false,
    "message": "Patient not found with id: 999",
    "data": null,
    "timestamp": "2026-05-01T15:42:00"
}
```

### Unauthorized (Missing/Invalid Token)

**Request without token:**
```
GET http://localhost:8080/api/patients
```

**Response (401 UNAUTHORIZED):**
```
HTTP/1.1 401 Unauthorized
```

### Duplicate Email/Unique Constraint

**Request with existing email:**
```
POST http://localhost:8080/api/patients
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "john.doe@email.com",
    ...
}
```

**Response (400 BAD REQUEST):**
```json
{
    "success": false,
    "message": "Email already exists",
    "data": null,
    "timestamp": "2026-05-01T15:42:00"
}
```

## Testing with cURL

### Register
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin123",
    "password": "admin@password",
    "email": "admin@hospital.com",
    "role": "ROLE_ADMIN"
  }'
```

### Create Patient
```bash
TOKEN="eyJhbGciOiJIUzI1NiJ9..."
curl -X POST http://localhost:8080/api/patients \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@email.com",
    "phone": "1234567890",
    "dateOfBirth": "1990-05-15",
    "gender": "MALE",
    "address": "123 Main Street, New York",
    "bloodType": "O_POSITIVE"
  }'
```

## Notes

- All timestamps are in ISO 8601 format
- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- The application uses Hibernate auditing to track creation and modification times
- All endpoints (except `/api/auth/**`) require authentication
- Roles: ROLE_ADMIN, ROLE_DOCTOR, ROLE_RECEPTIONIST

