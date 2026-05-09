package com.ali.hospitalsystem.controller;

import com.ali.hospitalsystem.dto.request.PatientRequestDto;
import com.ali.hospitalsystem.dto.response.ApiResponse;
import com.ali.hospitalsystem.dto.response.PatientResponseDto;
import com.ali.hospitalsystem.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponseDto>> createPatient(@Valid @RequestBody PatientRequestDto dto) {
        PatientResponseDto patient = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(patient, "Patient created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDto>> getPatientById(@PathVariable Long id) {
        PatientResponseDto patient = patientService.getPatientById(id);
        return ResponseEntity.ok(ApiResponse.success(patient, "Patient retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponseDto>>> getAllPatients() {
        List<PatientResponseDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(ApiResponse.success(patients, "Patients retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDto>> updatePatient(
            @PathVariable Long id, @Valid @RequestBody PatientRequestDto dto) {
        PatientResponseDto patient = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(ApiResponse.success(patient, "Patient updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Patient deleted successfully"));
    }
}

