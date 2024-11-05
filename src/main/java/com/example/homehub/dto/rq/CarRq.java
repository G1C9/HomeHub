package com.example.homehub.dto.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRq {

    @NotBlank(message = "Enter the brand")
    @Size(min = 2, max = 30)
    private String brand;

    @NotBlank(message = "Enter the model")
    @Size(min = 2, max = 30)
    private String model;

    @NotNull(message = "Enter the owner ID")
    private UUID ownerId;

}
