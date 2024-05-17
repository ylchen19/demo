package com.example.demo.service;

import com.example.demo.apiRequest.LoginRequest;
import com.example.demo.apiRequest.RegisterRequest;
import com.example.demo.apiResponse.AuthenticationResponse;
import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.TokenType;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService{

    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .biography(request.getBiography())
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeTokens(user);
        saveUserToken(user, token);
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .status("Success")
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .status("Success")
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = TokenEntity
                .builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshTokens(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.validateToken(refreshToken, user)) {
                var token = jwtService.generateToken(user);
                revokeTokens(user);
                saveUserToken(user, token);
                var authResponse = AuthenticationResponse.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
