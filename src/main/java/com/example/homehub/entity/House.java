package com.example.homehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class House {

    @Id
    private UUID id;

    private Integer number;

    private Integer square;

    @OneToMany(mappedBy = "house")
    private Set<HouseOwners> houseOwners = new HashSet<>();

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Address address;

}
