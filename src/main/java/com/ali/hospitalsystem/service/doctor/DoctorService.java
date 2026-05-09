package com.ali.hospitalsystem.service.doctor;

import com.ali.hospitalsystem.dto.request.DoctorRequestDto;
import com.ali.hospitalsystem.dto.response.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    DoctorResponseDto createDoctor(DoctorRequestDto dto);
    DoctorResponseDto getDoctorById(Long id);
    List<DoctorResponseDto> getAllDoctors();
    DoctorResponseDto updateDoctor(Long id, DoctorRequestDto dto);
    void deleteDoctor(Long id);
}

