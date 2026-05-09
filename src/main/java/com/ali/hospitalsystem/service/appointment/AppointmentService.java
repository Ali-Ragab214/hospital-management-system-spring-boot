package com.ali.hospitalsystem.service.appointment;

import com.ali.hospitalsystem.dto.request.AppointmentRequestDto;
import com.ali.hospitalsystem.dto.response.AppointmentResponseDto;
import com.ali.hospitalsystem.entity.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto createAppointment(AppointmentRequestDto dto);
    AppointmentResponseDto getAppointmentById(Long id);
    List<AppointmentResponseDto> getAllAppointments();
    List<AppointmentResponseDto> getAppointmentsByStatus(AppointmentStatus status);
    AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto dto);
    void deleteAppointment(Long id);
}

