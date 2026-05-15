package com.ali.hospitalsystem.service.patient;

import com.ali.hospitalsystem.dto.request.PatientRequestDto;
import com.ali.hospitalsystem.dto.response.PatientResponseDto;
import com.ali.hospitalsystem.entity.Patient;
import com.ali.hospitalsystem.mapper.PatientMapper;
import com.ali.hospitalsystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import com.myapp.exceptions.ConflictException;
import com.myapp.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto dto) {
        if (patientRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        Patient patient = patientMapper.toEntity(dto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDto(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
        return patientMapper.toDto(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponseDto updatePatient(Long id, PatientRequestDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        if (!patient.getEmail().equals(dto.getEmail()) && patientRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());
        patient.setBloodType(dto.getBloodType());

        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDto(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}

