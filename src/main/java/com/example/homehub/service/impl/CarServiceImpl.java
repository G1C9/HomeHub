package com.example.homehub.service.impl;

import com.example.homehub.entity.Car;
import com.example.homehub.exception.IdNotFoundException;
import com.example.homehub.repository.CarRepository;
import com.example.homehub.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import static com.example.homehub.constant.EntitiesConstant.CAR;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public Car getOne(UUID id) {
        log.info("GET CAR WITH ID: {}", id);
        return carRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("CAR NOT FOUND WITH ID: {}", id);
                    return new IdNotFoundException(CAR, id);
                });
    }

    @Override
    public List<Car> findAllByOwnerId(UUID ownerId) {
        log.info("FIND ALL CARS BY OWNER ID: {}", ownerId);
        return carRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public List<Car> getAll() {
        log.info("GET ALL CARS");
        return carRepository.findAll();
    }

    @Override
    public Car create(Car car) {
        log.info("CREATE CAR");
        return carRepository.save(Car.builder()
                .id(UUID.randomUUID())
                .brand(car.getBrand())
                .model(car.getModel())
                .owner(car.getOwner())
                .build());
    }

    @Override
    public Car update(Car car, UUID id) {
        return carRepository.findById(id)
                .map(existingCar -> {
                    Car updatedCar = existingCar.toBuilder()
                            .brand(car.getBrand())
                            .model(car.getModel())
                            .owner(car.getOwner())
                            .build();
                    log.info("UPDATE CAR WITH ID: {}", id);
                    return carRepository.save(updatedCar);
                })
                .orElseThrow(() -> new IdNotFoundException(CAR, id));
    }

    @Override
    public void delete(UUID id) {
        log.info("DELETE CAR");
        carRepository.deleteById(id);
    }

    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        log.info("DELETE ALL CARS BY OWNER ID: {}", ownerId);
        carRepository.deleteAllByOwnerId(ownerId);
    }

    @Override
    public void deleteAll() {
        log.info("DELETE ALL CARS");
        carRepository.deleteAll();
    }

}
