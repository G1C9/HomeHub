package com.example.homehub.dto.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRq {

    @NotBlank(message = "Enter the firstName")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotBlank(message = "Enter the lastName")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank(message = "Enter the gender")
    @Size(min = 2, max = 6)
    private String gender;

}
