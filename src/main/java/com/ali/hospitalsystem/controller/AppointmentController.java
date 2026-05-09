package com.ali.hospitalsystem.controller;

import com.ali.hospitalsystem.dto.request.AppointmentRequestDto;
import com.ali.hospitalsystem.dto.response.ApiResponse;
import com.ali.hospitalsystem.dto.response.AppointmentResponseDto;
import com.ali.hospitalsystem.entity.AppointmentStatus;
import com.ali.hospitalsystem.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        AppointmentResponseDto appointment = appointmentService.createAppointment(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(appointment, "Appointment created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> getAppointmentById(@PathVariable Long id) {
        AppointmentResponseDto appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponse.success(appointment, "Appointment retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAllAppointments() {
        List<AppointmentResponseDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(ApiResponse.success(appointments, "Appointments retrieved successfully"));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDto>>> getAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        List<AppointmentResponseDto> appointments = appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(appointments, "Appointments retrieved by status successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDto>> updateAppointment(
            @PathVariable Long id, @Valid @RequestBody AppointmentRequestDto dto) {
        AppointmentResponseDto appointment = appointmentService.updateAppointment(id, dto);
        return ResponseEntity.ok(ApiResponse.success(appointment, "Appointment updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Appointment deleted successfully"));
    }
}

