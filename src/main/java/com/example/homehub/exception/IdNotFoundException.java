package com.example.homehub.exception;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String entity, UUID id) {
        super(entity + " not found with id: " + id);
    }

}
