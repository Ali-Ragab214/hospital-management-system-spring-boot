package com.ali.hospitalsystem.mapper;

import com.ali.hospitalsystem.dto.request.PatientRequestDto;
import com.ali.hospitalsystem.dto.response.PatientResponseDto;
import com.ali.hospitalsystem.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    Patient toEntity(PatientRequestDto dto);
    PatientResponseDto toDto(Patient entity);
}

