package com.example.homehub.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRs {

    private UUID id;

    private String country;

    private String city;

    private String street;

}
