package com.example.homehub.dto.rs;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ErrorRs (
    HttpStatus status,
    String message,
    OffsetDateTime time
) {}
