package com.example.demo.apiRequest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostRequest {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
}
