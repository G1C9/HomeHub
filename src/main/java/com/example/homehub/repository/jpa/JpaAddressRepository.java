package com.example.homehub.repository.jpa;

import com.example.homehub.entity.Address;
import com.example.homehub.repository.AddressRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@Profile("jpa")
public interface JpaAddressRepository extends JpaRepository<Address, UUID>, AddressRepository {

}
