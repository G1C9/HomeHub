package com.example.homehub.dto.rq;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseOwnersRq {

    @NotNull(message = "Enter the house ID")
    private UUID houseId;

    @NotNull(message = "Enter the owner ID")
    private UUID ownerId;

}
