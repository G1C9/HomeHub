package com.example.homehub.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassportRs {

    private UUID id;

    private Integer series;

    private Integer number;

}
