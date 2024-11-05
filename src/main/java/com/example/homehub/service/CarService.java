package com.example.homehub.service;

import com.example.homehub.entity.Car;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface CarService {

    Car getOne(UUID id);

    List<Car> findAllByOwnerId(UUID ownerId);

    List<Car> getAll();

    Car create(Car car);

    Car update(Car car, UUID id);

    void delete(UUID id);

    void deleteAllByOwnerId(UUID ownerId);

    void deleteAll();

}
