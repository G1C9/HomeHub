package com.example.homehub.dto.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRq {

    @NotBlank(message = "Enter the country")
    @Size(min = 2, max = 30)
    private String country;

    @NotBlank(message = "Enter the city")
    @Size(min = 2, max = 30)
    private String city;

    @NotBlank(message = "Enter the street")
    @Size(min = 2, max = 30)
    private String street;

}
