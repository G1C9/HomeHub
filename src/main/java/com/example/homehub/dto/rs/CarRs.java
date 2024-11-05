package com.example.homehub.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRs {

    private UUID id;

    private String brand;

    private String model;

    private UUID ownerId;

}
