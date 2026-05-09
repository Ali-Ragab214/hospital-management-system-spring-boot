package com.ali.hospitalsystem.service.patient;

import com.ali.hospitalsystem.dto.request.PatientRequestDto;
import com.ali.hospitalsystem.dto.response.PatientResponseDto;

import java.util.List;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto dto);
    PatientResponseDto getPatientById(Long id);
    List<PatientResponseDto> getAllPatients();
    PatientResponseDto updatePatient(Long id, PatientRequestDto dto);
    void deletePatient(Long id);
}

