package com.example.homehub.handler;

import com.example.homehub.dto.rs.ErrorRs;
import com.example.homehub.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.OffsetDateTime;

@RestControllerAdvice
public class ExHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ErrorRs notFoundException(IdNotFoundException ex) {
        return new ErrorRs(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                OffsetDateTime.now()
        );
    }

}
