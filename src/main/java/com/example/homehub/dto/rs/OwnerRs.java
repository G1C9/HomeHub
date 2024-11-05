package com.example.homehub.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRs {

    private UUID id;

    private String firstName;

    private String lastName;

    private String gender;

    private PassportRs passport;

}
