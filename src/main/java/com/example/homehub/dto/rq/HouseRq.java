package com.example.homehub.dto.rq;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseRq {

    @NotNull(message = "Enter the number")
    @Min(value = 1, message = "Number must be at least 1")
    @Max(value = 1000, message = "Number must not exceed 1000")
    private Integer number;

    @NotNull(message = "Enter the square")
    @Min(value = 1, message = "Square must be at least 1")
    @Max(value = 100000, message = "Square must not exceed 100000")
    private Integer square;

    @NotNull(message = "Enter the address ID")
    private UUID addressId;

}
