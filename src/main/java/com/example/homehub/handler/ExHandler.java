package com.example.homehub.handler;

import com.example.homehub.dto.rs.ErrorRs;
import com.example.homehub.exception.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class ExHandler {

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRs notFoundException(IdNotFoundException ex) {
        log.error("Exception caught: {}", ex.getMessage());
        return new ErrorRs(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                OffsetDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    public ErrorRs handleGeneralException(Exception ex) {
        log.error("Exception caught: ", ex);
        return new ErrorRs(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                OffsetDateTime.now()
        );
    }

}
