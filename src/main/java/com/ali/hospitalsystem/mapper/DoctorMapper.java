package com.ali.hospitalsystem.mapper;

import com.ali.hospitalsystem.dto.request.DoctorRequestDto;
import com.ali.hospitalsystem.dto.response.DoctorResponseDto;
import com.ali.hospitalsystem.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor toEntity(DoctorRequestDto dto);
    DoctorResponseDto toDto(Doctor entity);
}

