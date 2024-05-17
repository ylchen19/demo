package com.example.demo.apiRequest;

import com.example.demo.entity.UserRole;
import jakarta.persistence.Column;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String biography;
    private UserRole role;
}
