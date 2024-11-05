package com.example.homehub.repository.jpa;

import com.example.homehub.entity.Car;
import com.example.homehub.repository.CarRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

@Profile("jpa")
public interface JpaCarRepository extends JpaRepository<Car, UUID>, CarRepository {

    List<Car> findAllByOwnerId(UUID ownerId);

    void deleteAllByOwnerId(UUID ownerId);

}
