package com.example.homehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "house_owners")
@Entity
public class HouseOwners {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private House house;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Owner owner;

}
