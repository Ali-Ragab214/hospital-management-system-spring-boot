package com.ali.hospitalsystem.controller;

import com.ali.hospitalsystem.dto.request.DoctorRequestDto;
import com.ali.hospitalsystem.dto.response.ApiResponse;
import com.ali.hospitalsystem.dto.response.DoctorResponseDto;
import com.ali.hospitalsystem.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDto>> createDoctor(@Valid @RequestBody DoctorRequestDto dto) {
        DoctorResponseDto doctor = doctorService.createDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(doctor, "Doctor created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> getDoctorById(@PathVariable Long id) {
        DoctorResponseDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponse.success(doctor, "Doctor retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDto>>> getAllDoctors() {
        List<DoctorResponseDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(ApiResponse.success(doctors, "Doctors retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDto>> updateDoctor(
            @PathVariable Long id, @Valid @RequestBody DoctorRequestDto dto) {
        DoctorResponseDto doctor = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(ApiResponse.success(doctor, "Doctor updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Doctor deleted successfully"));
    }
}

