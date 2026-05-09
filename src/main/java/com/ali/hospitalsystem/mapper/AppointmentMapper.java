package com.ali.hospitalsystem.mapper;

import com.ali.hospitalsystem.dto.request.AppointmentRequestDto;
import com.ali.hospitalsystem.dto.response.AppointmentResponseDto;
import com.ali.hospitalsystem.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DoctorMapper.class})
public interface AppointmentMapper {
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Appointment toEntity(AppointmentRequestDto dto);

    @Mapping(source = "patient", target = "patient")
    @Mapping(source = "doctor", target = "doctor")
    AppointmentResponseDto toDto(Appointment entity);
}

