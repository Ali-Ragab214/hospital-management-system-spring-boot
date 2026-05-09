package com.ali.hospitalsystem.dto.response;

import com.ali.hospitalsystem.entity.BloodType;
import com.ali.hospitalsystem.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private BloodType bloodType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

