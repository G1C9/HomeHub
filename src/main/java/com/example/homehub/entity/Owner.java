package com.example.homehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Owner {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String gender;

    @OneToOne
    @JoinColumn(referencedColumnName = "id")
    private Passport passport;

    @OneToMany(mappedBy = "owner")
    private Set<HouseOwners> houseOwners = new HashSet<>();

}
