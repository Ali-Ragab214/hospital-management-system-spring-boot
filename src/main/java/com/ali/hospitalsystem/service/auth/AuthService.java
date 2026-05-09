package com.ali.hospitalsystem.service.auth;

import com.ali.hospitalsystem.dto.request.LoginRequestDto;
import com.ali.hospitalsystem.dto.request.RegisterRequestDto;
import com.ali.hospitalsystem.dto.response.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(RegisterRequestDto dto);
    AuthResponseDto login(LoginRequestDto dto);
}

