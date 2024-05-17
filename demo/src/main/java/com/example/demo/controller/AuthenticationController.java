package com.example.demo.controller;

import com.example.demo.apiRequest.LoginRequest;
import com.example.demo.apiRequest.RegisterRequest;
import com.example.demo.apiResponse.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "身分驗證",description = "註冊、登入、登出身分驗證API")
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "register socket")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @Operation(summary = "login socket")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @Operation(summary = "refresh token")
    @PostMapping("/refresh_token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshTokens(request, response);
    }

}
