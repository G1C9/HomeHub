package com.example.homehub.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseRs {

    private UUID id;

    private Integer number;

    private Integer square;

    private UUID addressId;

}
