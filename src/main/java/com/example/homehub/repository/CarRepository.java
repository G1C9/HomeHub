package com.example.homehub.repository;

import com.example.homehub.entity.Car;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository {

    Optional<Car> findById(UUID id);

    List<Car> findAllByOwnerId(UUID ownerId);

    List<Car> findAll();

    Car save(Car car);

    void deleteById(UUID id);

    void deleteAllByOwnerId(UUID id);

    void deleteAll();

}
